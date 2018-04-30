package com.addapp.izum.Controller;

import com.addapp.izum.AbstractClasses.CommonControllerChat;
import com.addapp.izum.Adapter.AdapterCommonChat;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.Model.ModelCityChat;
import com.addapp.izum.Structure.PrivateItem;
import com.addapp.izum.View.ViewCommonChat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ILDAR on 26.08.2015.
 */
public class ControllerCityChat extends CommonControllerChat {

    private ModelCityChat modelCityChat;
    private AdapterCommonChat adapterCommonChat;

    public ControllerCityChat(ModelCityChat modelCityChat,
                              ViewCommonChat viewCommonChat) {
        super(viewCommonChat);
        this.modelCityChat = modelCityChat;
        initModel();
    }

    private void initModel() {

        modelCityChat.setListener(new ModelCityChat.OnUpdateListener() {
            @Override
            public void onUpdate(JSONObject obj) {
                String text = null;
                try {
                    text = obj.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterCommonChat.addMessage(text);
            }
        });
    }

    @Override
    protected void setConfigView(ViewCommonChat viewCommonChat){
        adapterCommonChat = new AdapterCommonChat(viewCommonChat.getContext(),
                new ArrayList<PrivateItem>());
        viewCommonChat.getChatMessages().setAdapter(adapterCommonChat);
        viewCommonChat.getChatBottom().setClickListener(new ChatBottom.OnClickListener() {
            @Override
            public void onSend(String text) {

                if (!text.equals("")) {
                    adapterCommonChat.addMessage(text);
                    modelCityChat.sendText(text);
                }
            }
        });
    }
}
