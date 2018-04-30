package com.addapp.izum.Controller;

import com.addapp.izum.AbstractClasses.CommonControllerChat;
import com.addapp.izum.Adapter.AdapterCommonChat;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.Model.ModelAnonChat;
import com.addapp.izum.Structure.PrivateItem;
import com.addapp.izum.View.ViewCommonChat;

import java.util.ArrayList;

/**
 * Created by ILDAR on 30.08.2015.
 */
public class ControllerAnonChat extends CommonControllerChat {

    private ModelAnonChat mAnonChat;
    private AdapterCommonChat adapterCommonChat;

    public ControllerAnonChat(ModelAnonChat mAnonChat,
                              ViewCommonChat viewCommonChat) {
        super(viewCommonChat);
        this.mAnonChat = mAnonChat;
    }

    @Override
    protected void setConfigView(ViewCommonChat viewCommonChat) {
        adapterCommonChat = new AdapterCommonChat(viewCommonChat.getContext(),
                new ArrayList<PrivateItem>());
        adapterCommonChat.setAnonimImage(true);
        viewCommonChat.getChatMessages().setAdapter(adapterCommonChat);
        viewCommonChat.getChatBottom().setClickListener(new ChatBottom.OnClickListener() {
            @Override
            public void onSend(String text) {

                if (!text.equals("")) {
                    adapterCommonChat.addMessage(text);
                }
            }
        });
    }
}
