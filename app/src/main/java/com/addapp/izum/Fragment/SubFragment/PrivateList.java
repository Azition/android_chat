package com.addapp.izum.Fragment.SubFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerPrivateList;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelPrivateList;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewPrivateList;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class PrivateList extends Fragment {

    private ControllerPrivateList controllerPrivateList;
    private ViewPrivateList viewPrivateList;
    private ModelPrivateList modelPrivateList;
    private OnShowFragment mListener;

    public PrivateList(OnShowFragment mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        modelPrivateList = new ModelPrivateList();
        viewPrivateList = new ViewPrivateList(inflater.inflate(R.layout.layout_clear, container, false));
        controllerPrivateList = new ControllerPrivateList(viewPrivateList, modelPrivateList, mListener);
        return viewPrivateList.getView();
    }
}
