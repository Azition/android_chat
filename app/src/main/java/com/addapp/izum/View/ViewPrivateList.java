package com.addapp.izum.View;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.R;

/**
 * Created by ILDAR on 22.07.2015.
 */
public class ViewPrivateList {

    private View view;

    private LinearLayout mainLayout;
    private ListView privateList;

    public ViewPrivateList(View view){
        this.view = view;
        init();
        setup();
    }

    private void init() {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        privateList = new ListView(getContext());
    }

    private void setup() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        privateList.setLayoutParams(params);
        privateList.setPadding(0, 20, 0, 0);
        privateList.setDividerHeight(0);

        mainLayout.addView(privateList);
    }

    private Context getContext(){
        return view.getContext();
    }

    public View getView() {
        return view;
    }

    public ListView getPrivateList() {
        return privateList;
    }
}
