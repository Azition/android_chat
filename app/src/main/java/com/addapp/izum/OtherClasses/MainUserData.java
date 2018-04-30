package com.addapp.izum.OtherClasses;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.addapp.izum.Interface.MainFuctionInterface;

/**
 * Created by ILDAR on 13.07.2015.
 */
public class MainUserData {

    private static final boolean DEBUG = false;

	private String token;
    private String avatar;
    private int id;
    private String username;
    private static int screenWidth;
    private static int screenHeight;
    private static int avatarSize;
    private int keyboardHeight = 0;

    public final static int WIDTH = 0;
    public final static int HEIGHT = 1;


    private MainUserData(){
        avatar = "";
        username = "";
    }

    private static MainUserData self = null;

    public static MainUserData getInstance(){
        if (self == null){
            synchronized (MainUserData.class){
                if (self == null){
                    self = new MainUserData();
                }
            }
        }
        return self;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        if(DEBUG){
            Log.e("MainUserData", "ScreenHeight: " + screenHeight);
            Log.e("MainUserData", "SCALE_FONT_SIZE: " + screenHeight * (float)0.0014);
        }
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        if(DEBUG){
            Log.e("MainUserData", "ScreenWidth: " + screenWidth);
        }
        this.screenWidth = screenWidth;

        this.avatarSize = screenWidth / 100 * 22;
    }

    public static int percentToPix(int percent, int attr){
        switch (attr){
            case WIDTH:
                if(DEBUG){
                    Log.e("MainUserData", "ScreenWidth: " + screenWidth);
                    Log.e("MainUserData", "Result: " + screenWidth / 100 * percent);
                }
                return screenWidth / 100 * percent;
            case HEIGHT:
                if(DEBUG){
                    Log.e("MainUserData", "ScreenHeight: " + screenHeight);
                    Log.e("MainUserData", "Result: " + screenHeight / 100 * percent);
                }
                return screenHeight / 100 * percent;
            default:
                return 0;
        }
    }

    public static Typeface getCommonTextFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
    }

    public static float setTextSize(int size){
        return size * screenHeight * (float)0.0014;
    }

    public static int getAvatarSize() {
        return avatarSize;
    }

    public void setKeyboardHeight(int keyboardHeight){
        this.keyboardHeight = keyboardHeight;
    }

    public int getKeyboardHeight(){
        return keyboardHeight;
    }

    public static int findKeyboardHeight(final View view){

        final int[] previousHeightDiffrence = new int[1];

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);

                int screenHeight = view.getRootView().getHeight();
                int heightDifference = screenHeight - (r.bottom);

                if(heightDifference > 100){
                    previousHeightDiffrence[0] = heightDifference;
                } else {
                    previousHeightDiffrence[0] = 0;
                }
            }
        });

        return previousHeightDiffrence[0];
    }

    public static Typeface getFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
    }
}
