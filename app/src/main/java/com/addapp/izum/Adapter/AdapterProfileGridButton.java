package com.addapp.izum.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.CustomViewComponents.CustomLinearLayout;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewProfile;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 11.07.2015.
 */
public class AdapterProfileGridButton extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] resImgName = {
        "like",
        "gifts",
        "message_add",
        "money"
    };
    private int measuredHeight;
    private int imageSize;


    private int attr;

    public AdapterProfileGridButton(int attr, int measuredHeight) {
        this.attr = attr;
        this.measuredHeight = measuredHeight;
        this.imageSize = (int) (measuredHeight * 0.6f);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v  = view;

        if (v == null) {
            inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_clear, viewGroup, false);
            v.findViewById(R.id.main_layout).setMinimumHeight(measuredHeight);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ImageView pic = new ImageView(viewGroup.getContext());
            pic.setLayoutParams(params);
            TextView txt = new TextView(viewGroup.getContext());
            txt.setLayoutParams(params);

            txt.setText("100");

            if (i == getCount() - 1){
                if (attr == ViewProfile.MY_PROFILE){
                    Picasso.with(viewGroup.getContext())
                            .load(viewGroup.getResources().getIdentifier(resImgName[getCount()], "drawable", viewGroup.getContext().getPackageName()))
                            .resize(imageSize, 0)
                            .into(pic);
                } else{
                    Picasso.with(viewGroup.getContext())
                            .load(viewGroup.getResources().getIdentifier(resImgName[i], "drawable", viewGroup.getContext().getPackageName()))
                            .resize(imageSize, 0)
                            .into(pic);
                }
            } else {
                Picasso.with(viewGroup.getContext())
                        .load(viewGroup.getResources().getIdentifier(resImgName[i], "drawable", viewGroup.getContext().getPackageName()))
                        .resize(imageSize, 0)
                        .into(pic);
            }
            ((LinearLayout)v.findViewById(R.id.main_layout)).addView(pic);
            ((LinearLayout)v.findViewById(R.id.main_layout)).addView(txt);

            ((LinearLayout)v.findViewById(R.id.main_layout)).setOrientation(LinearLayout.HORIZONTAL);
            ((LinearLayout)v.findViewById(R.id.main_layout)).setGravity(Gravity.CENTER);
        }

        return v;
    }
}
