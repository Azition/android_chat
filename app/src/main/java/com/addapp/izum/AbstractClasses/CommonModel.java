package com.addapp.izum.AbstractClasses;

/**
 * Created by Азат on 01.10.2015.
 * Создавалось для связывания с абстрактным классом CommonController.
 * Не окончательная реализация!!! Нужна доработка...
 */
public abstract class CommonModel {

    private OnUpdateListener mListener;

    public void setOnUpdateListener(OnUpdateListener listener){
        mListener = listener;
    }

    protected OnUpdateListener getListener(){
        return mListener;
    }

    public interface OnUpdateListener {
        void onUpdate();
    }
}