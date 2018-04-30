package com.addapp.izum.Fragment.SubFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerTopPeople;
import com.addapp.izum.Model.ModelTopPeople;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewCommonPeople;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class TopPeople extends Fragment {

    private ViewCommonPeople vCommonPeople;
    private ModelTopPeople mTopPeople;
    private ControllerTopPeople cTopPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vCommonPeople = new ViewCommonPeople(inflater
                .inflate(R.layout.layout_relative_clear, container, false));
        mTopPeople = new ModelTopPeople();
        cTopPeople = new ControllerTopPeople(mTopPeople, vCommonPeople);

        return vCommonPeople.getView();
    }
}
