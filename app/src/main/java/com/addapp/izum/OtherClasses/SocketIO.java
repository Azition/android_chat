package com.addapp.izum.OtherClasses;

import android.util.Log;

import com.addapp.izum.Interface.OnModelUpdate;
import com.addapp.izum.Model.ModelProfile;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by Азат on 06.10.2015.
 */
public class SocketIO {

    private final String TAG = getClass().getSimpleName();

    public enum Model {
        LOGIN,
        MAIN,
        PROFILE,
        DIALOGS
    }

    private static SocketIO instance;
    private Socket mSocket;
    private HashMap<Model, OnModelUpdate> mapModels =
            new HashMap<>();
    private MainUserData userData = MainUserData.getInstance();
    private DownloadStream downloadImage;

    {
        try {
            mSocket = IO.socket("http://192.168.150.1:5000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private SocketIO() {
        Log.e("SocketIO", "_constructor");
        mSocket.on("onNextPart", onNextPart);
        mSocket.on("onAuthorized", onAuthorized);
        mSocket.on("onSetMainData", onSetMainData);
        mSocket.on("onGetProfileData", onGetProfileData);
        mSocket.on("onGetToken", onGetToken);
        mSocket.on("onGetCityList", onGetCityList);
        mSocket.on("onGetData", onGetData);
        mSocket.on("onDoneDownload", onDoneDownload);
        mSocket.connect();
    }

    public static SocketIO getInstance(){
        if(null == instance){
            instance = new SocketIO();
        }

        return instance;
    }

/****************************************************************************************
        Принимающие методы сокета
 *****************************************************************************************/

    private Emitter.Listener onAuthorized = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];

            Log.e("SocketIO", "onAuthorized: " + data);
            try {
                JSONObject obj = new JSONObject(data);
                userData.setToken(obj.getString("token"));
                userData.setId(obj.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapModels.containsKey(Model.LOGIN))
                mapModels.get(Model.LOGIN).onListen(null);
        }
    };

    private Emitter.Listener onSetMainData = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];

            Log.e("SocketIO", "onSetMainData: " + data);
            try {
                JSONObject obj = new JSONObject(data);
                userData.setAvatar(obj.getString("avatar"));
                userData.setUsername(obj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mapModels.containsKey(Model.MAIN))
                mapModels.get(Model.MAIN).onListen(null);
        }
    };

    private Emitter.Listener onGetProfileData = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];
            JSONObject obj = null;

            Log.e("SocketIO", "onGetProfileData: " + data);
            try {
                obj = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mapModels.containsKey(Model.PROFILE)) {
                Log.e("SocketIO", "Profile.onListener");
                mapModels.get(Model.PROFILE).onListen(obj);
            }
        }
    };

    private Emitter.Listener onNextPart = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];
            JSONObject obj = null;
            int state = -1;

            Log.e("SocketIO", "onNextPart: " + data);
            try {
                obj = new JSONObject(data);
                state = obj.getInt("state");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapModels.containsKey(Model.PROFILE)) {
                ((ModelProfile)mapModels.get(Model.PROFILE))
                        .setProgress(downloadImage.getPos(),
                                downloadImage.getMaxLength());
            }

            switch (state){
                case 0:
                    if (mapModels.containsKey(Model.PROFILE)) {
                        ((ModelProfile)mapModels.get(Model.PROFILE))
                                .onDownload();
                    }
                    downloadImage.nextPart();
                    break;
                case 1:
                    if (mapModels.containsKey(Model.PROFILE)) {
                        ((ModelProfile)mapModels.get(Model.PROFILE))
                                .onStopDownload();
                    }
                    downloadImage.stopDownload();
                    break;
            }

        }
    };

    private Emitter.Listener onGetToken = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];
            JSONObject obj = null;

            Log.e("SocketIO", "onGetToken: " + data);
            try {
                obj = new JSONObject(data);
                userData.setId(obj.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("onGetMainData", obj);

        }
    };

    private Emitter.Listener onGetData = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];
            Log.e("SocketIO", "onGetData: " + data);
            JSONObject obj = null;

            try {
                obj = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapModels.containsKey(Model.PROFILE)) {
                Log.e("SocketIO", "Profile.onUpdateData");
                ((ModelProfile)mapModels.get(Model.PROFILE))
                        .onUpdateData(obj);
            }
        }
    };

    private Emitter.Listener onGetCityList = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];
            Log.e("SocketIO", "onGetCityList: " + data);
            JSONArray obj = null;

            try {
                obj = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mapModels.containsKey(Model.PROFILE)) {
                Log.e("SocketIO", "Profile.onUpdateData");
                ((ModelProfile)mapModels.get(Model.PROFILE))
                        .onUpdateCity(obj);
            }
        }
    };

    private Emitter.Listener onDoneDownload = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            sendUserID(userData.getId());
            getProfileData(userData.getId());
        }
    };

/****************************************************************************************
        Отправляющие методы сокета
 *****************************************************************************************/

    public void sendAuthData(String number, String pass){
        JSONObject obj = new JSONObject();
        try{
            obj.put("phone", number);
            obj.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onAuth", obj);
    }

    public void sendUserID(int id){
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onGetMainData", obj);
    }

    public void addModel(Model key, OnModelUpdate model){
        if (!mapModels.containsKey(key))
            mapModels.put(key, model);
        else {
            if (!mapModels.get(key).equals(model)){
                mapModels.remove(key);
                mapModels.put(key, model);
            }
        }
    }

    public void getProfileData(int id){
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onGetProfileData", obj);
    }

    public void getData(int id){
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onGetData", obj);
    }

    public void getCityList(int regionID){
        JSONObject obj = new JSONObject();
        try {
            obj.put("regionID", regionID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onGetCity", obj);
    }

    public void sendURI(String URL){

        downloadImage = new DownloadStream(URL);
        downloadImage.setDaemon(true);
        downloadImage.setListener(new DownloadStream.OnSendDataListener() {
            @Override
            public void onSendData(String base64, int state) {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("userID", userData.getId());
                    obj.put("base64str", base64);
                    obj.put("state", state);
                    obj.put("fname", downloadImage.getURL());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("onSendAvatar", obj);
            }
        });
        downloadImage.start();
    }

    public void sendToken(String token){
        JSONObject obj = new JSONObject();
        try {
            obj.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onToken", obj);
    }

    public void setStatus(String status){
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", status);
            obj.put("id", userData.getId());
            obj.put("token", userData.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onChangeStatus", obj);
    }

    public void setProfileData(JSONObject obj){
        mSocket.emit("onChangeProfileData", obj);
    }

    public void getDialogs(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("userid", userData.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("onGetDialogs", obj);
    }

    public void connect(){
        if (!mSocket.connected())
            mSocket.connect();
    }

    public void disconnect(){
        mSocket.disconnect();
    }
}
