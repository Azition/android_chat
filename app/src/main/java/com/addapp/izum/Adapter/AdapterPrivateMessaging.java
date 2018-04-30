package com.addapp.izum.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.addapp.izum.OtherClasses.PicassoRound2Transformation;
import com.addapp.izum.R;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class AdapterPrivateMessaging extends BaseAdapter {

    private static final int MY_MESSAGE = 0;
    private static final int OTHER_MESSAGE = 1;

    private static final String TAG = "AdapterPrivateMessaging";
    private static final boolean DEBUG = true;

    private ArrayList<PrivateList> items = new ArrayList<>();
    private LinearLayout mainLayout;
    private RowView rowView;
    private ArrayImage images;
    private Context context;

    private MainUserData userData = MainUserData.getInstance();

    public AdapterPrivateMessaging() {

        images = new ArrayImage();
        images.addImage("http://im.topufa.org/quadro/49da3572d00c53bdd77258fe35e9a23d/thumb_06c6e7ea041615c2a949a47db9fa1b0a.jpg");
        images.addImage("http://im.topufa.org/quadro/49da3572d00c53bdd77258fe35e9a23d/thumb_06c6e7ea041615c2a949a47db9fa1b0a.jpg");
        images.addImage("genm");
        images.addImage("genm");

        items.add(new PrivateList("Привет", OTHER_MESSAGE));
        items.add(new PrivateList("Как дела??", OTHER_MESSAGE));
        items.add(new PrivateList("Норм. Ты как??", MY_MESSAGE));
        items.add(new PrivateList("Бывший главный тренер\nсборной России 69-летний\nФабио Капелло", OTHER_MESSAGE));
        items.add(new PrivateList("Норм. Ты как??", MY_MESSAGE));
        items.add(new PrivateList("Отлично, чем занят??", OTHER_MESSAGE, images));
        items.add(new PrivateList("Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", MY_MESSAGE));
        items.add(new PrivateList("Бывший главный тренер\nсборной России 69-летний\nФабио Капелло", OTHER_MESSAGE));
        items.add(new PrivateList("Норм. Ты как??", MY_MESSAGE));
        items.add(new PrivateList("Отлично, чем занят??", OTHER_MESSAGE));
        items.add(new PrivateList("Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", MY_MESSAGE));
        items.add(new PrivateList("Бывший главный тренер\nсборной России 69-летний\nФабио Капелло", OTHER_MESSAGE));
        items.add(new PrivateList("Норм. Ты как??", MY_MESSAGE));
        items.add(new PrivateList("Отлично, чем занят??", OTHER_MESSAGE));
        items.add(new PrivateList("Бывший главный тренер сборной России 69-летний\nФабио Капелло\n" +
                "Бывший главный тренер сборной России 69-летний\n" +
                "Фабио Капелло", MY_MESSAGE, images));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PrivateList getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        if (context == null){
            context = viewGroup.getContext();
        }

        if (v == null) {

            mainLayout = new LinearLayout(viewGroup.getContext());
            mainLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.setPadding(0, MainUserData.percentToPix(1, MainUserData.HEIGHT), 0, MainUserData.percentToPix(1, MainUserData.HEIGHT));

        } else {
            mainLayout = (LinearLayout) v;
        }

        rowView = new RowView(viewGroup.getContext(), mainLayout);

        PrivateList item = getItem(i);

        if(item.getAttr() == OTHER_MESSAGE) {
            return rowView.getLeftView(item);
        } else{
            return rowView.getRightView(item);
        }
    }

    public class PrivateList{
        private String msg;
        private int attr;
        private ArrayImage images;

        public PrivateList(String msg, int attr) {
            this.attr = attr;
            this.msg = msg;
        }

        public PrivateList(String msg, int attr, ArrayImage images) {
            this.attr = attr;
            this.images = images;
            this.msg = msg;
        }


        public ArrayImage getImages() {
            return images.getImages();
        }

        public void setImages(ArrayImage images) {
            this.images.setImages(images);
        }

        public int getAttr() {
            return attr;
        }

        public void setAttr(int attr) {
            this.attr = attr;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isEmptyImages(){
            return images == null;
        }

        public int getCountImages(){
            return images.getCountImages();
        }
    }

    public static class ArrayImage{
        private ArrayList<String> imgPath;

        public ArrayImage() {
            imgPath = new ArrayList<>();
        }


        public ArrayImage getImages() {
            ArrayImage tempImages = new ArrayImage();
            for(int i = 0; i < imgPath.size(); i++)
                tempImages.addImage(imgPath.get(i));
            return tempImages;
        }

        public void setImages(ArrayImage images) {
            imgPath.clear();
            for(int i = 0; i < images.getCountImages(); i++)
                this.imgPath.add(images.getImage(i));
        }

        public void setImages(ArrayList<String> tempImages) {
            imgPath.clear();
            for(int i = 0; i < tempImages.size(); i++)
                this.imgPath.add(tempImages.get(i));
        }

        public void addImage(String path){
            imgPath.add(path);
        }

        public String getImage(int pos){
            return imgPath.get(pos);
        }

        public int getCountImages(){
            return imgPath.size();
        }

        public void removeImages(){
            imgPath.clear();
        }
    }

    private class RowView{

        private LinearLayout mainLayout;
        private LinearLayout.LayoutParams params;
        private Context context;
        private int imageSize;

        public RowView(Context context, LinearLayout mainLayout) {
            this.context = context;
            this.mainLayout = mainLayout;
            imageSize = MainUserData.getAvatarSize();
            this.mainLayout.removeAllViews();
        }

        public LinearLayout getLeftView(PrivateList item){

            /* Инициализая визуальных компонентов */

            LinearLayout imageLayout = new LinearLayout(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageLayout.setLayoutParams(params);
            imageLayout.setOrientation(LinearLayout.VERTICAL);

            ImageView userImg = new ImageView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = MainUserData.percentToPix(4, MainUserData.WIDTH);
            userImg.setLayoutParams(params);

            Picasso.with(context)
                    .load(R.drawable.photo_1)
                    .resize(imageSize, imageSize)
                    .centerCrop()
                    .transform(new PicassoRound2Transformation())
                    .into(userImg);

            LinearLayout timeLayout = new LinearLayout(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeLayout.setLayoutParams(params);
            timeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            timeLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView time = new TextView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            time.setLayoutParams(params);
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(14));
            time.setTextColor(Color.GRAY);
            time.setText("09:07");

            LinearLayout msgCell = new LinearLayout(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            msgCell.setLayoutParams(params);
            msgCell.setBackgroundResource(R.drawable.background_user_message);
            msgCell.setGravity(Gravity.LEFT);
            msgCell.setOrientation(LinearLayout.VERTICAL);

            EmojiconTextView msg = new EmojiconTextView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) MainUserData.setTextSize(10);
            msg.setLayoutParams(params);
            msg.setEmojiconSize(40);
            msg.setPadding(0, 3, 0, 3);
            msg.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(18));
            msg.setTextColor(Color.WHITE);
            if(item.getMsg().equals("")){
                msg.setVisibility(View.GONE);
            } else {
                msg.setText(item.getMsg());
            }

            timeLayout.addView(time);

            imageLayout.addView(userImg);
            imageLayout.addView(timeLayout);

            msgCell.addView(msg);
            addImageMsg(item, msgCell);

            mainLayout.addView(imageLayout);
            mainLayout.addView(msgCell);
            mainLayout.setGravity(Gravity.LEFT);


            return mainLayout;
        }

        public LinearLayout getRightView(PrivateList item){

            /* Инициализая визуальных компонентов */

            LinearLayout timeLayout = new LinearLayout(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT|Gravity.BOTTOM;
            timeLayout.setLayoutParams(params);
            timeLayout.setPadding(20, 0, 0, 0);
            timeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            timeLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView time = new TextView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            time.setLayoutParams(params);
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(14));
            time.setTextColor(Color.GRAY);
            time.setPadding(0, 0, MainUserData.percentToPix(3, MainUserData.WIDTH), 0);
            time.setText("09:07");

            LinearLayout msgCell = new LinearLayout(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            msgCell.setLayoutParams(params);
            msgCell.setBackgroundResource(R.drawable.background_my_message);
            msgCell.setGravity(Gravity.RIGHT);
            msgCell.setOrientation(LinearLayout.VERTICAL);

            EmojiconTextView msg = new EmojiconTextView(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            params.rightMargin = (int) MainUserData.setTextSize(10);
            msg.setLayoutParams(params);
            msg.setEmojiconSize(40);
            msg.setPadding(0, 3, 0, 3);
            msg.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(18));
            if(item.getMsg().equals("")){
                msg.setVisibility(View.GONE);
            } else {
                msg.setText(item.getMsg());
            }

            timeLayout.addView(time);

            msgCell.addView(msg);
            addImageMsg(item, msgCell);

            mainLayout.addView(timeLayout);
            mainLayout.addView(msgCell);
            mainLayout.setGravity(Gravity.RIGHT);

            return mainLayout;
        }
    }

    private void addImageMsg(PrivateList item, LinearLayout msgCell){
        if (!item.isEmptyImages()){
            int count = item.getCountImages();
            int imgSize = userData.getScreenWidth() / 2;
            ImageView img;
            Pattern pattern = Pattern.compile("http://");
            Matcher matcher;
            String imgName;

            for(int i=0; i < count; i++){
                img = new ImageView(context);
                img.setLayoutParams(new LinearLayout.LayoutParams(imgSize, imgSize));
                imgName = item.getImages().getImage(i);
                matcher = pattern.matcher(imgName);
                if (matcher.find()) {
                    Picasso.with(context)
                            .load(imgName)
                            .fit()
                            .centerCrop()
                            .into(img);
                } else {
                    Picasso.with(context)
                            .load(new File(item.getImages().getImage(i)))
                            .fit()
                            .centerCrop()
                            .into(img);
                }
                if(imgName.equals("genm")){
                    Picasso.with(context)
                            .load(R.drawable.photo_1)
                            .fit()
                            .centerCrop()
                            .into(img);
                }
                msgCell.addView(img);
            }
        }
    }

    public void addMessage(String text, ArrayImage images){
        items.add(new PrivateList(text, MY_MESSAGE, images));
        notifyDataSetChanged();
    }

    /* Getters and Setters */
}
