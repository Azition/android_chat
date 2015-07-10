package com.addapp.izum.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.Fragment.SubFragment.FavoriteList;
import com.addapp.izum.Fragment.SubFragment.PrivateList;
import com.addapp.izum.R;
import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILDAR on 18.06.2015.
 */
public class ViewPrivateMessage {

    private View view;

    private final List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private PagerSlidingTabStrip pagerTab;
    private FragmentManager fragmentManager;

    private Configurations config;

    public ViewPrivateMessage(View view, FragmentManager fragmentManager){
        this.view = view;
        this.fragmentManager = fragmentManager;
        init();
        setup();
    }

    private void init() {
        fragments.add(0, new PrivateList());
        fragments.add(1, new FavoriteList());

        config = new Configurations(view.getContext());

        viewPager = (ViewPager)view.findViewById(R.id.pager_private);
        pagerAdapter = new PagerAdapter(fragmentManager);
        pagerTab = (PagerSlidingTabStrip)view.findViewById(R.id.pagerTab_private);

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
            //return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Сообщения";
                case 1:
                    return "Друзья";
            }
            return "Title " + position;
        }

    }
}
