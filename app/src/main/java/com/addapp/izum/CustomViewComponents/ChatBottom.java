package com.addapp.izum.CustomViewComponents;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addapp.izum.Adapter.AdapterPrivateMessaging;
import com.addapp.izum.OtherClasses.KeyboardHandler;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by ILDAR on 07.08.2015.
 */
public class ChatBottom extends LinearLayout{

    public static final int PRIVATE_CHAT = 0;
    public static final int COMMON_CHAT = 1;

    private OnClickListener clickListener;
    private OnClickAddPhotoListener clickAddPhotoListener;
    private ImageView addPhoto;
    private EmojiconEditText msgText;
    private ImageView addSmile;
    private TextView buttonSend;
    private LinearLayout mainLayout;
    private ExpandableHeightGridView photoGrid;
    private AdapterPhotoGrid adapterPhotoGrid;
    private FrameLayout smilesCover;
    private FragmentManager fragmentManager;
    private KeyboardHandler keyboardHandler;

    private AdapterPrivateMessaging.ArrayImage myImages;

    private LayoutParams params;
    private MainUserData userData = MainUserData.getInstance();

    public ChatBottom(Context context, int attr, FragmentManager fragmentManager) {
        super(context);
        myImages = new AdapterPrivateMessaging.ArrayImage();
        this.fragmentManager = fragmentManager;
        switch (attr){
            case PRIVATE_CHAT:
                initViewPrivateChat();
                setupControllerPrivateChat();
                break;
            case COMMON_CHAT:
                initViewCommonChat();
                setupControllerCommonChat();
                break;
        }
    }

    private void initViewPrivateChat() {
        setOrientation(VERTICAL);

        /* Основной слой компонентов*/

        mainLayout = new LinearLayout(getContext());
//        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(params);
        mainLayout.setOrientation(HORIZONTAL);
        mainLayout.setBaselineAligned(false);
        mainLayout.setGravity(Gravity.BOTTOM);
//        mainLayout.setBackgroundColor(Color.YELLOW);

        /* Таблица добавляемых фотографии */

        photoGrid = new ExpandableHeightGridView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        photoGrid.setLayoutParams(params);
        photoGrid.setExpanded(true);
        photoGrid.setScrollContainer(false);
//        photoGrid.setBackgroundColor(Color.RED);
        photoGrid.setVerticalSpacing(0);
        photoGrid.setHorizontalSpacing(0);
        photoGrid.setNumColumns(5);
        photoGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        photoGrid.setVisibility(GONE);

        /* Инициализация адаптера фотографии */

        adapterPhotoGrid = new AdapterPhotoGrid();
        photoGrid.setAdapter(adapterPhotoGrid);

        /* Кнопка добаления фотографии*/

        addPhoto = new ImageView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.leftMargin = MainUserData.percentToPix(5, MainUserData.WIDTH);
        params.bottomMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        addPhoto.setLayoutParams(params);
//        addPhoto.setBackgroundColor(Color.CYAN);
        Picasso.with(getContext())
                .load(R.drawable.photo_message)
                .resize(50, (int) (50 * 0.78))
                .into(addPhoto);


        /* Поле ввода сообщения */

        msgText = new EmojiconEditText(getContext()){
            @Override
            public boolean onKeyPreIme(int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK && keyboardHandler.isSmileOpened()){
                    keyboardHandler.setSmileOpened(false);
                }

                return super.onKeyPreIme(keyCode, event);
            }
        };
        params = new LayoutParams(MainUserData.percentToPix(58, MainUserData.WIDTH), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        msgText.setLayoutParams(params);
        msgText.setEmojiconSize(32);
        msgText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        msgText.setHintTextColor(getResources().getColor(R.color.hint_color));
        msgText.setMaxLines(4);
        msgText.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        msgText.setBackgroundResource(R.drawable.background_edittext);
//        msgText.setBackgroundColor(Color.RED);
        msgText.setHint("Сообщение...");

        /* Кнопка добаления смайликов */

        addSmile = new ImageView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        params.bottomMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.rightMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        addSmile.setLayoutParams(params);
//        addSmile.setBackgroundColor(Color.BLACK);
        int imageSize = (int) (50 * 0.78);
                Picasso.with(getContext())
                .load(R.drawable.smile)
                .resize(imageSize, imageSize)
                .into(addSmile);

        /* Кнопка добаления смайликов */

        buttonSend = new TextView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        params.rightMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);

