package com.example.APImethods.controller;
import java.util.List;

import com.example.APImethods.models.InvitationModel;
import com.example.APImethods.models.PartnerModel;
import com.example.APImethods.service.APIserv;
import com.example.APImethods.utils.Util;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Utilities;

@RestController
@RequestMapping(value = "/api")
public class APIController {

    @Autowired
    public APIserv service;

    private List<PartnerModel.Partner> partnerList;

    private List<InvitationModel> inviteList;





/// test for response

@GetMapping(value = "/response")
@ApiResponse(responseCode = "200", description = "Success")
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
public String get() throws Exception {
    return service.get();

}


    @GetMapping(value = "/request")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public String sendInvitation() throws Exception {
    Util util = new Util();
        partnerList = service.retrievePartnersAvailability();

        if(partnerList == null) {
           System.out.println("Null list!");
        }

        inviteList = service.getInvites(partnerList);

        String invitationListForPost = util.stringToJSON(inviteList);

        return service.post(invitationListForPost);
    }
}