package com.addapp.izum.Fragment.SubFragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerFavoriteList;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelFavoriteList;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewFavoriteList;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class FavoriteList extends Fragment {

    private ControllerFavoriteList controllerFL;
    private ModelFavoriteList modelFL;
    private ViewFavoriteList viewFL;
    private OnShowFragment mListener;

    public FavoriteList(OnShowFragment mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewFL =  new ViewFavoriteList(inflater.inflate(R.layout.layout_clear, container, false));
        controllerFL = new ControllerFavoriteList(viewFL, modelFL, mListener);
        return viewFL.getView();
    }
}
