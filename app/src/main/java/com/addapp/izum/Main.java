package com.addapp.izum;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.addapp.izum.Controller.ControllerMain;
import com.addapp.izum.Model.ModelMain;
import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.View.ViewMain;


public class Main extends FragmentActivity{
    private Configurations configurations;

    private ViewMain viewMain;
    private ModelMain modelMain;
    private ControllerMain controllerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurations = new Configurations(this);

/*        if (configurations.getToken().equals("")){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        }*/

        viewMain = new ViewMain(this, this);
        modelMain = new ModelMain();

        controllerMain = new ControllerMain(modelMain, viewMain);
        controllerMain.bindDrawerListener();

        setContentView(controllerMain.getView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
