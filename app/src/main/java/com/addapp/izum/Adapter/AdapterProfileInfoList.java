package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.Structure.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 11.07.2015.
 */
public class AdapterProfileInfoList extends BaseAdapter {

    private int imageSize;
    private User user;
    private ArrayList<String> userInfo = new ArrayList<>();

    public AdapterProfileInfoList() {
        imageSize = MainUserData.percentToPix(12, MainUserData.WIDTH);
    }

    @Override
    public int getCount() {
        return 6;
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

        if (i == 0){
            return initHeader(viewGroup.getContext());
        } else {
            return initInfo(viewGroup.getContext(), i, getCount());
        }
    }

    private View initHeader(Context context) {

        LinearLayout mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params;
        mainLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.setBackgroundColor(Color.WHITE);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 10);

        TextView headerText = new TextView(context);
        headerText.setLayoutParams(params);
        headerText.setText("Информация");
        headerText.setTextColor(Color.BLACK);
        headerText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(32));
        headerText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf"));


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 30);

        TextView infoText = new TextView(context);
        infoText.setLayoutParams(params);
        infoText.setGravity(Gravity.CENTER_HORIZONTAL);
        if (user != null)
            infoText.setText(user.getUserInfo());
        infoText.setTextColor(Color.BLACK);
        infoText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(16));
        infoText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf"));

        mainLayout.addView(headerText);
        mainLayout.addView(infoText);

        return mainLayout;
    }


    private View initInfo(Context context, int pos, int count) {

        String[] resImgName = {
            "region",
            "semia",
            "ichu",
            "celi",
            "interesy"
        };

        String[] infoHeader = {
            "Регион",
            "Семейное положение",
            "Ищу",
            "Цель знакомства",
            "Интересы"
        };

/*        String[] infoUser = {
            "Башкортостан, Уфа",
            "Есть девушка",
            "Девушку",
            "Спорт",
            "Искусство, Спорт"
        };*/

        LinearLayout itemLayout = new LinearLayout(context);

        itemLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setBackgroundColor(Color.parseColor("#f5f5f8"));

        LinearLayout mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params;

        mainLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainLayout.setPadding(50, 30, 50, 30);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setGravity(Gravity.CENTER_VERTICAL);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView iconPic = new ImageView(context);
        iconPic.setLayoutParams(params);

        Picasso.with(context)
                .load(context.getResources().getIdentifier(resImgName[pos - 1], "drawable", context.getPackageName()))
                .resize(imageSize, imageSize)
                .into(iconPic);

        LinearLayout infoLayout = new LinearLayout(context);

        infoLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        infoLayout.setPadding(30, 0, 0, 0);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setGravity(Gravity.LEFT);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView headerText = new TextView(context);
        headerText.setLayoutParams(params);
        headerText.setText(infoHeader[pos - 1]);
        headerText.setTextColor(Color.parseColor("#3d8ada"));
        headerText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        headerText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf"));


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView infoText = new TextView(context);
        infoText.setLayoutParams(params);
        if (userInfo.size() != 0)
            infoText.setText(userInfo.get(pos-1));
        infoText.setTextColor(Color.parseColor("#3d8ada"));
        infoText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        infoText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf"));

        infoLayout.addView(headerText);
        infoLayout.addView(infoText);

        mainLayout.addView(iconPic);
        mainLayout.addView(infoLayout);

        itemLayout.addView(mainLayout);

        if (pos > 0 && pos < count - 1){
            View line = new View(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            params.setMargins(40, 0, 40, 0);
            line.setLayoutParams(params);
            line.setBackgroundColor(Color.parseColor("#DCDCDE"));
            itemLayout.addView(line);
        }

        return itemLayout;
    }

    public void setUserInfo(User user){
        this.user = user;
        userInfo.clear();
        userInfo.add(user.getCountry() + ", " +
                user.getRegion() + ", " + user.getCity());
        userInfo.add(user.getFamilyStatus());
        userInfo.add(user.getLookingFor());
        userInfo.add(user.getTarget());
        userInfo.add(user.getInterest());
    }
}
