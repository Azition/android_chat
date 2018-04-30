package com.addapp.izum.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.addapp.izum.Fragment.SubFragment.AnonChat;
import com.addapp.izum.Fragment.SubFragment.CityChat;
import com.addapp.izum.Fragment.SubFragment.CountryChat;
import com.addapp.izum.Fragment.SubFragment.RegionChat;
import com.addapp.izum.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class ViewChat {

    private View view;

    private final List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private SmartTabLayout pagerTab;
    private FragmentManager fragmentManager;
    private LinearLayout otherStatusLayout;
    private ImageView chatOptions;

    public ViewChat(View view, FragmentManager fragmentManager,
                    LinearLayout otherStatusLayout){
        this.view = view;
        this.fragmentManager = fragmentManager;
        this.otherStatusLayout = otherStatusLayout;
        init();
        setup();
    }

    private void init() {

        fragments.clear();

        fragments.add(new AnonChat());
        fragments.add(new CityChat());
        fragments.add(new RegionChat());
        fragments.add(new CountryChat());

        viewPager = (ViewPager)view.findViewById(R.id.pager_chat);
        pagerAdapter = new PagerAdapter(fragmentManager);
        pagerTab = (SmartTabLayout)view.findViewById(R.id.pagerTab_chat);
        chatOptions = new ImageView(view.getContext());
    }

    private void setup() {

        chatOptions.setBackground(view.getResources()
                .getDrawable(R.drawable.button));

        otherStatusLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        otherStatusLayout.addView(chatOptions);
        otherStatusLayout.setPadding(0, 0, 20, 0);

        otherStatusLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
                    @Override
                    public void onGlobalLayout() {
                        int iconSize = (int) (otherStatusLayout.getHeight() * 0.7);

                            Picasso.with(view.getContext())
                                    .load(R.drawable.options)
                                    .resize(iconSize, iconSize)
                                    .into(chatOptions);
                    }
                });


        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        pagerTab.setViewPager(viewPager);
        pagerTab.setBackgroundResource(R.color.izum_color_unstate);
    }

    public View getView(){
        return this.view;
    }

    public void setFragment(int num){
        viewPager.setCurrentItem(num);
    }

    public ImageView getChatOptions() {
        return chatOptions;
    }

    public SmartTabLayout getPagerTab() {
        return pagerTab;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Анонимный чат";
                case 1:
                    return "Город";
                case 2:
                    return "Регион";
                case 3:
                    return "Страна";
            }
            return "Title " + position;
        }

    }
}
