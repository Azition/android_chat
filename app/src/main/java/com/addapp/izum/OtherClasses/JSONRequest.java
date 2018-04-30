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
    private boolean complete = false;

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

    public boolean isComplete(){
        return complete;
    }

    private void setComplete(boolean complete){
        this.complete = complete;
    }

    private class RequestFromURL extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            setComplete(false);
            jsonObj = jsonParser.getJSONFromUrl(URL);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setComplete(aBoolean);
        }
    }
}
