package com.addapp.izum.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.addapp.izum.Controller.ControllerLogin;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by ILDAR on 16.06.2015.
 * Активити для авторизации/регистрации пользователя
 * в приложении
 */
public class Login extends FragmentActivity {

    private SocketIO mSocket = SocketIO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();

        ControllerLogin controllerLogin =
                new ControllerLogin(this);

        setContentView(controllerLogin.getView());
    }

    public void close(){
        finish();
    }
}
