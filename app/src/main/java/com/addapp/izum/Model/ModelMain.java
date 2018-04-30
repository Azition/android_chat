package com.addapp.izum.Model;

import android.util.Log;

import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.Interface.OnModelUpdate;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ModelMain extends CommonModel implements OnModelUpdate {

    private SocketIO mSocket = SocketIO.getInstance();
    private MainUserData userData = MainUserData.getInstance();

    public ModelMain() {
        if (!userData.getToken().equals("")) {
            mSocket.addModel(SocketIO.Model.MAIN, this);
            mSocket.sendToken(userData.getToken());
        }
    }

    @Override
    public void onListen(Object obj) {
        Log.e("ModelMain", "onListen");
        getListener().onUpdate();
    }
}
