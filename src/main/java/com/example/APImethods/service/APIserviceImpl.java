package com.example.APImethods.service;
import com.example.APImethods.models.InvitationModel;
import com.example.APImethods.models.PartnerModel.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class APIserviceImpl implements APIserv {


    String getURL = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=f18567a90a4b49658b55674c74e6";

    String postURL = "https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=f18567a90a4b49658b55674c74e6";

    @Override
    public List<Partner> retrievePartnersAvailability() throws Exception {


        try {

            List<Partner> partnerList = new ArrayList<Partner>();

            String jsonData = get();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonData);
            JSONObject json = (JSONObject) obj;

            JSONArray result = (JSONArray) json.get("partners");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            for (int i = 0; i < result.size(); i++) {

                JSONObject jsonPartner = (JSONObject) result.get(i);

                Partner p = gson.fromJson(jsonPartner.toJSONString(), Partner.class);

                partnerList.add(p);

            }

            return partnerList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Date> datesForPartner(Partner partner) {

        Collections.sort(partner.getAvailableDates()); //tim sort with collections

        Map<Date, Integer> startDates = new TreeMap<>(); // tree map to preserve sorted key order

        List<Date> dates = new ArrayList<>(partner.getAvailableDates()); // we now have a list of dates that are sorted by date
        if (!dates.isEmpty()) {
            startDates.put(dates.get(0), 0); // in this treeMap we can put the first date (K) and 0 (V)
        }
        for (int i = 1; i < dates.size(); i++) {

            Date prev = dates.get(i - 1); // we can set a previous date as current - 1 where current is just
            Date current = dates.get(i); // went ahead and stored current as a local variable

            long difference = Math.abs(dates.get(i).getTime() - prev.getTime()) / (24 * 60 * 60 * 1000);


            if (difference == 1) {
                int count  = startDates.get(prev);
                startDates.put(prev, count+1);
                startDates.put(current, 1);
            }
            startDates.put(current, 0);
        }
        List<Date> datesThatWork = new ArrayList<Date>(); // here's our list of dates that work

        for (Map.Entry<Date, Integer> entry : startDates.entrySet()) { //we need the entry set from the start dates
            // this way we can populate a list with the keys only of
            // dates we want
            if (entry.getValue() > 0) { // if the value > 0 (that is there is not conflict)
                datesThatWork.add(entry.getKey()); /// add to the new list the key (the date)
            }
        }

        return datesThatWork; // return a list of these dates

    }

    @Override
    public Map<String, List<Partner>> countryDates(List<Partner> partners) {

        Map<String, List<Partner>> countryMap = new HashMap<String, List<Partner>>();
        // hold a list of the partners in the values

        for (Partner p : partners) { // look at all of the partners in our list

            List<Partner> partnerList = new ArrayList<Partner>(); // list to store partners

            if (countryMap.containsKey(p.getCountry())) { // if the map countains the country then -->
                partnerList = countryMap.get(p.getCountry()); // the list of partners is the list of people from the country
                partnerList.add(p); // add to that list the person
            }
            partnerList.add(p);  // we will continue to add people to the list
            countryMap.put(p.getCountry(), partnerList); // the map will now put the country and the list of people
            // associated with the country as the value

        }
        return countryMap;  // returns the K,V relationship of countries to people
    }

    @Override
    public Map<String, Map<Date, List<Partner>>> getInvitationList(List<Partner> partnerList) {
        Map<String, List<Partner>> countryMap = countryDates(partnerList);

        Map<String, Map<Date, List<Partner>>> invitatonMap = new HashMap<>();

        for (Map.Entry<String, List<Partner>> countryEntry : countryMap.entrySet()) {

            List<Partner> partners = countryEntry.getValue(); /// partners are the entries values

            Set<Date> setDate = new TreeSet<>(); // to preserve order again

            for (Partner p : partners) { /// add all of the partners for the given date here
                setDate.addAll(datesForPartner(p));
            }

            Map<Date, List<Partner>> partnerMap = new TreeMap<>();

            for (Partner p : partners) {
                List<Date> partnerDate = datesForPartner(p); //get dates for each person

                for (Date d : partnerDate) { /// look at each date
                    if (setDate.contains(d)) {
                        List<Partner> partList= new ArrayList<>();
                        if (partnerMap.containsKey(d)) {
                            partList = partnerMap.get(d); // add to new List
                        }
                        partList.add(p);
                        partnerMap.put(d, partList); // put the dates with new people into partner map
                    }
                }
            }

            invitatonMap.put(countryEntry.getKey(), partnerMap);

        }

        return invitatonMap; // return the new map
    }

    public List<InvitationModel> getInvites(List<Partner> partners) {

        List<InvitationModel> invites = new ArrayList<>();

        Map<String, Map<Date, List<Partner>>> invitationMap = getInvitationList(partners); ///
        /// the map here is actually just holding the invitation list for the parameter partners

        for (Map.Entry<String, Map<Date, List<Partner>>> invitationEntry : invitationMap.entrySet()) {

            Map<Date, List<Partner>> dateMap = invitationEntry.getValue();
            /// the map of dates that work for people are going to be from the map we made of the invitation list

            int max = -1;
            Date date = null;
            List<Partner> available = null;

            for (Map.Entry<Date, List<Partner>> entry : dateMap.entrySet()) { // look at the entries in the date set

                List<Partner> partnerList = entry.getValue(); // the partner list comes from the entries in this set

                if (partnerList.size() > max) { /// if the size is 0 or larger
                    max = partnerList.size();  // max is the size
                    date = entry.getKey(); // date is the entry key
                    available = partnerList; // available list populated
                }
            }

            InvitationModel invitationModel = new InvitationModel(); // instance of model

            invitationModel.setAttendeeCount(available.size()); // set the inivites from those that are available

            List<String> emailList = new ArrayList<>();
            for (Partner partner : available) // look at all of the partners in avaiable, get emails, and set the, in the invite
                emailList.add(partner.getEmail());
            invitationModel.setAttendees(emailList);

            invitationModel.setName(invitationEntry.getKey());
            invitationModel.setStartDate(date);
            invites.add(invitationModel);
        }

        return invites;
    }

    public String post(String invitationList) throws Exception {
        String result = "";
        HttpPost post = new HttpPost(postURL);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "userKey=f18567a90a4b49658b55674c74e6");
        post.setHeader("Accept", "application/json");
        post.setHeader("Accept-Charset", "UTF-8");


        post.setEntity(new StringEntity(invitationList));


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }


    @Override
    public String get() throws Exception {
   /// get for the initial json data

        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getURL))
                .header("Authorization", "userKey=f18567a90a4b49658b55674c74e6")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }
}