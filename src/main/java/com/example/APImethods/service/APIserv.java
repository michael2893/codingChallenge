package com.example.APImethods.service;

import com.example.APImethods.models.InvitationModel;
import com.example.APImethods.models.PartnerModel;

import java.util.Date;
import java.util.List;

import java.util.Map;


public interface APIserv {

        public  List<PartnerModel.Partner> retrievePartnersAvailability() throws Exception; // defines partner list

        public  List<Date> datesForPartner(PartnerModel.Partner partner); //get available dates for partner


        public  Map<String, List<PartnerModel.Partner>> countryDates(List<PartnerModel.Partner> partners);

        // function to get the final invite list of people
        public  Map<String, Map<Date, List<PartnerModel.Partner>>> getInvitationList(List<PartnerModel.Partner> partnerList);

        // function to CREATE the invitation list
        public  List<InvitationModel> getInvites(List<PartnerModel.Partner> partners);

// post
        public  String post(String invitationList) throws Exception;
// get
        public String get() throws Exception;



}


