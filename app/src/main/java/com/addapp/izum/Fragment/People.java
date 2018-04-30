package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerPeople;
import com.addapp.izum.Model.ModelPeople;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewPeople;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class People extends Fragment {

    private ControllerPeople cPeople;
    private ViewPeople vPeople;
    private ModelPeople mPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vPeople = new ViewPeople(inflater.inflate(R.layout.layout_chat, container, false),
                getChildFragmentManager());
        cPeople = new ControllerPeople(mPeople, vPeople);

        return vPeople.getView();
    }
}
