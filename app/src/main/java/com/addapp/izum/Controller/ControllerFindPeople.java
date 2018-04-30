package com.addapp.izum.Controller;

import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.addapp.izum.Adapter.AdapterFindGridContext;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.View.ViewFindPeople;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;

/**
 * Created by ILDAR on 24.06.2015.
 */
public class ControllerFindPeople {

    private ModelFindPeople modelFindPeople;
    private ViewFindPeople viewFindPeople;
    private AdapterFindGridContext adapterFindGridContext;


    private OnShowFragment mListener;

    public ControllerFindPeople(ModelFindPeople modelFindPeople, ViewFindPeople viewFindPeople, OnShowFragment mListener){
        this.modelFindPeople = modelFindPeople;
        this.viewFindPeople = viewFindPeople;
        this.mListener = mListener;

        adapterFindGridContext = new AdapterFindGridContext(viewFindPeople.getContext());
        modelFindPeople.update(this);
    }

    /*
    *       Привязка событии к компонентам
    */

    public void bindListeners(){

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
        viewFindPeople.getGridFindContext().setAdapter(adapterFindGridContext);

        viewFindPeople.getToRefreshGridView().setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                modelFindPeople.addPeople(ControllerFindPeople.this);
            }
        });

        viewFindPeople.getGridFindContext().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelFindPeople.FindListItem item = modelFindPeople.getArrayListItem().get(i);

                mListener.showFragment(item, ControllerMain.PROFILE);

                Log.e("ControllerFindPeople", item.getId() + "  " + item.getName() + "  " + item.getAge());
            }
        });
    }

    public void notifyAdapter(ArrayList<ModelFindPeople.FindListItem> arrayList){
        adapterFindGridContext.setArrayListItem(arrayList);
        if(viewFindPeople.getToRefreshGridView().isRefreshing()){
            viewFindPeople.getToRefreshGridView().onRefreshComplete();
        }
        Log.e("ControllerFindPeople","Обновилось");
    }
}
