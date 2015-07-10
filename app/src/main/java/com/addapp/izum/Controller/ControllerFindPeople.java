package com.addapp.izum.Controller;

import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.view.MotionEvent;
import android.view.View;

import com.addapp.izum.Adapter.AdapterFindGridContext;
import com.addapp.izum.Adapter.AdapterFindListContext;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.View.ViewFindPeople;

/**
 * Created by ILDAR on 24.06.2015.
 */
public class ControllerFindPeople {

    private ModelFindPeople modelFindPeople;
    private ViewFindPeople viewFindPeople;

    private Configurations config;

    public ControllerFindPeople(ModelFindPeople modelFindPeople, ViewFindPeople viewFindPeople){
        this.modelFindPeople = modelFindPeople;
        this.viewFindPeople = viewFindPeople;

        config = new Configurations(viewFindPeople.getContext());
    }

    /*
    *       Привязка событии к компонентам
    */

    public void bindListeners(){

        /*
        *       Раскрытие\Скрытие меню параметров при нажатии иконки
        * */
        viewFindPeople.getImageParamFind().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewFindPeople.getDrawerLayout().isDrawerVisible(GravityCompat.END)){
                    viewFindPeople.getDrawerLayout().closeDrawer(GravityCompat.END);
                } else {
                    viewFindPeople.getDrawerLayout().openDrawer(GravityCompat.END);
                }
            }
        });

        /*
        *       Эффект нажатия кнопки
        * */
        viewFindPeople.getImageParamFind().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        viewFindPeople.getImageParamFind().setBackgroundResource(config.getIzumPressColor());
                        break;
                    case MotionEvent.ACTION_UP:
                        viewFindPeople.getImageParamFind().setBackgroundResource(config.getIzumColor());
                        break;
                }
                return false;
            }
        });

        /*
        *   Раскрытие\Скрытие меню параметров при нажатии кнопки внизу справа
        * */
        viewFindPeople.getButtonParamFind().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewFindPeople.getDrawerLayout().isDrawerVisible(GravityCompat.END)){
                    viewFindPeople.getDrawerLayout().closeDrawer(GravityCompat.END);
                } else {
                    viewFindPeople.getDrawerLayout().openDrawer(GravityCompat.END);
                }
            }
        });

        /*
        *       Адаптер списка найденных людей
        * */
        viewFindPeople.getListFindContext().setAdapter(new AdapterFindGridContext(viewFindPeople.getContext()));
    }



}
