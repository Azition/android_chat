package com.addapp.izum.Fragment.SubFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerRegionChat;
import com.addapp.izum.Interface.OnHideInterface;
import com.addapp.izum.Model.ModelRegionChat;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewCommonChat;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class RegionChat extends Fragment implements EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        OnHideInterface {

    private ViewCommonChat viewCommonChat;
    private ControllerRegionChat controllerRegionChat;
    private ModelRegionChat modelRegionChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewCommonChat = new ViewCommonChat(inflater.inflate(R.layout.layout_clear, container, false),
                getChildFragmentManager());
        modelRegionChat = new ModelRegionChat();
        controllerRegionChat = new ControllerRegionChat(modelRegionChat,
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
        controllerRegionChat.hideSmileAndKeyboard();
    }
}
