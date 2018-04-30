package com.addapp.izum.Adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.Structure.ProfileEditListItem;
import com.addapp.izum.Structure.User;

import java.util.ArrayList;

/**
 * Created by ILDAR on 29.07.2015.
 */
public class AdapterListEditProfile extends BaseAdapter {

    private LinearLayout mainLayout;
    private ArrayList<ProfileEditListItem> items = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private int widthTextView;

    @Override
    public int getCount() {
        return items.size();
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
        View v = view;
        EditListItemHolder holder = new EditListItemHolder();
        if(view == null){
            mainLayout = new LinearLayout(viewGroup.getContext());
            mainLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);

            widthTextView = viewGroup.getWidth() / 2;
            params = new LinearLayout.LayoutParams(widthTextView, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.header = new TextView(viewGroup.getContext());
            holder.header.setLayoutParams(params);
            params = new LinearLayout.LayoutParams(widthTextView, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.info = new TextView(viewGroup.getContext());
            holder.info.setLayoutParams(params);
            int paddingTopBot = MainUserData.percentToPix(2, MainUserData.HEIGHT);
            holder.header.setPadding(20, paddingTopBot, 0, paddingTopBot);
            holder.header.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(16));
            holder.info.setPadding(20, paddingTopBot, 0, paddingTopBot);
            holder.info.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(16));

            mainLayout.addView(holder.header);
            mainLayout.addView(holder.info);

            v = mainLayout;
            v.setTag(holder);
        } else {
            holder = (EditListItemHolder) v.getTag();
        }


        switch(i){
            case 0: case 3:
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                holder.header.setText(items.get(i).getHeader());
                holder.header.setTextColor(Color.GRAY);
                holder.header.setBackgroundColor(Color.parseColor("#f7f7f7"));
                holder.header.setLayoutParams(params);
                holder.info.setVisibility(View.GONE);
                break;
            default:
                params = new LinearLayout.LayoutParams(widthTextView, ViewGroup.LayoutParams.MATCH_PARENT);
                holder.header.setText(items.get(i).getHeader());
                holder.header.setTextColor(Color.BLACK);
                holder.header.setBackgroundColor(Color.WHITE);
                holder.header.setLayoutParams(params);
                if ( i == 2 )
                    holder.info.setText(items.get(i).getDate().toString());
                else
                    holder.info.setText(items.get(i).getInfo());
                holder.info.setTextColor(Color.parseColor("#CCCCCC"));
                holder.info.setBackgroundColor(Color.WHITE);
                holder.info.setLayoutParams(params);
                holder.info.setVisibility(View.VISIBLE);
                break;
        }

        return v;
    }

    private class EditListItemHolder{
        public TextView header;
        public TextView info;
    }

    /*   **********  Getters  **********  */

    public ArrayList<ProfileEditListItem> getItems() {
        return items;
    }

    /*   **********  Setters  **********  */

    public void setItems(User user) {
        items.clear();
        items.add(new ProfileEditListItem("ЛИЧНЫЕ ДАННЫЕ", ""));
        items.add(new ProfileEditListItem("Ваше имя", user.getName()));
        items.add(new ProfileEditListItem("Дата рождения",
                user.getBirthYear(),
                user.getBirthMonth(),
                user.getBirthDay()));
        items.add(new ProfileEditListItem("ОБЩАЯ ИНФОРМАЦИЯ", ""));
        items.add(new ProfileEditListItem("О себе", user.getUserInfo()));
        items.add(new ProfileEditListItem("Регион", user.getRegion()));
        items.add(new ProfileEditListItem("Город", user.getCity()));
        items.add(new ProfileEditListItem("Семья", user.getFamilyStatus()));
        items.add(new ProfileEditListItem("Ищу", user.getLookingFor()));
        items.add(new ProfileEditListItem("Цель знакомства", user.getTarget()));
        items.add(new ProfileEditListItem("Интересы", user.getInterest()));
        items.add(new ProfileEditListItem("Пол", user.getGender()));
    }
}
