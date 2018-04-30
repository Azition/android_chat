package com.addapp.izum.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.addapp.izum.AbstractClasses.CommonController;
import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.Activity.Login;
import com.addapp.izum.Main;
import com.addapp.izum.Model.ModelLogin;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewLogin;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ControllerLogin extends CommonController{

    private ModelLogin modelLogin;
    private ViewLogin viewLogin;

    private LayoutInflater inflater;

    public ControllerLogin(Context context){
        super(context);
        inflater = LayoutInflater.from(getContext());
        setComponents(new ViewLogin(inflater
                .inflate(R.layout.layout_relative_clear, null)), new ModelLogin());
    }

    @Override
    protected void bindListener(CommonView view, CommonModel model) {
        viewLogin = (ViewLogin)view;
        modelLogin = (ModelLogin)model;

        viewLogin.getBSingin().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = viewLogin.getSetPhoneNumber().getText().toString();
                String pass = viewLogin.getSetPassword().getText().toString();

                modelLogin.sendAuthData(number, pass);
            }
        });
    }

    @Override
    public void onUpdate() {
        Intent i = new Intent(getContext(), Main.class);
        getContext().startActivity(i);
        ((Login)getContext()).close();
    }
}
