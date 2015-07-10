package com.addapp.izum.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.Configurations;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 25.06.2015.
 */
public class AdapterFindListContext extends BaseAdapter {

    private Configurations config;

    private String[] sWords = {
            "World",
            "Android",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "Kitkat",
            "Lollipop"
    };

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        config = new Configurations(parent.getContext());

        if(convertView == null){
            convertView = new ImageView(parent.getContext());
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        int resId = parent.getResources().getIdentifier("photo_" + (position + 1), "drawable", parent.getContext().getPackageName());

        Picasso.with(parent.getContext())
                .load(resId)
                .fit()
                .centerCrop()
                .into((ImageView) convertView);

        return convertView;
    }

    public class FindListItem{
        public FindListItem(){

        }
    }
}
