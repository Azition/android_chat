package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addapp.izum.CustomViewComponents.ImageCountAndOnline;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class AdapterListRation extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private ArrayList<RationItem> items = new ArrayList<>();
    private int imageSize;
    private int count;

    public AdapterListRation(Context context, ArrayList<RationItem> items) {
        this.ctx = context;
        inflater = LayoutInflater.from(context);
        imageSize = MainUserData.getAvatarSize();
        items.add(new RationItem.Builder("657", "Рустам")
                .online(true)
                .userImg("http://im.topufa.org/")
                .userStatus("Статус чувака тут отображается")
                .userLikeCount(10800)
                .userGender(RationItem.MALE)
                .build());
        items.add(new RationItem.Builder("3465", "Алина")
                .online(false)
                .userImg("http://im.topufa.org/")
                .userStatus("Побрею вашу киску))")
                .userLikeCount(10600)
                .userGender(RationItem.FEMALE)
                .build());
        items.add(new RationItem.Builder("5467", "Диана")
                .online(true)
                .userImg("http://im.topufa.org/")
                .userStatus("Отличный денек котята")
                .userLikeCount(9800)
                .userGender(RationItem.FEMALE)
                .build());
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        CommonRationHolder holder;
        RationItem item = items.get(i);

        if(v == null){
            v = inflater.inflate(R.layout.layout_clear, null);

            holder = new CommonRationHolder();

            initView(v, holder);

            v.setTag(holder);
        } else {
            holder = (CommonRationHolder) v.getTag();
        }

        if(item.getUserImg().equals("http://im.topufa.org/")) {

            String letter;

            if (!item.getUserName().equals("")) {
                letter = item.getUserName().substring(0, 1);
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

            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.pixel)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation(drawable))
                    .placeholder(drawable)
                    .stableKey("" + item.getUserID())
                    .into(holder.userImg);
        } else {

            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.photo_1)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation())
                    .into(holder.userImg);
        }
        count = i + 1;
        holder.userImg.setOnline(item.isOnline());
        holder.userImg.setNumCount(count);
        holder.userName.setText(item.getUserName());
        holder.userStatus.setText(item.getUserStatus());
        holder.userLikeCount.setText("" + item.getUserLikeCount());

        return v;
    }

    public class CommonRationHolder{
        public ImageCountAndOnline userImg;
        public EmojiconTextView userName;
        public EmojiconTextView userStatus;
        public TextView userLikeCount;
    }

    private void initView(View v, final CommonRationHolder holder){

        LinearLayout mainLayout = (LinearLayout) v.findViewById(R.id.main_layout);
//        mainLayout.setPadding(30, 0, 16, 0);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setBackgroundResource(R.drawable.activated_background_private_item);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(MainUserData.percentToPix(4, MainUserData.WIDTH),
                10, 0, 10);
        holder.userImg = new ImageCountAndOnline(ctx); // level 3
        holder.userImg.setLayoutParams(params);
        holder.userImg.setOnline(true);

        LinearLayout contentLayout = new LinearLayout(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 0);
        contentLayout.setLayoutParams(params);
//        contentLayout.setBackgroundColor(Color.RED);
        contentLayout.setOrientation(LinearLayout.VERTICAL);

        final RelativeLayout textLayout = new RelativeLayout(ctx); // level 3
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, MainUserData.percentToPix(5, MainUserData.WIDTH), 0);
        params.topMargin = (int) (imageSize * 0.14);
        textLayout.setLayoutParams(params);
//        textLayout.setBackgroundColor(Color.GREEN);

        RelativeLayout.LayoutParams relParams;

        holder.userName = new EmojiconTextView(ctx); // level 5
        relParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        relParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
        holder.userName.setLayoutParams(relParams);
        holder.userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        holder.userName.setTextColor(Color.BLACK);
