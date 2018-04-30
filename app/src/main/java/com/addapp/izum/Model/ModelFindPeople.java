package com.addapp.izum.Model;

import android.content.Context;
import android.os.AsyncTask;

import com.addapp.izum.Controller.ControllerFindPeople;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ILDAR on 24.06.2015.
 */
public class ModelFindPeople {

    private JSONObject jsonObj;
    private ArrayList<FindListItem> arrayListItem;
    private int page = 0;
    private MainUserData userData = MainUserData.getInstance();

    public ModelFindPeople(Context context){

        arrayListItem = new ArrayList<>();
    }

    public void update(ControllerFindPeople controllerFindPeople){
        new FindHandler(controllerFindPeople).execute();
    }

    public ArrayList<FindListItem> getArrayListItem() {
        return arrayListItem;
    }

    private class FindHandler extends AsyncTask<Void, ArrayList<FindListItem>, Void> {

        private boolean handling;
        private ControllerFindPeople controllerFindPeople;

        public FindHandler(ControllerFindPeople controllerFindPeople){
            this.controllerFindPeople = controllerFindPeople;
            handling = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (handling){
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<FindListItem>... values) {
            super.onProgressUpdate(values);
            controllerFindPeople.notifyAdapter(values[0]);
        }
    }

    public void addPeople(ControllerFindPeople controllerFindPeople){
        page++;
        new AddPeople(controllerFindPeople).execute();
    }

    private class AddPeople extends AsyncTask<Void, ArrayList<FindListItem>, Void> {

        private boolean handling;
        private ControllerFindPeople controllerFindPeople;

        public AddPeople(ControllerFindPeople controllerFindPeople){
            this.controllerFindPeople = controllerFindPeople;
            handling = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (handling){
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<FindListItem>... values) {
            super.onProgressUpdate(values);
            controllerFindPeople.notifyAdapter(values[0]);
        }
    }

    public static class FindListItem{

        private String avatar;
        private String id;
        private String name;
        private String age;

        public FindListItem(String avatar, String id, String name, String age){
            this.avatar = avatar;
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    private ArrayList<FindListItem> handleJSONObj(JSONObject json) throws JSONException {

        ArrayList<FindListItem> arrayList = new ArrayList<>();
        JSONObject item = null;
        JSONArray items = null;
        int count = 0;

        try {
            count = json.getInt("total");
            items = new JSONArray(json.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < count; i++){
            item = new JSONObject(items.get(i).toString());
            arrayList.add(new FindListItem(
                    item.getString("avatar"),
                    item.getString("id"),
                    Utils.deleteEnters(item.getString("name")),
                    item.getString("age")
            ));
        }

        return arrayList;
    }

}
