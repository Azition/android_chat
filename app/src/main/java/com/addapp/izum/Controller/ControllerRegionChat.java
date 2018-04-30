package com.addapp.izum.Controller;

import com.addapp.izum.AbstractClasses.CommonControllerChat;
import com.addapp.izum.Adapter.AdapterCommonChat;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.Model.ModelRegionChat;
import com.addapp.izum.Structure.PrivateItem;
import com.addapp.izum.View.ViewCommonChat;

import java.util.ArrayList;

/**
 * Created by ILDAR on 30.08.2015.
 */
public class ControllerRegionChat extends CommonControllerChat {

    private ModelRegionChat modelRegionChat;
    private AdapterCommonChat adapterCommonChat;

    public ControllerRegionChat(ModelRegionChat mRegionChat,
                                ViewCommonChat vCommonChat) {
        super(vCommonChat);
        modelRegionChat = mRegionChat;
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
