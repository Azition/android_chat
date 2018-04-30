package com.addapp.izum.Controller;

import com.addapp.izum.AbstractClasses.CommonControllerChat;
import com.addapp.izum.Adapter.AdapterCommonChat;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.Model.ModelCountryChat;
import com.addapp.izum.Structure.PrivateItem;
import com.addapp.izum.View.ViewCommonChat;

import java.util.ArrayList;

/**
 * Created by ILDAR on 30.08.2015.
 */
public class ControllerCountryChat extends CommonControllerChat {

    private ModelCountryChat mCountryChat;
    private AdapterCommonChat adapterCommonChat;

    public ControllerCountryChat(ModelCountryChat mCountryChat,
                                 ViewCommonChat viewCommonChat) {
        super(viewCommonChat);
        this.mCountryChat = mCountryChat;
    }

    @Override
    protected void setConfigView(ViewCommonChat viewCommonChat) {
        adapterCommonChat = new AdapterCommonChat(viewCommonChat.getContext(),
                new ArrayList<PrivateItem>());
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
