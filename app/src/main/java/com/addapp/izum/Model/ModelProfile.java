package com.addapp.izum.Model;

import com.addapp.izum.OtherClasses.JSONRequest;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ILDAR on 01.07.2015.
 */
public class ModelProfile {

    private ArrayList<String> arrayPhoto = new ArrayList<>();
    private JSONRequest jsonRequest;

    public ModelProfile() throws JSONException {
        arrayPhoto.add("photo_1");
        arrayPhoto.add("photo_2");
        arrayPhoto.add("photo_3");

        jsonRequest = new JSONRequest("http://im.topufa.org/index.php");
        jsonRequest.setParam("action", "auth_singin");
        jsonRequest.setParam("phone", "89191479353");
        jsonRequest.setParam("password", "450871");
        jsonRequest.setParam("deviceid", "");
        jsonRequest.execute();
    }

    public ArrayList<String> getArrayPhoto(){
        return arrayPhoto;
    }
}
