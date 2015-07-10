package com.addapp.izum.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.OtherClasses.PicassoRoundTransformation;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 16.06.2015.
 */
public class AdapterMainMenu extends BaseAdapter{

    private Context context;
    private ArrayList<ItemMenu> items = new ArrayList<>();
    private LayoutInflater itemInflater;
    private Configurations config;
    private int photoImageSize = 160; /* image size = photoImageSize * photoImageSize */
    private int iconSize = 64; /* icon size = iconSize * iconSize */

    public AdapterMainMenu(Context context){
        this.context = context;

        config = new Configurations(context);

        items.add(new ItemMenu(config.getAvatar(), "userName"));
        items.add(new ItemMenu("message", "Сообщения"));
        items.add(new ItemMenu("find", "Поиск"));
        items.add(new ItemMenu("chat", "Чат"));
        items.add(new ItemMenu("people", "Люди"));
        items.add(new ItemMenu("geolocation", "Геолокация"));
        items.add(new ItemMenu("", ""));
        items.add(new ItemMenu("support", "Техподдержка"));
        items.add(new ItemMenu("options", "Настройки"));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ItemMenuHolder holder = new ItemMenuHolder();

        if (convertView == null){
            itemInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = itemInflater.inflate(R.layout.item_drawer_list, null);

            holder.icon = (ImageView)v.findViewById(R.id.icon);
            holder.text = (TextView)v.findViewById(R.id.item_text);
            holder.text.setTypeface(config.getFont());
            holder.itemLayout = (LinearLayout)v.findViewById(R.id.item_layout);

            v.setTag(holder);
        }
        else{
            holder = (ItemMenuHolder) v.getTag();
        }

        ItemMenu itemMenu = items.get(position);

        if (position == 0){
            if (itemMenu.image.equals(""))
                    Picasso.with(context)
                        .load(R.drawable.nophoto)
                        .transform(new PicassoRoundTransformation())
                        .resize(photoImageSize, photoImageSize)
                        .into(holder.icon);
            else
                    Picasso.with(context)
                        .load(itemMenu.image)
                        .transform(new PicassoRoundTransformation())
                        .resize(photoImageSize, photoImageSize)
                        .noFade()
                        .into(holder.icon);
            holder.icon.setPadding(25, 25, 25, 25);
            holder.text.setText(itemMenu.item_name);
        } else {
            int resId = context
                    .getResources()
                    .getIdentifier(itemMenu.image, "drawable", context.getPackageName());
            if (resId != 0)
                Picasso.with(context)
                        .load(resId)
                        .resize(iconSize, iconSize)
                        .into(holder.icon);
            holder.icon.setPadding(40, 20, 20, 20);
            holder.text.setText(itemMenu.item_name);
            if (itemMenu.item_name.equals("")) {
                holder.itemLayout.removeAllViews();
                holder.itemLayout.setPadding(0, 100, 0, 100);
                holder.itemLayout.setFocusable(true);
            }
        }


        return v;
    }

    public String getNameItem(int itemNum){
        return items.get(itemNum).item_name;
    }

    private class ItemMenu{

        public String image;
        public String item_name;

        public ItemMenu(String image, String item_name){
            this.image = image;
            this.item_name = item_name;
        }
    }

    private class ItemMenuHolder{
        public LinearLayout itemLayout;
        public ImageView icon;
        public TextView text;
    }
}