//        holder.userName.setBackgroundColor(Color.RED);
        holder.userName.setTypeface(Typeface.createFromAsset(ctx.getAssets(),
                "Roboto-Light.ttf"), Typeface.BOLD);


        holder.userLikeCount = new TextView(ctx); // level 5
        relParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        relParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        holder.userLikeCount.setLayoutParams(relParams);
        holder.userLikeCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        holder.userLikeCount.setTextColor(Color.BLACK);
//        holder.userLikeCount.setBackgroundColor(Color.RED);
        holder.userLikeCount.setTypeface(Typeface.createFromAsset(ctx.getAssets(),
                "Roboto-Light.ttf"), Typeface.BOLD);
        holder.userLikeCount.setId(Utils.generateViewId());

        int viewID = holder.userLikeCount.getId();

        final ImageView heartIcon = new ImageView(ctx);
        relParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        relParams.addRule(RelativeLayout.LEFT_OF, viewID);
        relParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        relParams.rightMargin = 8;
        heartIcon.setLayoutParams(relParams);
//        heartIcon.setBackgroundColor(Color.WHITE);

        heartIcon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                holder.userLikeCount.getPaint()
                        .getTextBounds(holder.userLikeCount.getText().toString(),
                                0, holder.userLikeCount.getText().length(), r);
                int size = (int) (r.height() * 1.4);
                Picasso.with(ctx)
                        .load(R.drawable.like)
                        .resize(size, 0)
                        .into(heartIcon);
                heartIcon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        holder.userStatus = new EmojiconTextView(ctx); // level 5
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 0, 0);
        holder.userStatus.setLayoutParams(params);
        holder.userStatus.setEmojiconSize(40);
        holder.userStatus.setLineSpacing(3.0f, 1.0f);
        holder.userStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(16));
        holder.userStatus.setTypeface(Typeface.createFromAsset(ctx.getAssets(),
                "Roboto-Light.ttf"), Typeface.BOLD);
        holder.userStatus.setTextColor(Color.parseColor("#3d3d3d"));


        textLayout.addView(holder.userName);
        textLayout.addView(heartIcon);
        textLayout.addView(holder.userLikeCount);

        contentLayout.addView(textLayout);
        contentLayout.addView(holder.userStatus);

        mainLayout.addView(holder.userImg);
        mainLayout.addView(contentLayout);
    }

    public static class RationItem{

        public static final int FEMALE = 0;
        public static final int MALE = 1;
        public static final int NONE = 2;

        private final String userID;
        private final String userName;
        private String userImg = "";
        private boolean online = false;
        private String userStatus = "";
        private int userLikeCount = 0;
        private int userGender = NONE;

        private RationItem(Builder builder) {
            userID = builder.userID;
            userName = builder.userName;
            online = builder.online;
            userStatus = builder.userStatus;
            userLikeCount = builder.userLikeCount;
            userImg = builder.userImg;
            userGender = builder.userGender;
        }

        /* ------ Setters ------ */

        public void setOnline(boolean online) {
            this.online = online;
        }

        public void setUserGender(int userGender) {
            this.userGender = userGender;
        }

        public void setUserLikeCount(int userLikeCount) {
            this.userLikeCount = userLikeCount;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        /* ------ Getters ------ */

        public boolean isOnline() {
            return online;
        }

        public String getUserImg() {
            return userImg;
        }

        public int getUserGender() {
            return userGender;
        }

        public String getUserID() {
            return userID;
        }

        public int getUserLikeCount() {
            return userLikeCount;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public static class Builder{
            private final String userID;
            private final String userName;
            private String userImg = "";
            private boolean online;
            private String userStatus;
            private int userLikeCount;
            private int userGender;

            public Builder(String userID, String userName) {
                this.userID = userID;
                this.userName = userName;
            }

            public Builder online(boolean val){
                online = val;
                return this;
            }

            public Builder userStatus(String val){
                userStatus = val;
                return this;
            }

            public Builder userLikeCount(int val){
                userLikeCount = val;
                return this;
            }

            public Builder userGender(int val){
                userGender = val;
                return this;
            }

            public Builder userImg(String val){
                userImg = val;
                return this;
            }

            public RationItem build(){
                return new RationItem(this);
            }
        }
    }
}
