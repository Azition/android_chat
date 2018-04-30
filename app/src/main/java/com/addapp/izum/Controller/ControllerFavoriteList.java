package com.addapp.izum.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.addapp.izum.Adapter.AdapterFavoriteList;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelFavoriteList;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.View.ViewFavoriteList;

/**
 * Created by ILDAR on 27.07.2015.
 */
public class ControllerFavoriteList {

    private ViewFavoriteList viewFL;
    private ModelFavoriteList modelFL;
    private AdapterFavoriteList adapterFL;
    private OnShowFragment mListener;

    public ControllerFavoriteList(ViewFavoriteList viewFL, ModelFavoriteList modelFL, OnShowFragment mListener) {
        this.viewFL = viewFL;
        this.modelFL = modelFL;
        this.mListener = mListener;
        adapterFL = new AdapterFavoriteList(viewFL.getView().getContext());

        bindListener();
    }

    private void bindListener(){
        viewFL.getFavoriteList().setAdapter(adapterFL);
        viewFL.getFavoriteList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.showFragment(new ModelFindPeople.FindListItem("avatar", "100", "Артур", "36"), ControllerMain.PRIVATE_MESSAGING);
            }
        });
    }
}