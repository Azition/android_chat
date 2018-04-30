package com.addapp.izum.Model;

import android.content.Context;
import android.util.Log;

import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.Interface.OnModelUpdate;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;
import com.addapp.izum.Structure.ChoiceListItem;
import com.addapp.izum.Structure.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ILDAR on 01.07.2015.
 */

public class ModelProfile extends CommonModel implements OnModelUpdate{

    private final String TAG = getClass().getSimpleName();

    private ArrayList<String> arrayPhoto = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayCity = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayRegion = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayFamilyStatus = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayFindGender = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayGender = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayTarget = new ArrayList<>();
    private ArrayList<ChoiceListItem> arrayInterest = new ArrayList<>();


    private User user;

    private MainUserData userData = MainUserData.getInstance();
    private SocketIO mSocket = SocketIO.getInstance();
    private DownloadThread downloadThread;

    private int pos, max;

    public ModelProfile(Context context){
        downloadThread = new DownloadThread();
        downloadThread.start();
        mSocket.addModel(SocketIO.Model.PROFILE, this);

//        arrayPhoto.add("photo_1");
//        arrayPhoto.add("photo_2");
//        arrayPhoto.add("photo_3");


        arrayFindGender.add(new ChoiceListItem(0, false, "Не выбрано"));
        arrayFindGender.add(new ChoiceListItem(1, false, "Девушку"));
        arrayFindGender.add(new ChoiceListItem(2, false, "Парня"));

        arrayGender.add(new ChoiceListItem(0, false, "Не выбрано"));
        arrayGender.add(new ChoiceListItem(1, false, "Женский"));
        arrayGender.add(new ChoiceListItem(2, false, "Мужской"));
    }

    public ArrayList<String> getArrayPhoto(){
        return arrayPhoto;
    }

    public ArrayList<ChoiceListItem> getCity(){
        return arrayCity;
    }

    public ArrayList<ChoiceListItem> getRegion(){
        return arrayRegion;
    }

    public ArrayList<ChoiceListItem> getFamilyStatus() {
        return arrayFamilyStatus;
    }

    public ArrayList<ChoiceListItem> getFindGender() {
        return arrayFindGender;
    }

    public ArrayList<ChoiceListItem> getGender() {
        return arrayGender;
    }

    public ArrayList<ChoiceListItem> getTarget() {
        return arrayTarget;
    }

    public ArrayList<ChoiceListItem> getInterest() {
        return arrayInterest;
    }

