package com.addapp.izum.Controller;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.addapp.izum.Interface.OnHideInterface;
import com.addapp.izum.Model.ModelChat;
import com.addapp.izum.View.ViewChat;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class ControllerChat {

    private ModelChat mChat;
    private ViewChat vChat;

    private int activeFragment = 1;

    public ControllerChat(ViewChat vChat, ModelChat mChat) {
        this.mChat = mChat;
        this.vChat = vChat;

        bindListener();
    }

    private void bindListener() {
        vChat.setFragment(activeFragment);
        vChat.getPagerTab().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                activeFragment = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    ((OnHideInterface) vChat.getFragments().get(activeFragment)).hideSmileAndKeyboard();
                }
            }
        });
        vChat.getChatOptions().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
