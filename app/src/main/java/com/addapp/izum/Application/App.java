package com.addapp.izum.Application;

import android.app.Application;
import android.content.SharedPreferences;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by Азат on 06.10.2015.
 */
public class App extends Application {

    private static final String PREFS_NAME = "TOKEN";
    private SharedPreferences settings;

    private SocketIO socket = SocketIO.getInstance();
    private MainUserData userData = MainUserData.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userData.setToken(settings.getString(PREFS_NAME, ""));
    }
}
