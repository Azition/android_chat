package com.addapp.izum.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.addapp.izum.Adapter.PagerAdapter2Fragments;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 11.08.2015.
 * Активити для выбора фотографии/снимка фотографии.
 */
public class ImagePickerActivity extends nl.changer.polypicker.ImagePickerActivity{

    private ViewPager mViewPager;
    private static int currentItem = 0;

    public static final int PHOTO = 0;
    public static final int GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new PagerAdapter2Fragments(getFragmentManager()));
        mViewPager.setCurrentItem(currentItem);
    }

    public static void setCurrentItem(int item){
        currentItem = item;
    }
}
