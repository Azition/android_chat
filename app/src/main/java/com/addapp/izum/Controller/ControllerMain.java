package com.addapp.izum.Controller;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.addapp.izum.AbstractClasses.CommonModel;
import com.addapp.izum.Fragment.Chat;
import com.addapp.izum.Fragment.FindPeople;
import com.addapp.izum.Fragment.Geolocation;
import com.addapp.izum.Fragment.IzumOptions;
import com.addapp.izum.Fragment.People;
import com.addapp.izum.Fragment.PrivateMessage;
import com.addapp.izum.Fragment.Profile;
import com.addapp.izum.Fragment.SubFragment.PrivateMessaging;
import com.addapp.izum.Fragment.UserSupport;
import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.Model.ModelMain;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewMain;
import com.addapp.izum.View.ViewProfile;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ControllerMain implements OnShowFragment, CommonModel.OnUpdateListener {

    private ModelMain modelMain;
    private ViewMain viewMain;
    private Handler handler = new Handler();

    private FragmentManager fragmentManager;

    public static final int PROFILE = 0;
    public static final int PRIVATE_MSG = 1;
    public static final int FIND = 2;
    public static final int CHAT = 3;
    public static final int PEOPLE = 4;
    public static final int GEOLOCATION = 5;
    public static final int SUPPORT = 8;
    public static final int OPTIONS = 7;
    public static final int PRIVATE_MESSAGING = 9;


    public ControllerMain(FragmentActivity fa){
        fragmentManager = fa.getSupportFragmentManager();
        viewMain = new ViewMain(fa);
        modelMain = new ModelMain();
        selectItem(CHAT);
        modelMain.setOnUpdateListener(this);
        bindDrawerListener();
    }

    public void bindDrawerListener(){
        viewMain.getDrawerList().setOnItemClickListener(new DrawerItemClickListener());
        viewMain.getImageBar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewMain.getDrawerLayout().isDrawerVisible(GravityCompat.START)) {
                    viewMain.getDrawerLayout().closeDrawer(GravityCompat.START);
                } else {
                    viewMain.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });


    }

    @Override
    public void showFragment(ModelFindPeople.FindListItem item, int attr) {
        openFragment(item, attr);
    }

    @Override
    public void rewriteTitle(int position) {
        viewMain.getOtherStatusLayout().removeAllViews();
        viewMain.setStatusText(viewMain.getNameItem(position));
    }

    @Override
    public void onUpdate() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                viewMain.getAdapterMenu().setCountMessage(5);
                viewMain.getAdapterMenu().notifyDataSetChanged();
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
        viewMain.render();

        Fragment fragment = null;
        switch (position){
            case PROFILE:
                fragment = new Profile(viewMain.getOtherStatusLayout(), ViewProfile.MY_PROFILE);
                break;
            case PRIVATE_MSG:
                fragment = new PrivateMessage(this);
                break;
            case FIND:
                fragment = new FindPeople(this);
                break;
            case CHAT:
                fragment = new Chat(viewMain.getOtherStatusLayout());
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

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            switch(position){
                case PEOPLE:
                    viewMain.setStatusText("Люди твоего региона");
                    break;
                default:
                    viewMain.setStatusText(viewMain.getNameItem(position));
                    break;
            }
            viewMain.getDrawerLayout().closeDrawer(viewMain.getDrawerList());
        }
    }

    private void openFragment(ModelFindPeople.FindListItem item, int attr){

        viewMain.getOtherStatusLayout().removeAllViews();
        Fragment fragment = null;

        switch(attr){
            case PROFILE:
                fragment = new Profile(viewMain.getOtherStatusLayout(), ViewProfile.OTHER_PROFILE);
                break;
            case PRIVATE_MESSAGING:
                fragment = new PrivateMessaging();
                break;
        }


        if(fragment != null){

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();

            viewMain.setStatusText(item.getName() + ", " + item.getAge());
        }
    }

    public View getMainView(){
        return viewMain.getMainView();
    }
}
