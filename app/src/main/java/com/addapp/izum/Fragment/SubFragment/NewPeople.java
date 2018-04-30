package com.addapp.izum.Fragment.SubFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerNewPeople;
import com.addapp.izum.Model.ModelNewPeople;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewNewPeople;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class NewPeople extends Fragment {

    private ViewNewPeople vNewPeople;
    private ModelNewPeople mNewPeople;
    private ControllerNewPeople cNewPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vNewPeople = new ViewNewPeople(inflater
                .inflate(R.layout.layout_clear, container, false));
        mNewPeople = new ModelNewPeople();
        cNewPeople = new ControllerNewPeople(mNewPeople, vNewPeople);

        return vNewPeople.getView();
    }
}
