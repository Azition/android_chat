package com.addapp.izum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import com.addapp.izum.Activity.Login;
import com.addapp.izum.Controller.ControllerMain;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SocketIO;

/**
 * Created by Азат on 01.10.2015.
 * Основной класс в котором выполняется инициализация
 * отображения фрагментов. Представление и логика связаны в
 * классе ControllerMain.
 */


public class Main extends FragmentActivity{

    private static final String PREFS_NAME = "TOKEN";
    private SocketIO mSocket = SocketIO.getInstance();

    private ControllerMain controllerMain;

    private MainUserData userData = MainUserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/********************************************************
 *       Убираем заголовок
 *********************************************************/

        requestWindowFeature(Window.FEATURE_NO_TITLE);

/********************************************************
 *       Получаем данные экрана( разрешение )
 *********************************************************/

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        userData.setScreenWidth(metrics.widthPixels);
        userData.setScreenHeight(metrics.heightPixels);
        userData.setKeyboardHeight((int) getResources().getDimension(
                R.dimen.keyboard_height));

/********************************************************
 *       Если токен пустой, перебрасываем в окно логина
 *********************************************************/

        Log.e("Main", "token: " + userData.getToken());

/*        if (userData.getToken().equals("")){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        }*/

/********************************************************
 *       Запуск основную программу
 *********************************************************/

        controllerMain = new ControllerMain(this);

        setContentView(controllerMain.getMainView());
    }

    @Override
    protected void onStop() {
        super.onStop();

/********************************************************
 *       Сохраняем токен
 *********************************************************/

        saveToken();
    }

    @Override
    protected void onPause() {
        super.onPause();

/********************************************************
 *       Сохраняем токен
 *********************************************************/

        saveToken();
    }

/********************************************************
 *       Функция сохранения токена
 *********************************************************/

    private void saveToken(){
        SharedPreferences settings =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_NAME, userData.getToken());
        editor.commit();
    }

    public void close(){
        mSocket.disconnect();
        finish();
    }
}
