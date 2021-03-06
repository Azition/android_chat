package com.addapp.izum.Fragment.SubFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerAnonChat;
import com.addapp.izum.Controller.ControllerCountryChat;
import com.addapp.izum.Interface.OnHideInterface;
import com.addapp.izum.Model.ModelAnonChat;
import com.addapp.izum.Model.ModelCountryChat;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewCommonChat;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class AnonChat extends Fragment implements EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        OnHideInterface {

    private ViewCommonChat viewCommonChat;
    private ControllerAnonChat cAnonChat;
    private ModelAnonChat mAnonChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewCommonChat = new ViewCommonChat(inflater.inflate(R.layout.layout_clear, container, false),
                getChildFragmentManager());
        mAnonChat = new ModelAnonChat();
        cAnonChat = new ControllerAnonChat(mAnonChat,
                viewCommonChat);

        return viewCommonChat.getView();
    }

    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(viewCommonChat.getChatBottom().getMsgText());
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(viewCommonChat.getChatBottom().getMsgText(), emojicon);
    }

    @Override
    public void hideSmileAndKeyboard() {
        cAnonChat.hideSmileAndKeyboard();
    }
}
