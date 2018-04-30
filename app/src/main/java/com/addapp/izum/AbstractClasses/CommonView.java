package com.addapp.izum.AbstractClasses;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by ILDAR on 31.08.2015.
 * Создавалось для связывания с абстрактным классом CommonController.
 * Имеет абстрактные методы init и setup для инициализации и конфигурирования
 * компонентов представления.
 * Не окончательная реализация!!! Нужна доработка...
 */
public abstract class CommonView {

    private View view;
    protected FragmentManager fragmentManager;


    public CommonView(View view) {
        this(view, null);
    }

    public CommonView(View view, FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        this.view = view;
        init(this.view);
        setup();
    }

    protected abstract void init(View view);

    protected abstract void setup();

    public View getView() {
        return this.view;
    }

    public Context getContext(){
        return view.getContext();
    }
}
