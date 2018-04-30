package com.addapp.izum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.addapp.izum.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 11.07.2015.
 */
public class AdapterProfilePhotoGrid extends BaseAdapter {

    private LayoutInflater inflater;

    private String[] resImgName = {
            "photo_1",
            "photo_2",
            "photo_3"
    };

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

        inflater = (LayoutInflater)viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_profile_photo_grid, viewGroup, false);

        ImageView pic = (ImageView) itemView.findViewById(R.id.imagePhoto);

        Picasso.with(viewGroup.getContext())
                .load(viewGroup.getResources().getIdentifier(resImgName[i % 3], "drawable", viewGroup.getContext().getPackageName()))
                .fit()
                .centerCrop()
                .into(pic);

        return itemView;
    }
}