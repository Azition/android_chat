package com.addapp.izum.CustomViewComponents;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by ILDAR on 07.07.2015.
 */
public class NestedListView extends ListView implements View.OnTouchListener, AbsListView.OnScrollListener {
    private int listViewTouchAction;
    private static final int MAXIMUM_LIST_ITEMS_VIEWABLE = 99;

    public NestedListView(Context context) {
        super(context);
        listViewTouchAction = -1;
        setOnScrollListener(this);
        setOnTouchListener(this);
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if(getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE){
            if (listViewTouchAction == MotionEvent.ACTION_MOVE){
                scrollBy(0, -1);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE){
            listViewTouchAction = motionEvent.getAction();
            if (listViewTouchAction == MotionEvent.ACTION_MOVE){
                scrollBy(0, 1);
            }
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newHeight = 0;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode != MeasureSpec.EXACTLY){
            ListAdapter listAdapter = getAdapter();
            if(listAdapter != null && !listAdapter.isEmpty()){
                int listPosition;
                for(listPosition = 0; listPosition < listAdapter.getCount()
                        && listPosition < MAXIMUM_LIST_ITEMS_VIEWABLE; listPosition++){
                    View listItem = listAdapter.getView(listPosition, null, this);
                    if(listItem instanceof ViewGroup){
                        listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                    listItem.measure(widthMeasureSpec, heightMeasureSpec);
                    newHeight += listItem.getMeasuredHeight();
                }
                newHeight += getDividerHeight() * listPosition;
            }
            if((heightMode == MeasureSpec.AT_MOST) && (newHeight > heightSize)){
                if(newHeight > heightSize){
                    newHeight = heightSize;
                }
            }
        } else {
            newHeight = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), newHeight);
    }
}