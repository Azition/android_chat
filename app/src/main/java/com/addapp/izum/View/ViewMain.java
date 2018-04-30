package com.addapp.izum.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.addapp.izum.Adapter.AdapterMainMenu;
import com.addapp.izum.OtherClasses.DrawerArrowDrawable;
import com.addapp.izum.OtherClasses.MainUserData;
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

    private Context context;
    private DrawerArrowDrawable drawerArrowDrawable;
    private AdapterMainMenu adapterMenu;

    private MainUserData userData = MainUserData.getInstance();

    private float offset;
    private boolean flipped;
    private int statusHeight;
    private boolean render;

    public ViewMain(Context context){
        this.context = context;


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
        statusHeight = MainUserData.percentToPix(8,MainUserData.HEIGHT);

        otherStatusLayout = (LinearLayout)mainView.findViewById(R.id.otherLayout);

        mDrawerLayout = (DrawerLayout) mainView.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mainView.findViewById(R.id.list_menu);

        final Resources resources = context.getResources();
        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.white));

        imageBar = (ImageView)mainView.findViewById(R.id.image_bar);
    }

    private void setup() {

        statusBarLayout.setBackgroundResource(R.color.izum_color);
//        statusBarLayout.setBackgroundColor(Color.RED);
        ((LinearLayout.LayoutParams)statusBarLayout.getLayoutParams()).height = statusHeight;
        statusBarLayout.setGravity(Gravity.LEFT);

        imageBar.setLayoutParams(new LinearLayout.LayoutParams(statusHeight, statusHeight));
        imageBar.setImageDrawable(drawerArrowDrawable);
        imageBar.setPadding(MainUserData.percentToPix(2, MainUserData.WIDTH), 0, MainUserData.percentToPix(4, MainUserData.WIDTH), 0);

        statusText = (TextView)mainView.findViewById(R.id.status_text);

        /* определенеие позиции статус текста в "шапке" */

        final int[] arrayWidth = new int[2];
        arrayWidth[0] = userData.getScreenWidth();

        statusBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int leftMargin;

                arrayWidth[1] = statusText.getWidth();

                leftMargin = arrayWidth[0] / 2 - arrayWidth[1] / 2 - statusHeight;

                ((LinearLayout.LayoutParams)statusText
                        .getLayoutParams())
                        .leftMargin = leftMargin;
                if(render) {
                    render = false;
                    statusBarLayout.requestLayout();
                }
            }
        });

        ((LinearLayout.LayoutParams)statusText.getLayoutParams()).gravity = Gravity.BOTTOM;
        ((LinearLayout.LayoutParams)statusText.getLayoutParams()).bottomMargin = (int)(statusHeight * 0.09);
        statusText.setTextColor(Color.WHITE);
//        statusText.setPadding(30, MainUserData.percentToPix(3,MainUserData.HEIGHT), 30, 0);
        statusText.setBackgroundColor(getContext().getResources().getColor(R.color.izum_color));
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        statusText.setTypeface(MainUserData.getCommonTextFont(context));

        adapterMenu = new AdapterMainMenu(context);
        int mDrawerListWidth = (int) (userData.getScreenWidth() * 0.9);
        ViewGroup.LayoutParams params = mDrawerList.getLayoutParams();
        params.width = mDrawerListWidth;
        mDrawerList.setLayoutParams(params);
        mDrawerList.setAdapter(adapterMenu);
        mDrawerList.setBackgroundResource(R.color.izum_color);

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

    public AdapterMainMenu getAdapterMenu() {
        return adapterMenu;
    }

    /*   **********  Setters  **********  */

    public void render() {
        this.render = true;
    }

    public void setStatusText(String text){
        this.statusText.setText(text);
    }

    public String getNameItem(int pos){
        if (pos == 0){
            return userData.getUsername();
        }
        return adapterMenu.getNameItem(pos);
    }
}