        params.bottomMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        buttonSend.setLayoutParams(params);
//        buttonSend.setBackgroundColor(Color.DKGRAY);
        buttonSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        buttonSend.setTextColor(getResources().getColor(R.color.izum_color));
        buttonSend.setGravity(Gravity.CENTER);
        buttonSend.setText("Отпр.");
        buttonSend.setOnClickListener(sendMessage);

        /* Слой для наложение под окно смайликов */

        smilesCover = new FrameLayout(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        smilesCover.setLayoutParams(params);
        smilesCover.setId(Utils.generateViewId());

        fragmentManager.beginTransaction()
                .replace(smilesCover.getId(), EmojiconsFragment.newInstance(false))
                .commit();

        addView(photoGrid);
        mainLayout.addView(addPhoto);
        mainLayout.addView(msgText);
        mainLayout.addView(addSmile);
        mainLayout.addView(buttonSend);
        addView(mainLayout);
        addView(smilesCover);
    }

    private void setupControllerPrivateChat() {

        /* нажатие кнопки "Добавить фото" */
        addPhoto.setOnClickListener(clickAddPhoto);

        /* нажатие кнопки "Смайлики" */
        addSmile.setOnClickListener(showSmileCover);
    }

    private void initViewCommonChat(){
        setOrientation(VERTICAL);

        /* Основной слой компонентов*/

        mainLayout = new LinearLayout(getContext());
//        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MainUserData.percentToPix(8, MainUserData.HEIGHT));
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(params);
        mainLayout.setOrientation(HORIZONTAL);
        mainLayout.setBaselineAligned(false);
        mainLayout.setGravity(Gravity.BOTTOM);
//        mainLayout.setBackgroundColor(Color.YELLOW);

        /* Поле ввода сообщения */

        msgText = new EmojiconEditText(getContext()){
            @Override
            public boolean onKeyPreIme(int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK && keyboardHandler.isSmileOpened()){
                    keyboardHandler.setSmileOpened(false);
                }

                return super.onKeyPreIme(keyCode, event);
            }
        };
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        msgText.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        msgText.setHintTextColor(getResources().getColor(R.color.hint_color));
        msgText.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        msgText.setBackgroundResource(R.drawable.background_edittext);
        msgText.setLayoutParams(params);
        msgText.setEmojiconSize(32);
        msgText.setMaxLines(4);
//        msgText.setBackgroundColor(Color.RED);
        msgText.setHint("Сообщение...");

        /* Кнопка добаления смайликов */

        addSmile = new ImageView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        params.bottomMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        params.rightMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        addSmile.setLayoutParams(params);
//        addSmile.setBackgroundColor(Color.BLACK);
        int imageSize = (int) (50 * 0.78);
        Picasso.with(getContext())
                .load(R.drawable.smile)
                .resize(imageSize, imageSize)
                .into(addSmile);

        /* Кнопка добаления смайликов */

        buttonSend = new TextView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);
        params.rightMargin = MainUserData.percentToPix(2, MainUserData.WIDTH);

        params.bottomMargin = MainUserData.percentToPix(2, MainUserData.HEIGHT);
        buttonSend.setLayoutParams(params);
