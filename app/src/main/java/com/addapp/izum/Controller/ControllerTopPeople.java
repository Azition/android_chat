package com.addapp.izum.Controller;

import com.addapp.izum.Adapter.AdapterListRation;
import com.addapp.izum.Model.ModelTopPeople;
import com.addapp.izum.View.ViewCommonPeople;

import java.util.ArrayList;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class ControllerTopPeople {

    private ModelTopPeople mTopPeople;
    private ViewCommonPeople vCommonPeople;
    private AdapterListRation adapterListRation;

    public ControllerTopPeople(ModelTopPeople mTopPeople,
                               ViewCommonPeople vCommonPeople) {
        this.mTopPeople = mTopPeople;
        this.vCommonPeople = vCommonPeople;

        bindListener();
    }

    private void bindListener(){
        adapterListRation = new AdapterListRation(vCommonPeople.getContext(),
                new ArrayList<AdapterListRation.RationItem>());
        vCommonPeople.getRationList().setAdapter(adapterListRation);
    }
}
