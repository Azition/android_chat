package com.addapp.izum.Controller;

import android.widget.GridView;

import com.addapp.izum.Adapter.AdapterFindGridContext;
import com.addapp.izum.Model.ModelNewPeople;
import com.addapp.izum.View.ViewNewPeople;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class ControllerNewPeople {

    private ModelNewPeople mNewPeople;
    private ViewNewPeople vNewPeople;
    private AdapterFindGridContext adapterFindGridContext;

    public ControllerNewPeople(ModelNewPeople mNewPeople,
                              ViewNewPeople vNewPeople) {
        this.mNewPeople = mNewPeople;
        this.vNewPeople = vNewPeople;

        adapterFindGridContext = new AdapterFindGridContext(vNewPeople.getContext());

        bindListener();
    }

    private void bindListener(){
        vNewPeople.getGridFindContext().setAdapter(adapterFindGridContext);

        vNewPeople.getToRefreshGridView().setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            }
        });

        adapterFindGridContext.setArrayListItem(mNewPeople.getArray());
    }

}
