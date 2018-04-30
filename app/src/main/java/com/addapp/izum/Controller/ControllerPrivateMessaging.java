package com.addapp.izum.Controller;

import android.app.Activity;
import android.app.Service;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.addapp.izum.Adapter.AdapterPrivateMessaging;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.Model.ModelPrivateMessaging;
import com.addapp.izum.OtherClasses.KeyboardHandler;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.SoftKeyboard;
import com.addapp.izum.View.ViewPrivateMessaging;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class ControllerPrivateMessaging {

    private ViewPrivateMessaging viewFL;
    private ModelPrivateMessaging modelFL;
    private AdapterPrivateMessaging adapterFL;

    private SoftKeyboard softKeyboard;


    public ControllerPrivateMessaging(final ViewPrivateMessaging viewFL, ModelPrivateMessaging modelFL) {
        this.viewFL = viewFL;
        this.modelFL = modelFL;
        adapterFL = new AdapterPrivateMessaging();

        bindListener();
    }

    private void bindListener(){
        viewFL.getPrivateMessages().setAdapter(adapterFL);
        adapterFL.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                viewFL.getPrivateMessages().setSelection(adapterFL.getCount() - 1);
            }
        });
        viewFL.getChatBottom().setClickListener(new ChatBottom.OnClickListener() {
            @Override
            public void onSend(String text) {

                if (!text.equals("") || modelFL.getImages().getCountImages() > 0) {
                    adapterFL.addMessage(text, modelFL.getImages());
                    hidePhotos();
                }
            }
        });

        InputMethodManager im = (InputMethodManager)viewFL.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);

        final KeyboardHandler keyboardHandler = new KeyboardHandler();

        viewFL.getChatBottom().setKeyboardHandler(keyboardHandler);

        softKeyboard = new SoftKeyboard(viewFL.getMainLayout(), im);
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
                    userData.setKeyboardHeight(MainUserData.findKeyboardHeight(viewFL.getMainLayout()));
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

        ((Activity)viewFL.getContext())
                .getWindow()
                .setGravity(Gravity.TOP);

        viewFL.getView().setOnKeyListener(new View.OnKeyListener() {
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

    public void setListener(ChatBottom.OnClickAddPhotoListener clickAddPhotoListener){
        viewFL.getChatBottom().setClickAddPhotoListener(clickAddPhotoListener);
    }

    public void showPhotos(){
        viewFL.getChatBottom().setMyImages(modelFL.getImages());
        viewFL.showPhotosGrid();
    }

    public void hidePhotos(){
        modelFL.removeImage();
        viewFL.hidePhotosGrid();
    }
}