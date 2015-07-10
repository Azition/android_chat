package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerPrivateMessage;
import com.addapp.izum.Model.ModelPrivateMessage;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewPrivateMessage;


/**
 * Created by ILDAR on 18.06.2015.
 */

public class PrivateMessage extends Fragment {

    private ViewPrivateMessage viewPrivateMessage;
    private ModelPrivateMessage modelPrivateMessage;
    private ControllerPrivateMessage controllerPrivateMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewPrivateMessage = new ViewPrivateMessage(inflater.inflate(R.layout.layout_private_message, container, false), getChildFragmentManager());
        modelPrivateMessage = new ModelPrivateMessage();
        controllerPrivateMessage = new ControllerPrivateMessage(modelPrivateMessage, viewPrivateMessage);

        return viewPrivateMessage.getView();
    }

}
