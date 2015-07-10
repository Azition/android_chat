package com.addapp.izum.OtherClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;

import com.addapp.izum.R;

/**
 * Created by ILDAR on 15.06.2015.
 */
public class Configurations {

    private SharedPreferences userData;
    final String PREFERENCE = "preference";
    final String USERID = "userID";
    final String TOKEN = "token";
    final String AVATAR = "avatar";
    final int TEXTSIZE = 24;
    private Context context;

    public Configurations(Context context){
        this.context = context;
        userData = context.getSharedPreferences(PREFERENCE, Activity.MODE_PRIVATE);
    }

    /*
    *
    *   Setters
    *
    * */


    private void setConfig(String name, String key){
        Editor editor = userData.edit();
        editor.putString(name, key);
        editor.commit();

    }

    public void setToken(String token){
        setConfig(TOKEN, token);
    }

    public void setUserID(String userID){
        setConfig(USERID, userID);
    }

    public void setAvatar(String avatar){
        setConfig(AVATAR, avatar);
    }

    /*
    *
    *   Getters
    *
    * */

    public String getToken(){
        return userData.getString(TOKEN, "");
    }

    public String getUserID(){
        return userData.getString(USERID, "");
    }

    public String getAvatar(){
        return userData.getString(AVATAR, "");
    }

    public int getIzumColor(){
        return R.color.izum_color;
    }

    public int getIzumPressColor(){
        return R.color.izum_color_press;
    }

    public int getTextColor(){ return Color.WHITE; }

    public int getTextSize() { return TEXTSIZE;}

    public Typeface getFont(){
        return Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
    }
}
