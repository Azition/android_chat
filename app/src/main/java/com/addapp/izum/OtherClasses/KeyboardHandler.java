package com.addapp.izum.OtherClasses;

import android.os.Handler;
import android.os.Message;

import com.addapp.izum.CustomViewComponents.ChatBottom;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ILDAR on 21.08.2015.
 */
public class KeyboardHandler{

    private static final int SMILE_HANDLE = 0;

    private boolean isKeyboard;
    private boolean isSmile;
    private boolean smileOpened;

    private ChatBottom chatBottom;
    private SoftKeyboard softKeyboard;

    private KeyboardHandlerThread keyboardHandlerThread;

    public KeyboardHandler() {
        this.keyboardHandlerThread = new KeyboardHandlerThread();
        this.keyboardHandlerThread.setDaemon(true);
        this.keyboardHandlerThread.start();
    }

    public boolean isSmile() {
        return isSmile;
    }

    public void setSmile(boolean isSmile) {
        this.isSmile = isSmile;
        if(isSmile){
            this.keyboardHandlerThread.nextThread();
        } else {
            this.keyboardHandlerThread.nextThread();
        }
    }

    public void hideKeyboard(){
        softKeyboard.closeSoftKeyboard();
    }

    public void setSoftKeyboard(SoftKeyboard softKeyboard) {
        this.softKeyboard = softKeyboard;
    }

    public boolean isKeyboard() {
        return isKeyboard;
    }

    public void setKeyboard(boolean isKeyboard) {
        this.isKeyboard = isKeyboard;
    }

    public ChatBottom getChatBottom() {
        return chatBottom;
    }

    public void setChatBottom(ChatBottom chatBottom) {
        this.chatBottom = chatBottom;
    }

    public boolean isSmileOpened() {
        return smileOpened;
    }

    public void setSmileOpened(boolean smileOpened) {
        this.smileOpened = smileOpened;
    }

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message m)
        {
            switch(m.what)
            {
                case SMILE_HANDLE:
                    if(isSmile()){
                        chatBottom.showSmiles();
                    } else {
                        chatBottom.hideSmiles();
                    }
                    break;
            }
        }
    };

    private class KeyboardHandlerThread extends Thread{

        private AtomicBoolean started;

        public KeyboardHandlerThread() {
            started = new AtomicBoolean(true);
        }

        @Override
        public void run() {
            while(started.get()){

                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                if(started.get())
                    mHandler.obtainMessage(SMILE_HANDLE).sendToTarget();
            }
        }

        public void nextThread(){
            synchronized (this){
                notify();
            }
        }

        public void stopThread(){
            synchronized (this){
                started.set(false);
                notify();
            }
        }
    }
}
