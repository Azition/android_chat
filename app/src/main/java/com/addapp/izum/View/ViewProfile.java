package com.addapp.izum.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.addapp.izum.CustomViewComponents.CustomTextView;
import com.addapp.izum.CustomViewComponents.ExpandableHeightGridView;
import com.addapp.izum.CustomViewComponents.NestedListView;
import com.addapp.izum.CustomViewComponents.SquareFrameLayout;
import com.addapp.izum.CustomViewComponents.SquareViewPager;
import com.addapp.izum.OtherClasses.Configurations;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by ILDAR on 01.07.2015.
 */
public class ViewProfile {

    public static final int MY_PROFILE = 0;
    public static final int OTHER_PROFILE = 1;

    private View view;
    private LinearLayout otherStatusLayout;
    private ImageView imageAddUser;             // кнопка - изображение добавление нового пользователя
    private LinearLayout mainProfileLayout;     // основной слой профиля
    private int attr;                           /* атрибут определяющий профиль
                                                   если 0 то профиль пользователя
                                                   если 1 то чужой профиль
                                               */

    /*
            Компоненты профиля
    */
    private SquareFrameLayout photoLayout;      // слой с аватаркой, статусом и подарками
    private SquareViewPager photoPager;         // компонент пролистывания фоток
    private GridView gridButtons;               // кнопки в таблице
    private CustomTextView statusText;          // статус пользователя
    private PopupWindow statusEditWindow;       // окно редактирования статуса
    private ExpandableHeightGridView gridPhoto; // фотографии в таблице
    private NestedListView listInfo;            // список информации о пользователе
    private CirclePageIndicator pageIndicator;  // индикатор фотографии

    private Configurations config;

    public ViewProfile(View view, LinearLayout otherStatusLayout, int attr){
        this.view = view;
        this.otherStatusLayout = otherStatusLayout;
        init();
        setup();
    }

    private void init() {
        config = new Configurations(view.getContext());
        imageAddUser = new ImageView(view.getContext());
        mainProfileLayout = (LinearLayout)view.findViewById(R.id.profile_layout);
        photoLayout = new SquareFrameLayout(getContext());
        photoPager = new SquareViewPager(view.getContext());
        gridButtons = new GridView(getContext());
        statusText = new CustomTextView(getContext());
        gridPhoto = new ExpandableHeightGridView(getContext());
        listInfo = new NestedListView(getContext());
        pageIndicator = new CirclePageIndicator(getContext());

    }


    private void setup() {
        /*
            Добавление кнопки в верхную панель
        */
        Picasso.with(view.getContext())
                .load(R.drawable.options)
                .resize(70, 70)
                .into(imageAddUser);

        otherStatusLayout.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        otherStatusLayout.addView(imageAddUser);
        otherStatusLayout.setPadding(0, 0, 20, 0);

        /*
            Генерация основного слоя
        */

        photoLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));


        photoPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140);
        params.gravity = Gravity.BOTTOM;

        /*
            Таблица кнопок
        */

        gridButtons.setLayoutParams(params);
        gridButtons.setBackgroundColor(Color.parseColor("#CCFFFFFF"));
        gridButtons.setHorizontalSpacing(0);
        gridButtons.setVerticalSpacing(0);
        gridButtons.setNumColumns(3);
        gridButtons.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = 120;

        /*
            Статус
        */

        statusText.setLayoutParams(params);
        statusText.setTextColor(config.getTextColor());
        statusText.setGravity(Gravity.CENTER);
        statusText.setText("Status for User");
        statusText.setBackgroundResource(R.drawable.shape_status_text);

        /*
            Индикатор фотографии
        */

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP|Gravity.CENTER_HORIZONTAL;
        params.topMargin = 40;

        pageIndicator.setLayoutParams(params);
        pageIndicator.setPageColor(Color.parseColor("#88FFFFFF"));
        pageIndicator.setStrokeColor(Color.parseColor("#00FFFFFF"));
        pageIndicator.setStrokeWidth(10);
        pageIndicator.setRadius(16);


        photoLayout.addView(photoPager);
        photoLayout.addView(gridButtons);
        photoLayout.addView(statusText);
        photoLayout.addView(pageIndicator);

        gridPhoto.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gridPhoto.setExpanded(true);
        gridPhoto.setScrollContainer(false);
        gridPhoto.setHorizontalSpacing(0);
        gridPhoto.setVerticalSpacing(0);
        gridPhoto.setNumColumns(3);
        gridPhoto.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        listInfo.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listInfo.setScrollContainer(false);
        listInfo.setDividerHeight(0);

        mainProfileLayout.addView(photoLayout);
        mainProfileLayout.addView(gridPhoto);
        mainProfileLayout.addView(listInfo);

    }

    public void initiatePopupWindow(){

        final PopupWindow dimBackground = dimBackground();

        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params;
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.shape_status_edit_window);

        LinearLayout editLayout = new LinearLayout(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editLayout.setLayoutParams(params);

        final EditText statusEditText = new EditText(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        statusEditText.setLayoutParams(params);
        statusEditText.setHint("Изменить статус");
        statusEditText.setBackgroundResource(R.drawable.shape_status_edit_text);

        Button closeButton = new Button(getContext());
        params = new LinearLayout.LayoutParams(70, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 0;
        closeButton.setLayoutParams(params);
        closeButton.setText("OK");
        closeButton.setBackgroundResource(R.drawable.shape_status_edit_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusText.setText(statusEditText.getText());
                statusEditWindow.dismiss();
            }
        });

        editLayout.addView(statusEditText);
        editLayout.addView(closeButton);

        TextView statusName = new TextView(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusName.setLayoutParams(params);
        statusName.setText("Статус");
        statusName.setTextColor(config.getTextColor());
        statusName.setTextSize(16);
        statusName.setPadding(0, 0, 0, 10);

        layout.addView(statusName);
        layout.addView(editLayout);
        statusEditWindow = new PopupWindow(layout, photoLayout.getWidth() - 100, 190, true);
        statusEditWindow.setAnimationStyle(R.style.PopupAnimation);
        statusEditWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        statusEditWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimBackground.dismiss();
            }
        });
        statusEditWindow.update();

    }

    private PopupWindow dimBackground(){
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.layout_fadepopup,
                (ViewGroup) view.findViewById(R.id.fadePopup));

        DisplayMetrics displaymetrics = getContext().getResources().getDisplayMetrics();

        int windowWidth = displaymetrics.widthPixels;
        int windowHeight = displaymetrics.heightPixels;

        PopupWindow fadePopup = new PopupWindow(layout, windowWidth, windowHeight, false);
        fadePopup.setAnimationStyle(R.style.PopupAnimation);
        fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);
        fadePopup.update();
        return fadePopup;

    }

    /*   **********  Getters  **********  */

    public View getView(){
        return this.view;
    }

    public ImageView getImageAddUser(){
        return this.imageAddUser;
    }

    public Context getContext(){
        return view.getContext();
    }

    public TextView getStatusText() { return statusText; }

    public SquareViewPager getPhotoPager(){ return photoPager; }

    public GridView getGridButtons(){ return gridButtons; }

    public ExpandableHeightGridView getGridPhoto(){ return gridPhoto; }

    public NestedListView getListInfo(){ return listInfo; }

    public CirclePageIndicator getPageIndicator(){ return pageIndicator; }

    /*   **********  Setters  **********  */



}
