package com.addapp.izum.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.addapp.izum.Interface.OnShowFragment;
import com.addapp.izum.Fragment.SubFragment.FavoriteList;
import com.addapp.izum.Fragment.SubFragment.PrivateList;
import com.addapp.izum.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

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
    private SmartTabLayout pagerTab;
    private FragmentManager fragmentManager;


    public ViewPrivateMessage(View view, FragmentManager fragmentManager, OnShowFragment mListener){
        this.view = view;
        this.fragmentManager = fragmentManager;
        init(mListener);
        setup();
    }

    private void init(OnShowFragment mListener) {
        fragments.add(0, new PrivateList(mListener));
        fragments.add(1, new FavoriteList(mListener));


        viewPager = (ViewPager)view.findViewById(R.id.pager_private);
        pagerAdapter = new PagerAdapter(fragmentManager);
        pagerTab = (SmartTabLayout)view.findViewById(R.id.pagerTab_private);

    }

    private void setup() {
        viewPager.setAdapter(pagerAdapter);
        pagerTab.setViewPager(viewPager);
        pagerTab.setBackgroundResource(R.color.izum_color);
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
                    return "Диалоги";
                case 1:
                    return "Мои друзья";
            }
            return "Title " + position;
        }
    }
}
