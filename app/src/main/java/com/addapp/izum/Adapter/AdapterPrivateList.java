package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.R;
import com.addapp.izum.Structure.PrivateItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 22.07.2015.
 */
public class AdapterPrivateList extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<PrivateItem> objects;
    private int imageSize;

    private MainUserData userData = MainUserData.getInstance();

    public AdapterPrivateList(Context ctx, ArrayList<PrivateItem> items){
        this.objects = items;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageSize = MainUserData.getAvatarSize();
    }

    @Override
    public int getCount() {
        return /*objects.size()*/ 20;
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        PrivateItemHolder holder = null;
        Container container;

        if (v == null){

            v = inflater.inflate(R.layout.layout_clear, null);
            container = initView(new Container(v, holder), viewGroup.getContext());

            v = container.getView();
            holder = container.getItemHolder();
            v.setTag(holder);

        } else{
            holder = (PrivateItemHolder) v.getTag();
        }

        Picasso.with(viewGroup.getContext())
                .load(R.drawable.nophoto)
                .resize(imageSize, imageSize)
                .centerCrop()
                .transform(new PicassoRound2Transformation())
                .into(holder.userImg);

        holder.userName.setText("Артур, 36");
        holder.userMsg.setText("Привет! Все отлично, сам...");
        holder.msgTime.setText("06:55");
        holder.msgCount.setText("1");

        return v;
    }

    private Container initView(Container container, Context ctx){
        PrivateItemHolder newHolder = new PrivateItemHolder();
        LinearLayout mainLayout = (LinearLayout) container.getView().findViewById(R.id.main_layout);
        mainLayout.setPadding(MainUserData.percentToPix(4, MainUserData.WIDTH),
                0, 16, 0);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundResource(R.drawable.activated_background_private_item);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageOnlineIndication userImg = new ImageOnlineIndication(ctx); // level 3
        userImg.setLayoutParams(params);
        userImg.setOnline(true);
        params.setMargins(0, 10, 0, 10);

        LinearLayout contentLayout = new LinearLayout(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(params);

        View line = new View(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
//        params.setMargins(40, 0, 40, 0);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#DCDCDE"));

        LinearLayout textLayout = new LinearLayout(ctx); // level 3
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(30, 0, 0, 0);
        params.weight = 1;
        textLayout.setLayoutParams(params);
        textLayout.setGravity(Gravity.CENTER_VERTICAL);
        textLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout messageLayout = new LinearLayout(ctx); // level 4
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        messageLayout.setLayoutParams(params);
        messageLayout.setOrientation(LinearLayout.VERTICAL);

        TextView msgCount = new TextView(ctx); // level 4
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 10;
        msgCount.setLayoutParams(params);
        msgCount.setBackgroundResource(R.drawable.shape_private_count_message);
        msgCount.setGravity(Gravity.CENTER);
        msgCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(14));
        msgCount.setTextColor(Color.WHITE);
        msgCount.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);

        TextView userName = new TextView(ctx); // level 5
        userName.setLayoutParams(params);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        userName.setTextColor(Color.BLACK);
        userName.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);
        TextView userMsg = new TextView(ctx); // level 5
        userMsg.setLayoutParams(params);
        userMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(16));
        userMsg.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);
        userMsg.setTextColor(Color.BLACK);
        TextView msgTime = new TextView(ctx); // level 5
        msgTime.setLayoutParams(params);
        msgTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(14));
        msgTime.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);
        msgTime.setTextColor(ctx.getResources().getColor(R.color.izum_color));

        messageLayout.addView(userName);
        messageLayout.addView(userMsg);
        messageLayout.addView(msgTime);

        textLayout.addView(messageLayout);
        textLayout.addView(msgCount);

        contentLayout.addView(userImg);
        contentLayout.addView(textLayout);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainLayout.addView(contentLayout);
        mainLayout.addView(line);

        newHolder.userImg = userImg;
        newHolder.userName = userName;
        newHolder.userMsg = userMsg;
        newHolder.msgTime = msgTime;
        newHolder.msgCount = msgCount;

        container.setItemHolder(newHolder);

        return container;
    }

    public class PrivateItemHolder{
        public ImageView userImg;
        public TextView userName;
        public TextView userMsg;
        public TextView msgTime;
        public TextView msgCount;
    }

    private class Container{

        private View view;
        private PrivateItemHolder itemHolder;

        public Container(View view, PrivateItemHolder itemHolder){
            this.view = view;
            this.itemHolder = itemHolder;
        }

        public PrivateItemHolder getItemHolder() {
            return itemHolder;
        }

        public void setItemHolder(PrivateItemHolder itemHolder) {
            this.itemHolder = itemHolder;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }
}