//        buttonSend.setBackgroundColor(Color.DKGRAY);
        buttonSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(20));
        buttonSend.setTextColor(getResources().getColor(R.color.izum_color));
        buttonSend.setGravity(Gravity.CENTER);
        buttonSend.setText("Отпр.");
        buttonSend.setOnClickListener(sendMessage);

        /* Слой для наложение под окно смайликов */

        smilesCover = new FrameLayout(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        smilesCover.setLayoutParams(params);
        smilesCover.setId(Utils.generateViewId());

        fragmentManager.beginTransaction()
                .replace(smilesCover.getId(), EmojiconsFragment.newInstance(false))
                .commit();

        mainLayout.addView(addSmile);
        mainLayout.addView(msgText);
        mainLayout.addView(buttonSend);
        addView(mainLayout);
        addView(smilesCover);

    }

    private void setupControllerCommonChat(){

        /* нажатие кнопки "Смайлики" */
        addSmile.setOnClickListener(showSmileCover);
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private View.OnClickListener sendMessage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clickListener.onSend(msgText.getText().toString());
            msgText.setText("");
        }
    };

    private View.OnClickListener showSmileCover = new View.OnClickListener() {
        @Override
        public void onClick(View view){


            if (keyboardHandler.isSmile()) {
                Log.e("ChatBottom", "setSmile(false)");
                keyboardHandler.setSmile(false);
                keyboardHandler.setSmileOpened(false);
            } else {

                if (keyboardHandler.isKeyboard()){
                    keyboardHandler.setSmileOpened(true);
                    keyboardHandler.hideKeyboard();
                    return;
                }

                Log.e("ChatBottom", "setSmile(true)");
                keyboardHandler.setSmile(true);
                keyboardHandler.setSmileOpened(true);
            }
        }
    };

    public EmojiconEditText getMsgText() {
        return msgText;
    }

    public void showSmiles(){
        smilesCover.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, userData.getKeyboardHeight()));
    }

    public void hideSmiles(){
        smilesCover.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
    }

    public void setKeyboardHandler(KeyboardHandler keyboardHandler) {
        keyboardHandler.setChatBottom(this);
        this.keyboardHandler = keyboardHandler;
    }



    /* Методы работы с фотографиями */

    public void setClickAddPhotoListener(OnClickAddPhotoListener clickAddPhotoListener) {
        this.clickAddPhotoListener = clickAddPhotoListener;
    }

    private View.OnClickListener clickAddPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clickAddPhotoListener.onAddPhoto();
        }
    };

    public void showPhotosGrid(){
        adapterPhotoGrid.setImgName(myImages);
        photoGrid.setVisibility(VISIBLE);
    }

    public void hidePhotosGrid(){
        adapterPhotoGrid.setImgName(myImages);
        photoGrid.setVisibility(GONE);
    }

    private class AdapterPhotoGrid extends BaseAdapter{

        private LayoutInflater inflater;

        private AdapterPrivateMessaging.ArrayImage imgName = new AdapterPrivateMessaging.ArrayImage();

        @Override
        public int getCount() {
            return imgName.getCountImages();
        }

        @Override
        public String getItem(int i) {
            return imgName.getImage(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater)viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.item_profile_photo_grid, viewGroup, false);

            ImageView pic = (ImageView) itemView.findViewById(R.id.imagePhoto);

            Uri uri = Uri.fromFile(new File(imgName.getImage(i)));

            Picasso.with(viewGroup.getContext())
                    .load(uri)
                    .fit()
                    .centerInside()
                    .into(pic);

            return itemView;
        }

        public void setImgName(AdapterPrivateMessaging.ArrayImage imgName) {
            this.imgName.setImages(imgName.getImages());
            notifyDataSetChanged();
        }
    }

    public AdapterPrivateMessaging.ArrayImage getMyImages() {
        return myImages;
    }

    public void setMyImages(AdapterPrivateMessaging.ArrayImage myImages) {
        this.myImages = myImages;
    }

    public interface OnClickListener{
        public void onSend(String text);
    }

    public interface OnClickAddPhotoListener{
        public void onAddPhoto();
    }
}
