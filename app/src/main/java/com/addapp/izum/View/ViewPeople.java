package com.addapp.izum.View;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.addapp.izum.Fragment.SubFragment.NewPeople;
import com.addapp.izum.Fragment.SubFragment.TopAllPeople;
import com.addapp.izum.Fragment.SubFragment.TopPeople;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILDAR on 13.08.2015.
 */
public class ViewPeople{

    private View view;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private SmartTabLayout pagerTab;
    private LinearLayout.LayoutParams params;
    private FragmentManager fragmentManager;

    private final List<Fragment> fragments = new ArrayList<>();

    private MainUserData userData = MainUserData.getInstance();

    public ViewPeople(View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        init();
        setup();
    }

    private void init() {

        fragments.clear();

        fragments.add(new NewPeople());
        fragments.add(new TopPeople());
        fragments.add(new TopAllPeople());

        viewPager = (ViewPager)view.findViewById(R.id.pager_chat);
        pagerAdapter = new PagerAdapter(fragmentManager);
        pagerTab = (SmartTabLayout)view.findViewById(R.id.pagerTab_chat);
    }

    private void setup() {

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        pagerTab.setViewPager(viewPager);
        pagerTab.setBackgroundResource(R.color.izum_color_unstate);
    }

    public View getView(){
        return this.view;
    }

    public Context getContext(){
        return view.getContext();
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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Новые";
                case 1:
                    return "Топ за сутки";
                case 2:
                    return "За все время";
            }
            return "Title " + position;
        }

    }
}
