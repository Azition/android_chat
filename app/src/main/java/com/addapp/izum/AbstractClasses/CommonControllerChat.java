package com.addapp.izum.AbstractClasses;

import android.app.Service;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.addapp.izum.OtherClasses.KeyboardHandler;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SoftKeyboard;
import com.addapp.izum.View.ViewCommonChat;

/**
 * Created by ILDAR on 30.08.2015.
 * Используется для расширения контроллера чата, и содержит в себе
 * функции необходимые для работы каждого чата.
 * В основном это функции контроля отображения клавиатуры
 * и смайликов. Так же содержит объект класса ViewCommonChat, в
 * дальнейшем передается дочернему классу через метод setConfigView.
 */
public abstract class CommonControllerChat {
    private ViewCommonChat viewCommonChat;

    private SoftKeyboard softKeyboard;
    private KeyboardHandler keyboardHandler;

    public CommonControllerChat(ViewCommonChat viewCommonChat) {
        this.viewCommonChat = viewCommonChat;
        bindListener();
    }

    private void bindListener() {

        setConfigView(viewCommonChat);

        InputMethodManager im = (InputMethodManager) viewCommonChat
                .getContext()
                .getSystemService(Service.INPUT_METHOD_SERVICE);

        keyboardHandler = new KeyboardHandler();

        viewCommonChat.getChatBottom().setKeyboardHandler(keyboardHandler);

        softKeyboard = new SoftKeyboard(viewCommonChat.getMainLayout(), im);
        keyboardHandler.setSoftKeyboard(softKeyboard);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {

            @Override
            public void onSoftKeyboardHide() {
                Log.e("ControllerPrivate", "onSoftKeyboardHide");
                keyboardHandler.setKeyboard(false);
                if (keyboardHandler.isSmileOpened()) {
                    keyboardHandler.setSmile(true);
                }
            }

            @Override
            public void onSoftKeyboardShow() {
                Log.e("ControllerPrivate", "onSoftKeyboardShow");
                MainUserData userData = MainUserData.getInstance();
                int height = userData.getKeyboardHeight();
                if (height == 0) {
                    userData.setKeyboardHeight(MainUserData.findKeyboardHeight(viewCommonChat.getMainLayout()));
                }
                keyboardHandler.setKeyboard(true);
            }
        });

        softKeyboard.setListener(new SoftKeyboard.OnFocusChangeListener() {
            @Override
            public void onFocusChange() {
                keyboardHandler.setSmile(false);
            }
        });

        viewCommonChat.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyboardHandler.isSmile()) {
                    keyboardHandler.setSmile(false);
                    keyboardHandler.setSmileOpened(false);
                    return true;
                }
                return false;
            }
        });
    }

    protected abstract void setConfigView(ViewCommonChat viewCommonChat);

    public void hideSmileAndKeyboard(){
        if (keyboardHandler.isKeyboard()){
            keyboardHandler.setKeyboard(false);
            softKeyboard.closeSoftKeyboard();
        }
        if (keyboardHandler.isSmile()){
            keyboardHandler.setSmile(false);
            keyboardHandler.setSmileOpened(false);
        }
    }
}
