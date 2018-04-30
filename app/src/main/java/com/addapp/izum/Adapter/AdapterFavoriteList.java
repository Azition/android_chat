package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.CustomViewComponents.ImageOnlineIndication;
import com.addapp.izum.Fragment.SubFragment.FavoriteList;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 27.07.2015.
 */
public class AdapterFavoriteList extends BaseAdapter {

    private static final boolean DEBUG = false;

    private LayoutInflater inflater;
//    private ArrayList<FavoriteItem> objects;
    private int imageSize;

    private MainUserData userData = MainUserData.getInstance();

    public AdapterFavoriteList(Context ctx /*, ArrayList<FavoriteItem> objects*/) {
//        this.objects = objects;
        inflater = LayoutInflater.from(ctx);
        imageSize = MainUserData.getAvatarSize();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
//        return objects.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        FavoriteListHolder holder = null;
        
        if(v == null){

            if (DEBUG){
                Log.e("AdapterFavoriteList", "ItemWidth: " + viewGroup.getHeight());
            }
            
            v = inflater.inflate(R.layout.layout_clear, null);
            holder = initView(v, viewGroup.getContext());
            v.setTag(holder);
        } else {
            holder = (FavoriteListHolder) v.getTag();
        }

        Picasso.with(viewGroup.getContext())
                .load(R.drawable.photo_1)
                .resize(imageSize, imageSize)
                .centerCrop()
                .transform(new PicassoRound2Transformation())
                .into(holder.userImg);

        holder.userName.setText("Артур, 36");
        holder.userCityName.setText("Москва");

        return v;
    }

    private FavoriteListHolder initView(View view, Context ctx) {
        FavoriteListHolder newHolder = new FavoriteListHolder();
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout); // level 1
        mainLayout.setPadding(MainUserData.percentToPix(4, MainUserData.WIDTH),
                0, 16, 0);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundResource(R.drawable.activated_background_private_item);

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageOnlineIndication userImg = new ImageOnlineIndication(ctx); // level 3
        userImg.setLayoutParams(params);
        userImg.setOnline(true);
        params.setMargins(0, 10, 0, 10);

        LinearLayout contentLayout = new LinearLayout(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(params);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);

        View line = new View(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#DCDCDE"));

        LinearLayout textLayout = new LinearLayout(ctx); // level 3
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 0, 0, 0);
        params.weight = 1;
        params.gravity = Gravity.CENTER_VERTICAL;
        textLayout.setLayoutParams(params);
        textLayout.setGravity(Gravity.TOP);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView userName = new TextView(ctx); // level 5
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (DEBUG){
            Log.e("AdapterFavoriteList", "ImageSize: " + imageSize);
            Log.e("AdapterFavoriteList", "TopMargin_1: " + (imageSize + 20) / 100 * 50);
        }
        userName.setLayoutParams(params);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
//        userName.setBackgroundColor(Color.GREEN);
        userName.setTextColor(Color.BLACK);
        userName.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);

        TextView userCityName = new TextView(ctx); // level 5
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (imageSize * 0.1);
        if (DEBUG){
            Log.e("AdapterFavoriteList", "TopMargin_2: " + (imageSize + 20) / 100 * 35);
        }
        userCityName.setLayoutParams(params);
//        userCityName.setBackgroundColor(Color.BLUE);
        userCityName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(16));
        userCityName.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);
        userCityName.setTextColor(Color.parseColor("#7D7D7D"));

        textLayout.addView(userName);
        textLayout.addView(userCityName);

        contentLayout.addView(userImg);
        contentLayout.addView(textLayout);

        mainLayout.addView(contentLayout);
        mainLayout.addView(line);

        newHolder.userName = userName;
        newHolder.userCityName = userCityName;
        newHolder.userImg = userImg;

        return newHolder;
    }

    private class FavoriteListHolder{
        public ImageView userImg;
        public TextView userName;
        public TextView userCityName;
    }
}
