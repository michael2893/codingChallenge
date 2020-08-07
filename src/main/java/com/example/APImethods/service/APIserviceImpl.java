package com.example.APImethods.service;
import com.example.APImethods.models.PartnerModel.Partner;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class APIservice {


        String getURL = "https://candidate.hubteam.com/candidateTest/v1/partners?userKey=bede60f886f7068a632c7911919d";

        String postURL = "https://candidate.hubteam.com/candidateTest/v1/results?userKey=bede60f886f7068a632c7911919d";


        
        public List<Partner> retrievePartnersAvailability(); // defines partner list

        // function to get the dates of the partner on which they can attend the conference
        public List<Date> feasibleDatesForPartner(Partner partner);

        // function to get the dates on which the conference can be kept on countries
        public Map<String, List<Partner>> buildCountryDates(List<Partner> partners);

        // function to get the final date, partner and country list
        public Map<String, Map<Date, List<Partner>>> getInvitationList(List<Partner> partnerList);

        // function to create the invitation list
        public List<Invitation> getInvites(List<Partner> partners);
}
