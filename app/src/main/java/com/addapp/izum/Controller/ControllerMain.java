package com.addapp.izum.Controller;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.addapp.izum.Fragment.Chat;
import com.addapp.izum.Fragment.FindPeople;
import com.addapp.izum.Fragment.Geolocation;
import com.addapp.izum.Fragment.IzumOptions;
import com.addapp.izum.Fragment.People;
import com.addapp.izum.Fragment.PrivateMessage;
import com.addapp.izum.Fragment.Profile;
import com.addapp.izum.Fragment.UserSupport;
import com.addapp.izum.Model.ModelMain;
import com.addapp.izum.OtherClasses.NotificationService;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewMain;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ControllerMain {

    private ModelMain modelMain;
    private ViewMain viewMain;

    private FragmentManager fragmentManager;

    private static final int PROFILE = 0;
    private static final int PRIVATE_MSG = 1;
    private static final int FIND = 2;
    private static final int CHAT = 3;
    private static final int PEOPLE = 4;
    private static final int GEOLOCATION = 5;
    private static final int SUPPORT = 7;
    private static final int OPTIONS = 8;

    public ControllerMain(ModelMain modelMain, ViewMain viewMain){
        this.modelMain = modelMain;
        this.viewMain = viewMain;
        selectItem(CHAT);
    }

    public void bindDrawerListener(){
        viewMain.getDrawerList().setOnItemClickListener(new DrawerItemClickListener());
        viewMain.getImageBar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewMain.getDrawerLayout().isDrawerVisible(GravityCompat.START)){
                    viewMain.getDrawerLayout().closeDrawer(GravityCompat.START);
                } else {
                    viewMain.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        viewMain.getOtherStatusLayout().removeAllViews();

        Fragment fragment = null;
        switch (position){
            case PROFILE:
                fragment = new Profile(viewMain.getOtherStatusLayout());
                break;
            case PRIVATE_MSG:
                fragment = new PrivateMessage();
                break;
            case FIND:
                fragment = new FindPeople();
                break;
            case CHAT:
                fragment = new Chat();
                break;
            case PEOPLE:
                fragment = new People();
                break;
            case GEOLOCATION:
                fragment = new Geolocation();
                break;
            case SUPPORT:
                fragment = new UserSupport();
                break;
            case OPTIONS:
                fragment = new IzumOptions();
                break;
        }

        if(fragment != null){

            fragmentManager = viewMain.getActivity().getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            viewMain.setStatusText(viewMain.getNameItem(position));
            viewMain.getDrawerLayout().closeDrawer(viewMain.getDrawerList());
        }
    }

    public View getView(){
        return viewMain.getMainView();
    }
}