    public void onUpdateCity(JSONArray json){

        arrayCity.clear();

        try {

            for (int i = 0; i < json.length(); i++){
                JSONObject obj = json.getJSONObject(i);
                arrayCity.add(new ChoiceListItem(obj.getInt("id"),
                        false, obj.getString("name")));
                if (i == 0)
                    user.setCity(obj.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, user.toString());
    }

    public void onUpdateData(JSONObject json){
        JSONArray targetArray;
        JSONArray interestArray;
        JSONArray regionArray;
        JSONArray cityArray;
        JSONArray familystatusArray;

        arrayCity.clear();
        arrayRegion.clear();
        arrayFamilyStatus.clear();
        arrayTarget.clear();
        arrayInterest.clear();



        /*********************************************************************
        *   Инициализация массивов
        **********************************************************************/

        try {
            targetArray = json.getJSONArray("targets");
            interestArray = json.getJSONArray("interest");
            regionArray = json.getJSONArray("region");
            cityArray = json.getJSONArray("cities");
            familystatusArray = json.getJSONArray("family_status");

            /*********************************************************************
             *   Добавление целей в массив
             **********************************************************************/

            for (int i = 0; i < targetArray.length(); i++){
                JSONObject obj = targetArray.getJSONObject(i);
                boolean find = false;
                for(String name : user.getArrayTarget()) {
                    if (name.equals(obj.getString("target"))) {
                        find = true;
                        break;
                    }
                }
                arrayTarget.add(new ChoiceListItem(obj.getInt("id"),
                        find, obj.getString("target")));
            }

            /*********************************************************************
             *   Добавление интересов в массив
             **********************************************************************/

            for (int i = 0; i < interestArray.length(); i++){
                JSONObject obj = interestArray.getJSONObject(i);
                boolean find = false;
                for(String name : user.getArrayInterest())
                    if (name.equals(obj.getString("interest"))) {
                        find = true;
                        break;
                    }
                arrayInterest.add(new ChoiceListItem(obj.getInt("id"),
                        find, obj.getString("interest")));
            }

            /*********************************************************************
             *   Добавление регионов в массив
             **********************************************************************/

            for (int i = 0; i < regionArray.length(); i++){
                JSONObject obj = regionArray.getJSONObject(i);
                arrayRegion.add(new ChoiceListItem(obj.getInt("id"),
                        false, obj.getString("name")));
            }

            /*********************************************************************
             *   Добавление городов в массив
             **********************************************************************/

            for (int i = 0; i < cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                arrayCity.add(new ChoiceListItem(obj.getInt("id"),
                        false, obj.getString("name")));
            }

            /*********************************************************************
             *   Добавление семейных статусов в массив
             **********************************************************************/

            for (int i = 0; i < familystatusArray.length(); i++){
                arrayFamilyStatus.add(new ChoiceListItem(0,
                        false,
                        familystatusArray.getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getProfileData(int id){
        mSocket.getProfileData(id);
        mSocket.getData(id);
    }

    public void getCityList(String region){
        for(ChoiceListItem item : arrayRegion)
            if (item.getText().equals(region))
                mSocket.getCityList(item.getId());
    }

    public int getMax() {
        return max;
    }

    public int getPos() {
        return pos;
    }

    public void onDownload(){
        Log.e(TAG, "onDownload");
        if(!downloadThread.doWork()) {
            Log.e(TAG, "start download");
            downloadThread.startDownload();
        }
    }

    public void onStopDownload(){
        Log.e(TAG, "onStopDownload");
        if (downloadThread.doWork()) {
            Log.e(TAG, "stop download");
            downloadThread.stopDownload();
        }
    }

    public void setRenderListener(OnRenderProgess mListener){
        downloadThread.setRenderListener(mListener);
    }

    public void setProgress(int current, int max){
        this.pos = current;
        this.max = max;
    }

    @Override
    public void onListen(Object obj) {
        JSONObject json = (JSONObject) obj;
        try {
            this.user = new User.Builder(json.getString("name"))
                    .setAvatar(json.getString("avatar"))
                    .setBirthday(json.getString("birthday"))
                    .setGender(json.getString("gender"))
                    .setUserInfo(json.getString("info"))
                    .setCity(json.getString("city"))
                    .setRegion(json.getString("region"))
                    .setCountry(json.getString("country"))
                    .setStatus(json.getString("status"))
                    .setLookingFor(json.getString("lookingfor"))
                    .setFamilyStatus(json.getString("spText"))
                    .setInterest(json.getJSONArray("interest"))
                    .setTargets(json.getJSONArray("targets"))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "user: " + user);
        getListener().onUpdate();
    }

    public User getUser() {
        return this.user;
    }

    public interface OnRenderProgess{
        void onRender();
    }

    private class DownloadThread extends Thread{

        private AtomicBoolean started;
        private OnRenderProgess mListener;

        public DownloadThread() {
            started = new AtomicBoolean(false);
        }

        public void setRenderListener(OnRenderProgess mListener) {
            this.mListener = mListener;
        }

        public boolean doWork(){
            return started.get();
        }

        @Override
        public void run() {
            while (true) {

                while (started.get()) {
                    mListener.onRender();

                    synchronized (this) {
                        try {
                            wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void startDownload(){
            synchronized (this){
                started.set(true);
                notify();
            }
        }

        public void stopDownload(){
            synchronized (this){
                started.set(false);
            }
        }
    }
}
