package com.addapp.izum.OtherClasses;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by ILDAR on 10.07.2015.
 */
public class JSONRequest {

    private String URL;
    private JSONParser jsonParser;
    private JSONObject jsonObj;

    public JSONRequest(String URL){
        this.URL = URL;
        jsonParser = new JSONParser();
    }

    public void setParam(String key, String value){
        jsonParser.setParam(key, value);
    }

    public void execute(){
        new RequestFromURL().execute();
    }

    public JSONObject getJsonObj(){
        return jsonObj;
    }

    private class RequestFromURL extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            jsonObj = jsonParser.getJSONFromUrl(URL);
            return null;
        }
    }
}
