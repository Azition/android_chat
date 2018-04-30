package com.addapp.izum.View;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 26.08.2015.
 */
public class ViewCommonChat {

    private View view;

    private LinearLayout mainLayout;
    private ChatBottom chatBottom;
    private ListView chatMessages;
    private FragmentManager fragmentManager;

    public ViewCommonChat(View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        init();
        setup();
    }

    private void init() {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        chatMessages = new ListView(getContext());
        chatBottom = new ChatBottom(getContext(), ChatBottom.COMMON_CHAT, fragmentManager);
    }

    private void setup() {
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        chatMessages.setLayoutParams(params);
        chatMessages.setDividerHeight(0);
        chatMessages.setVerticalScrollBarEnabled(false);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chatBottom.setLayoutParams(params);

        mainLayout.addView(chatMessages);
        mainLayout.addView(chatBottom);
    }

    public Context getContext(){
        return view.getContext();
    }

    public View getView() {
        return view;
    }

    public LinearLayout getMainLayout() {
        return mainLayout;
    }

    public ListView getChatMessages() {
        return chatMessages;
    }

    public ChatBottom getChatBottom() {
        return chatBottom;
    }
}
