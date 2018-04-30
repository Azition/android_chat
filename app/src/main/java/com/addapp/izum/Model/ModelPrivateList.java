package com.addapp.izum.Model;

import android.util.Log;

import com.addapp.izum.Interface.OnModelUpdate;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by ILDAR on 22.07.2015.
 */
public class ModelPrivateList implements OnModelUpdate {

    private final String TAG = getClass().getSimpleName();

    private SocketIO mSocket = SocketIO.getInstance();
    private MainUserData userData = MainUserData.getInstance();

    public ModelPrivateList() {
        mSocket.addModel(SocketIO.Model.DIALOGS, this);
    }

    public void getDialogs(){
        Log.e(TAG, "getDialogs");
        mSocket.getDialogs();
    }

    @Override
    public void onListen(Object obj) {

    }
}
