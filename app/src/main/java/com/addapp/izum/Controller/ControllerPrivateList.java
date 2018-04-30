package com.addapp.izum.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.addapp.izum.Adapter.AdapterPrivateList;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.Model.ModelPrivateList;
import com.addapp.izum.Structure.PrivateItem;
import com.addapp.izum.View.ViewPrivateList;

import java.util.ArrayList;

/**
 * Created by ILDAR on 22.07.2015.
 */
public class ControllerPrivateList {

    private final String TAG = getClass().getSimpleName();

    private ViewPrivateList viewPrivateList;
    private ModelPrivateList modelPrivateList;
    private AdapterPrivateList adapterPrivateList;
    private OnShowFragment mListener;

    public ControllerPrivateList(ViewPrivateList viewPrivateList, ModelPrivateList modelPrivateList, OnShowFragment mListener){
        this.modelPrivateList = modelPrivateList;
        this.viewPrivateList = viewPrivateList;
        this.mListener = mListener;
        adapterPrivateList = new AdapterPrivateList(viewPrivateList.getView().getContext(), new ArrayList<PrivateItem>());

        bindListener();
    }

    public void bindListener(){
        viewPrivateList.getPrivateList().setAdapter(adapterPrivateList);
        viewPrivateList.getPrivateList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.showFragment(new ModelFindPeople.FindListItem("avatar", "100", "Артур", "36"), ControllerMain.PRIVATE_MESSAGING);
            }
        });

        modelPrivateList.getDialogs();
    }
}
