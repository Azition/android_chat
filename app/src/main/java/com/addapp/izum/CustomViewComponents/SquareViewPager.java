package com.addapp.izum.CustomViewComponents;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by ILDAR on 02.07.2015.
 */
public class SquareViewPager extends ViewPager {
    public SquareViewPager(Context context) {
        super(context);
    }

    public SquareViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
