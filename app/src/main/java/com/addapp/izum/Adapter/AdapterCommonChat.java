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
import android.widget.LinearLayout;

import com.addapp.izum.CustomViewComponents.ImageOnlineIndication;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.addapp.izum.Structure.PrivateItem;
import com.amulyakhare.textdrawable.TextDrawable;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ILDAR on 26.08.2015.
 */
public class AdapterCommonChat extends BaseAdapter{

    private Context ctx;
    private LayoutInflater inflater;
    private ArrayList<PrivateItem> items = new ArrayList<>();
    private int imageSize;
    private boolean anonimImg = false;

    public AdapterCommonChat(Context context, ArrayList<PrivateItem> items) {
        this.items = items;
        ctx = context;
        inflater = LayoutInflater.from(context);
        imageSize = MainUserData.getAvatarSize();

        items.add(new PrivateItem(null,
                "Привет", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Как дела??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Норм. Ты как??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер\nсборной России 69-летний\nФабио Капелло",
                "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Норм. Ты как??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Отлично, чем занят??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер\nсборной России 69-летний\nФабио Капелло",
                "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Норм. Ты как??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Отлично, чем занят??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер\nсборной России 69-летний\nФабио Капелло",
                "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Норм. Ты как??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Отлично, чем занят??", "Артур, 36", "1"));
        items.add(new PrivateItem(null,
                "Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", "Артур, 36", "1"));
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
        CommonChatHolder holder;
        PrivateItem item = items.get(i);

        if(v == null){
            v = inflater.inflate(R.layout.layout_clear, null);

            holder = new CommonChatHolder();

            initView(v, holder);

            v.setTag(holder);
        } else {
            holder = (CommonChatHolder) v.getTag();
        }

        if(anonimImg) {

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

            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.pixel)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation(drawable))
                    .placeholder(drawable)
                    .stableKey("" + item.getId())
                    .into(holder.userImg);
        } else {

            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.photo_1)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation())
                    .into(holder.userImg);
        }

        holder.userName.setText(items.get(i).getName());
        holder.userMsg.setText(items.get(i).getMessage());

        return v;
    }

    public class CommonChatHolder{
        public ImageOnlineIndication userImg;
        public EmojiconTextView userName;
        public EmojiconTextView userMsg;
    }

    public void setAnonimImage(boolean anonimImg){

        this.anonimImg = anonimImg;
    }

    private void initView(View v, CommonChatHolder holder){

        LinearLayout mainLayout = (LinearLayout) v.findViewById(R.id.main_layout);
//        mainLayout.setPadding(30, 0, 16, 0);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundResource(R.drawable.activated_background_private_item);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(MainUserData.percentToPix(4, MainUserData.WIDTH),
                10, 0, 10);
        holder.userImg = new ImageOnlineIndication(ctx); // level 3
        holder.userImg.setLayoutParams(params);
        holder.userImg.setOnline(true);

        LinearLayout contentLayout = new LinearLayout(ctx); // level 2
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        contentLayout.setLayoutParams(params);

        View line = new View(ctx); // level 2
        int margin = (int) (imageSize * 0.14);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        params.setMargins(0, margin, margin, 0);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#DCDCDE"));

        LinearLayout textLayout = new LinearLayout(ctx); // level 3
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 0, 0);
        textLayout.setLayoutParams(params);
//            textLayout.setBackgroundColor(Color.GREEN);
        textLayout.setGravity(Gravity.CENTER_VERTICAL);
        textLayout.setMinimumHeight((int) (imageSize * 1.2));
        textLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout messageLayout = new LinearLayout(ctx); // level 4
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        messageLayout.setLayoutParams(params);
//            messageLayout.setBackgroundColor(Color.RED);
        messageLayout.setOrientation(LinearLayout.VERTICAL);


        holder.userName = new EmojiconTextView(ctx); // level 5
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (imageSize * 0.14);
        holder.userName.setLayoutParams(params);
        holder.userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        holder.userName.setTextColor(Color.BLACK);
        holder.userName.setTypeface(Typeface.createFromAsset(ctx.getAssets(),
                "Roboto-Light.ttf"), Typeface.BOLD);


        holder.userMsg = new EmojiconTextView(ctx); // level 5
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.userMsg.setLayoutParams(params);
        holder.userMsg.setEmojiconSize(40);
        holder.userMsg.setLineSpacing(3.0f, 1.0f);
        holder.userMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(16));
        holder.userMsg.setTypeface(Typeface.createFromAsset(ctx.getAssets(),
                "Roboto-Light.ttf"), Typeface.BOLD);
        holder.userMsg.setTextColor(Color.parseColor("#3d3d3d"));

        messageLayout.addView(holder.userName);
        messageLayout.addView(holder.userMsg);

        textLayout.addView(messageLayout);
        textLayout.addView(line);

        contentLayout.addView(holder.userImg);
        contentLayout.addView(textLayout);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);

        mainLayout.addView(contentLayout);
    }

    public void addMessage(String text){
        Log.e("AdapterCommonChat", "Text: " + text);
        items.add(0, new PrivateItem(null,
                text, "Артур, 36", "1"));
        notifyDataSetChanged();
    }
}
