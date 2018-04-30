package com.addapp.izum.Controller;

import com.addapp.izum.Model.ModelPeople;
import com.addapp.izum.View.ViewPeople;

/**
 * Created by ILDAR on 13.08.2015.
 */
public class ControllerPeople {

    private ModelPeople mPeople;
    private ViewPeople vPeople;

    public ControllerPeople(ModelPeople mPeople, ViewPeople vPeople) {
        this.mPeople = mPeople;
        this.vPeople = vPeople;

        bindListener();
    }

    private void bindListener() {

    }
}
