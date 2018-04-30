package com.addapp.izum.Model;

import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.Interface.OnModelUpdate;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ModelLogin extends CommonModel implements OnModelUpdate {

    private SocketIO mSocket = SocketIO.getInstance();

    public ModelLogin() {
        mSocket.addModel(SocketIO.Model.LOGIN, this);
    }

    public void sendAuthData(String number, String pass){
        mSocket.sendAuthData(number, pass);
    }

    @Override
    public void onListen(Object obj) {
        getListener().onUpdate();
    }
}
