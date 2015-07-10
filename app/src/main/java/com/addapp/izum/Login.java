package com.addapp.izum;

import android.app.Activity;
import android.os.Bundle;

import com.addapp.izum.Controller.ControllerLogin;
import com.addapp.izum.Model.ModelLogin;
import com.addapp.izum.View.ViewLogin;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModelLogin modelLogin = new ModelLogin("New text");
        ViewLogin viewLogin = new ViewLogin(this);

        ControllerLogin controllerLogin = new ControllerLogin(modelLogin, viewLogin);
        controllerLogin.control();

        setContentView(controllerLogin.getView());
    }
}
