package com.addapp.izum.View;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.Adapter.AdapterPrivateMessaging;
import com.addapp.izum.CustomViewComponents.ChatBottom;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class ViewPrivateMessaging {

    private View view;

    private LinearLayout mainLayout;
    private ChatBottom chatBottom;
    private ListView privateMessages;
    private FragmentManager fragmentManager;

    public ViewPrivateMessaging (View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        init();
        setup();
    }

    private void init() {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        privateMessages = new ListView(getContext());
        chatBottom = new ChatBottom(getContext(), ChatBottom.PRIVATE_CHAT, fragmentManager);
    }

    private void setup() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        privateMessages.setLayoutParams(params);
        privateMessages.setDividerHeight(0);
        privateMessages.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        privateMessages.setVerticalScrollBarEnabled(false);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chatBottom.setLayoutParams(params);

        mainLayout.addView(privateMessages);
        mainLayout.addView(chatBottom);
    }

    public LinearLayout getMainLayout() {
        return mainLayout;
    }

    public Context getContext(){
        return view.getContext();
    }

    public View getView() {
        return view;
    }

    public ListView getPrivateMessages() {
        return privateMessages;
    }

    public ChatBottom getChatBottom() {
        return chatBottom;
    }

    public void showPhotosGrid(){
        chatBottom.showPhotosGrid();
    }

    public void hidePhotosGrid(){
        chatBottom.hidePhotosGrid();
    }
}
