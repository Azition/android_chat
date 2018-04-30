package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addapp.izum.OtherClasses.MainUserData;
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

    private int profileItemHeight = 0;
    private int itemHeight = 0;

    private int photoImageSize = 110; /* image size = photoImageSize * photoImageSize */
    private int iconSize = 50; /* icon size = iconSize * iconSize */
    private int textSize = 18;
    private RelativeLayout.LayoutParams params;
    private MainUserData userData = MainUserData.getInstance();

    private TextView tvCountMessages;
    private int countMessage = 0;

    private boolean show_item;

    public AdapterMainMenu(Context context){
        this.context = context;

        show_item = true;

        items.add(new ItemMenu(userData.getAvatar() , userData.getUsername()));
        items.add(new ItemMenu("message", "Сообщения"));
        items.add(new ItemMenu("find", "Поиск"));
        items.add(new ItemMenu("chat", "Чат"));
        items.add(new ItemMenu("people", "Люди"));
        items.add(new ItemMenu("geolocation", "Геолокация"));
        items.add(new ItemMenu("", ""));
//        items.add(new ItemMenu("support", "Техподдержка"));
        items.add(new ItemMenu("options", "Настройки"));

        profileItemHeight = MainUserData.percentToPix(20, MainUserData.HEIGHT);
        itemHeight = MainUserData.percentToPix(10, MainUserData.HEIGHT);
        photoImageSize = (int) (profileItemHeight * 0.7);
        iconSize = (int) (itemHeight * 0.6);
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

            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = profileItemHeight;
            holder.text.setLayoutParams(params);

            holder.text.setTextColor(Color.WHITE);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    MainUserData.setTextSize(textSize));
            holder.text.setGravity(Gravity.CENTER_VERTICAL);
//            holder.text.setBackgroundColor(Color.WHITE);
            holder.text.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf"));

            holder.itemLayout = (RelativeLayout)v.findViewById(R.id.item_layout);

            ((RelativeLayout.LayoutParams)holder.icon.getLayoutParams())
                    .leftMargin = getMargin(profileItemHeight, iconSize);

            v.setTag(holder);
        }
        else{
            holder = (ItemMenuHolder) v.getTag();
        }

        ItemMenu itemMenu = items.get(position);

        switch(position){
            case 0:
                showHeaderMenu(itemMenu, holder);
                break;

            case 1:
                showMessageItemMenu(itemMenu, holder, parent.getContext());
                break;

            default:
                showOtherItemMenu(itemMenu, holder);
                break;
        }


        return v;
    }

    private void showHeaderMenu(ItemMenu item, final ItemMenuHolder holder){

        holder.itemLayout.setLayoutParams(
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        profileItemHeight));
        ((RelativeLayout.LayoutParams)holder.icon.getLayoutParams())
                .leftMargin = getMargin(profileItemHeight, photoImageSize);

        if (userData.getAvatar().equals(""))
            Picasso.with(context)
                    .load(R.drawable.nophoto)
                    .transform(new PicassoRoundTransformation())
                    .resize(photoImageSize, photoImageSize)
                    .into(holder.icon);
        else
            Picasso.with(context)
                    .load(userData.getAvatar())
                    .transform(new PicassoRoundTransformation())
                    .resize(photoImageSize, photoImageSize)
                    .noFade()
                    .into(holder.icon);
        holder.itemLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((RelativeLayout.LayoutParams) holder.text.getLayoutParams())
                                .topMargin = getMargin(photoImageSize, holder.text.getHeight());
                        holder.itemLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
        });
        holder.text.setText(userData.getUsername());
        holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        holder.text.setTypeface(MainUserData.getFont(context));
    }

    private void showMessageItemMenu(ItemMenu item, final ItemMenuHolder holder, Context context){

        holder.itemLayout.setLayoutParams(
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        itemHeight));

        int resId = context
                .getResources()
                .getIdentifier(item.image, "drawable", context.getPackageName());
        if (resId != 0)
            Picasso.with(context)
                    .load(resId)
                    .resize(iconSize, iconSize)
                    .into(holder.icon);
//        holder.icon.setPadding(60, 0, 20, 0);
        holder.text.setText(item.item_name);

//        holder.itemLayout.setPadding(0, paddingLayout, 0, paddingLayout);

        if (tvCountMessages == null) {

            tvCountMessages = (TextView) holder.itemLayout.findViewById(R.id.countMsg);
//            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params = (RelativeLayout.LayoutParams) tvCountMessages.getLayoutParams();
            params.rightMargin = 45;
            tvCountMessages.setLayoutParams(params);
            tvCountMessages.setGravity(Gravity.CENTER);
            tvCountMessages.setBackgroundResource(R.drawable.shape_menu_count_message);
            tvCountMessages.setTextColor(context.getResources().getColor(R.color.izum_color));
            tvCountMessages.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    MainUserData.setTextSize(16));
            tvCountMessages.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);
            tvCountMessages.setVisibility(View.GONE);
        }

        if (countMessage > 0){
            tvCountMessages.setText(Integer.toString(countMessage));
            tvCountMessages.setVisibility(View.VISIBLE);
        } else {
            tvCountMessages.setVisibility(View.GONE);
        }

        holder.itemLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((RelativeLayout.LayoutParams)holder.text.getLayoutParams())
                                .topMargin = getMargin(iconSize, holder.text.getHeight());
                        ((RelativeLayout.LayoutParams) tvCountMessages.getLayoutParams())
                                .topMargin = getMargin(iconSize, holder.text.getHeight());
                        holder.itemLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    private void showOtherItemMenu(ItemMenu item, final ItemMenuHolder holder){

        holder.itemLayout.setLayoutParams(
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        itemHeight));

        int resId = context
                .getResources()
                .getIdentifier(item.image, "drawable", context.getPackageName());

        if (resId != 0)
            Picasso.with(context)
                    .load(resId)
                    .resize(iconSize, iconSize)
                    .into(holder.icon);
//        holder.icon.setPadding(60, 0, 20, 0);
        holder.itemLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((RelativeLayout.LayoutParams)holder.text.getLayoutParams())
                                .topMargin = getMargin(iconSize, holder.text.getHeight());
                        holder.itemLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
        holder.text.setText(item.item_name);
//        holder.itemLayout.setPadding(0, paddingLayout, 0, paddingLayout);
        if (item.item_name.equals("")) {
            int width = MainUserData.percentToPix(10, MainUserData.HEIGHT);
            holder.itemLayout.removeAllViews();
            holder.itemLayout.setPadding(0, width, 0, width);
            holder.itemLayout.setFocusable(true);
        }
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
        public RelativeLayout itemLayout;
        public ImageView icon;
        public TextView text;
    }

    public void setCountMessage(int count){
        countMessage = count;
    }

    public void hideCountMessage(){
        tvCountMessages.setVisibility(View.GONE);
    }

    private int getMargin(int imgSize, int textSize){
        int result;

        imgSize = imgSize / 2;
        textSize = textSize / 2;

        result = imgSize - textSize;
        return result;
    }
}
