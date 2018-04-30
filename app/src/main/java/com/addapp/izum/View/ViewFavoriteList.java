package com.addapp.izum.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.R;

/**
 * Created by ILDAR on 27.07.2015.
 */
public class ViewFavoriteList {

    private View view;

    private LinearLayout mainLayout;
    private ListView favoriteList;

    public ViewFavoriteList(View view) {
        this.view = view;
        init();
        setup();
    }

    private void init() {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        favoriteList = new ListView(getContext());
    }

    private void setup() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        favoriteList.setLayoutParams(params);
        favoriteList.setPadding(0, 20, 0, 0);
        favoriteList.setDividerHeight(0);

        mainLayout.addView(favoriteList);
    }

    private Context getContext(){
        return view.getContext();
    }

    public View getView() {
        return view;
    }

    public ListView getFavoriteList() {
        return favoriteList;
    }
}
