package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.CustomViewComponents.ImageOnlineIndication;
import com.addapp.izum.Model.ModelFindPeople;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 01.07.2015.
 */
public class AdapterFindGridContext extends BaseAdapter {

    private LinearLayout mainLayout;
    private ViewCell viewCell;
    private ArrayList<ModelFindPeople.FindListItem> arrayListItem;
    private int imageSize;

    public AdapterFindGridContext(Context context){
        arrayListItem = new ArrayList<>();
        imageSize = MainUserData.percentToPix(30, MainUserData.WIDTH);
    }


    @Override
    public int getCount() {
        return arrayListItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
//        return mItems.get(position).drawableId;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        FindHolder holder = new FindHolder();

        ModelFindPeople.FindListItem item = arrayListItem.get(position);

        if(v == null){
            mainLayout = new LinearLayout(parent.getContext());
            mainLayout.setLayoutParams(
                    new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            mainLayout.setOrientation(LinearLayout.VERTICAL);

            viewCell = new ViewCell(parent.getContext(), mainLayout);

            viewCell.getGridCell(holder);
            v = mainLayout;
            v.setTag(holder);

        } else {
            holder = (FindHolder)v.getTag();
        }

        String letter;

        if (!item.getName().equals("")) {
            letter = item.getName().substring(0, 1);
        } else {
            letter = "";
        }

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .height(imageSize)
                .width(imageSize)
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(letter,
                        Color.parseColor(Utils.generateColorFemale()));

        if(item.getAvatar().equals("http://im.topufa.org/")){

            Picasso.with(parent.getContext())
                    .load(R.drawable.pixel)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation(drawable))
                    .placeholder(drawable)
                    .stableKey("" + item.getId())
                    .into(holder.userImg);

        } else {

            Picasso.with(parent.getContext())
                    .load(item.getAvatar())
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation())
                    .placeholder(drawable)
                    .stableKey("" + item.getId())
                    .into(holder.userImg);
        }

        holder.userName.setText(item.getName());

        return v;
    }

    public void setArrayListItem(ArrayList<ModelFindPeople.FindListItem> arrayListItem) {
        this.arrayListItem = arrayListItem;
        notifyDataSetChanged();
    }

    private class FindHolder{
        public ImageOnlineIndication userImg;
        public TextView userName;
    }

    private class ViewCell{

        private Context context;
        private LinearLayout mainLayout;
        private LinearLayout.LayoutParams params;

        public ViewCell(Context context, LinearLayout mainLayout) {
            this.context = context;
            this.mainLayout = mainLayout;
        }

        public void getGridCell(FindHolder tempHolder){

            tempHolder.userImg = new ImageOnlineIndication(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
            tempHolder.userImg.setLayoutParams(params);
            tempHolder.userImg.setOnline(true);

            tempHolder.userName = new TextView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            tempHolder.userName.setLayoutParams(params);
            tempHolder.userName.setSingleLine(true);
            tempHolder.userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(16));

            mainLayout.addView(tempHolder.userImg);
            mainLayout.addView(tempHolder.userName);
        }
    }
}
