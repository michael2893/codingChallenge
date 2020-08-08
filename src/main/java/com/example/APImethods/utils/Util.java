package com.example.APImethods.utils;

import com.example.APImethods.models.InvitationModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Util {

   public String stringToJSON(List<InvitationModel> inviteesList) throws JSONException {

        Gson gson = new Gson();
        String json = gson.toJson(inviteesList);

        return json;
    }
}
