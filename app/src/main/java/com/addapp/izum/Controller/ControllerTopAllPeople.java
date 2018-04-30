package com.addapp.izum.Controller;

import com.addapp.izum.Adapter.AdapterListRation;
import com.addapp.izum.Model.ModelTopAllPeople;
import com.addapp.izum.View.ViewCommonPeople;

import java.util.ArrayList;

/**
 * Created by ILDAR on 01.09.2015.
 */
public class ControllerTopAllPeople {

    private ModelTopAllPeople mTopAllPeople;
    private ViewCommonPeople vCommonPeople;
    private AdapterListRation adapterListRation;

    public ControllerTopAllPeople(ModelTopAllPeople mTopAllPeople,
                               ViewCommonPeople vCommonPeople) {
        this.mTopAllPeople = mTopAllPeople;
        this.vCommonPeople = vCommonPeople;

        bindListener();
    }

    private void bindListener() {
        adapterListRation = new AdapterListRation(vCommonPeople.getContext(),
                new ArrayList<AdapterListRation.RationItem>());
        vCommonPeople.getRationList().setAdapter(adapterListRation);
    }
}
