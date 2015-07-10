package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerChat;
import com.addapp.izum.Model.ModelChat;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewChat;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class Chat extends Fragment{

    private ViewChat viewChat;
    private ModelChat modelChat;
    private ControllerChat controllerChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewChat = new ViewChat(inflater.inflate(R.layout.layout_chat, container, false), getChildFragmentManager());
        modelChat = new ModelChat();
        controllerChat = new ControllerChat();

        return viewChat.getView();
    }
}
