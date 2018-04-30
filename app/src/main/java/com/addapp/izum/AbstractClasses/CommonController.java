package com.addapp.izum.AbstractClasses;

import android.content.Context;
import android.view.View;

/**
 * Created by Азат on 01.10.2015.
 * Создавалось для расширения классов контроллеров.
 * Содержит в себе объекты класса CommonView и CommonModel.
 * Метод bindListener подразумевает реализацию объединения
 * компонентов модели и представления.
 * Так же модели указывается функция OnUpdate которая содержится в
 * CommonModel.OnUpdateListener для обновления компонентов представления.
 */
public abstract class CommonController
    implements CommonModel.OnUpdateListener{

    private CommonView view;
    private CommonModel model;

    private Context context;

    public CommonController(Context context) {
        this.context = context;
    }

    protected void setComponents(CommonView commonView, CommonModel commonModel){
        view = commonView;
        model = commonModel;
        bindListener(this.view, this.model);
        model.setOnUpdateListener(this);
    }

    protected abstract void bindListener(CommonView view, CommonModel model);

    public View getView(){
        return view.getView();
    }

    protected Context getContext(){
        return context;
    }
}
