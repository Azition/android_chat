package com.addapp.izum.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.addapp.izum.Adapter.AdapterMainMenu;
import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.OtherClasses.DrawerArrowDrawable;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class ViewMain {
    private LayoutInflater mainLayout;
    private View mainView;
    private LinearLayout statusBarLayout;
    private LinearLayout otherStatusLayout;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView imageBar;
    private TextView statusText;

    private FragmentActivity activity;
    private Context context;
    private DrawerArrowDrawable drawerArrowDrawable;
    private Configurations config;
    private AdapterMainMenu adapterMenu;

    private float offset;
    private boolean flipped;

    public ViewMain(FragmentActivity activity, Context context){
        this.context = context;
        this.activity = activity;

        config = new Configurations(context);

        init();         // инициализация компонентов представления
        /*
        *       statusBarLayout - слой статус бара, в него входит imageBar и statusText
        *
        *       mDrawerLayout - слой меню, он содержит mDrawerList
        *
        * */

        setup();        // настройка компонентов
    }

    private void init() {
        mainLayout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = mainLayout.inflate(R.layout.layout_main, null);

        statusBarLayout = (LinearLayout)mainView.findViewById(R.id.status_bar);
        statusBarLayout.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        statusText = (TextView)mainView.findViewById(R.id.status_text);
        statusText.setPadding(30, 0, 30, 0);
        statusText.setTextSize(20);
        statusText.setTypeface(config.getFont());

        otherStatusLayout = (LinearLayout)mainView.findViewById(R.id.otherLayout);

        mDrawerLayout = (DrawerLayout) mainView.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mainView.findViewById(R.id.list_menu);
        mDrawerList.setBackgroundResource(config.getIzumColor());
        mDrawerList.setScrollContainer(false);

        final Resources resources = context.getResources();
        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.white));

        imageBar = (ImageView)mainView.findViewById(R.id.image_bar);
    }

    private void setup() {

        statusBarLayout.setBackgroundResource(config.getIzumColor());

        imageBar.setImageDrawable(drawerArrowDrawable);
        imageBar.setPadding(25, 25, 25, 25);

        statusText.setTextColor(Color.WHITE);

        adapterMenu = new AdapterMainMenu(context);

        mDrawerList.setAdapter(adapterMenu);
        mDrawerList.setBackgroundResource(config.getIzumColor());

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener(){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                if(slideOffset >= .995){
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else{
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });
    }

    /*   **********  Getters  **********  */

    public Context getContext(){
        return this.context;
    }

    public FragmentActivity getActivity(){
        return this.activity;
    }

    public View getMainView(){
        return mainView;
    }

    public ListView getDrawerList(){
        return this.mDrawerList;
    }

    public ImageView getImageBar(){
        return this.imageBar;
    }

    public DrawerLayout getDrawerLayout(){
        return this.mDrawerLayout;
    }

    public LinearLayout getOtherStatusLayout(){
        return this.otherStatusLayout;
    }

    /*   **********  Setters  **********  */

    public void setStatusText(String text){
        this.statusText.setText(text);
    }

    public String getNameItem(int pos){
        return adapterMenu.getNameItem(pos);
    }
}
