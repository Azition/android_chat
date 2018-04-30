package com.addapp.izum.Model;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by ILDAR on 26.08.2015.
 */
public class ModelCityChat{

    private static final int SEND_MSG = 0;

    private OnUpdateListener mListener;
    private Handler handler;

    private Socket mSocket;
    {
        try {
            Log.e("ModelCityChat", "_connect");
            mSocket = IO.socket("http://10.0.0.18:5000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ModelCityChat() {
        Log.e("ModelCityChat", "_constructor");
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on("onMessage", onMessage);
//        mSocket.on("onError", onError);
//        mSocket.connect();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case SEND_MSG:
                        mListener.onUpdate((JSONObject) msg.obj);
                        break;
                }
            }
        };
    }

    private Emitter.Listener onConnect = new Emitter.Listener(){

        @Override
        public void call(Object... args) {

            Log.e("ModelCityChat", "onConnect");
        }
    };

    private Emitter.Listener onMessage = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            if(args.length == 0)
                return;

            String data = (String) args[0];

            Log.e("ModelCityChat", "onMessage: " + data);

            JSONObject item = null;
            try {
                item = new JSONObject((String) args[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            handler.obtainMessage(SEND_MSG, item).sendToTarget();
        }
    };

    private Emitter.Listener onError = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            Log.e("ModelMain", "onError" );
            if(args.length == 0)
                return;

            String data = (String) args[0];

            Log.e("ModelMain", "onError: " + data);
        }
    };

    public void sendText(String msg){

        JSONObject obj = new JSONObject();
        try {
            obj.put("msg", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("ModelCityChat", "sendText: " + obj.toString());
        mSocket.emit("getMessage", obj);
    }

    public void setListener(OnUpdateListener mListener) {
        this.mListener = mListener;
    }

    public interface OnUpdateListener{
        void onUpdate(JSONObject obj);
    }
}
