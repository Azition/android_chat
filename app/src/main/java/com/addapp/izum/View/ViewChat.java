package com.addapp.izum.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.Fragment.SubFragment.AnonChat;
import com.addapp.izum.Fragment.SubFragment.CityChat;
import com.addapp.izum.Fragment.SubFragment.CountryChat;
import com.addapp.izum.Fragment.SubFragment.RegionChat;
import com.addapp.izum.R;
import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class ViewChat {

    private View view;

    private final List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private PagerSlidingTabStrip pagerTab;
    private FragmentManager fragmentManager;

    private Configurations config;

    public ViewChat(View view, FragmentManager fragmentManager){
        this.view = view;
        this.fragmentManager = fragmentManager;
        init();
        setup();
    }

    private void init() {

        fragments.clear();

        fragments.add(new AnonChat());
        fragments.add(new CityChat());
        fragments.add(new RegionChat());
        fragments.add(new CountryChat());

        config = new Configurations(view.getContext());

        viewPager = (ViewPager)view.findViewById(R.id.pager_chat);
        pagerAdapter = new PagerAdapter(fragmentManager);
        pagerTab = (PagerSlidingTabStrip)view.findViewById(R.id.pagerTab_chat);
    }

    private void setup() {
        viewPager.setAdapter(pagerAdapter);
        pagerTab.setViewPager(viewPager);
        pagerTab.setBackgroundResource(config.getIzumColor());
        pagerTab.setTextColor(config.getTextColor());
        pagerTab.setTextSize(config.getTextSize());
        pagerTab.setTypeface(config.getFont(), 1);
    }

    public View getView(){
        return this.view;
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
