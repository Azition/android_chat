package com.addapp.izum.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.Controller.ControllerFindPeople;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewFindPeople;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class FindPeople extends Fragment {

    private ViewFindPeople viewFindPeople;
    private ModelFindPeople modelFindPeople;
    private ControllerFindPeople controllerFindPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewFindPeople = new ViewFindPeople(inflater.inflate(R.layout.layout_find, container, false));
        modelFindPeople = new ModelFindPeople();
        controllerFindPeople = new ControllerFindPeople(modelFindPeople, viewFindPeople);
        controllerFindPeople.bindListeners();

        return viewFindPeople.getView();
    }


}
