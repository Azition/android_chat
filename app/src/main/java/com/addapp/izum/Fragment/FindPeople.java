package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerFindPeople;
import com.addapp.izum.Controller.ControllerMain;
import com.addapp.izum.Interface.OnShowFragment;
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
    private OnShowFragment mListener;

    public FindPeople(OnShowFragment mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mListener.rewriteTitle(ControllerMain.FIND);

        viewFindPeople = new ViewFindPeople(inflater.inflate(R.layout.layout_find, container, false));
        modelFindPeople = new ModelFindPeople(container.getContext());
        controllerFindPeople = new ControllerFindPeople(modelFindPeople, viewFindPeople, mListener);
        controllerFindPeople.bindListeners();

        return viewFindPeople.getView();
    }


}
