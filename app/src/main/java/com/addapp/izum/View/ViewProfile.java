package com.addapp.izum.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.addapp.izum.Adapter.AdapterListEditProfile;
import com.addapp.izum.CustomViewComponents.CustomTextView;
import com.addapp.izum.CustomViewComponents.ExpandableHeightGridView;
import com.addapp.izum.CustomViewComponents.NestedListView;
import com.addapp.izum.CustomViewComponents.SquareFrameLayout;
import com.addapp.izum.CustomViewComponents.SquareViewPager;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.R;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by ILDAR on 01.07.2015.
 */
public class ViewProfile {

    public static final int MY_PROFILE = 0;
    public static final int OTHER_PROFILE = 1;

    private static final boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();

    private View view;
    private ScrollView scrollView;
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
//    private GridView gridButtons;               // кнопки в таблице
    private CustomTextView statusText;          // статус пользователя
    private PopupWindow statusEditWindow;       // окно редактирования статуса
//    private ExpandableHeightGridView gridPhoto; // фотографии в таблице
    private ListView listInfo;            // список информации о пользователе
//    private CirclePageIndicator pageIndicator;  // индикатор фотографии

    private UpdateProfileListener mUpdateListener;
    private AdapterListEditProfile adapterListEditProfile;

    private MainUserData userData = MainUserData.getInstance();


    private int gridButtonHeight;
    private int mainWidth;
    private boolean render;

    public ViewProfile(View view, LinearLayout otherStatusLayout, int attr) {
        this.view = view;
        this.otherStatusLayout = otherStatusLayout;
        this.attr = attr;
        init();
        setup();
    }

    private void init() {
        imageAddUser = new ImageView(view.getContext());
        mainProfileLayout = (LinearLayout) view.findViewById(R.id.profile_layout);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        photoLayout = new SquareFrameLayout(getContext());
        photoPager = new SquareViewPager(view.getContext());
/****************************************************************************************
        Временное отключение кнопок
 *****************************************************************************************/
//        gridButtons = new GridView(getContext());
        statusText = new CustomTextView(getContext());
/****************************************************************************************
        Временное отключение фоток
 *****************************************************************************************/
//        gridPhoto = new ExpandableHeightGridView(getContext());
        listInfo = new NestedListView(getContext());
/****************************************************************************************
        Временное отключение индикатора фоток
 *****************************************************************************************/
//        pageIndicator = new CirclePageIndicator(getContext());

        mainWidth = userData.getScreenWidth();
        gridButtonHeight = MainUserData.percentToPix(20, MainUserData.WIDTH);
        render = true;
    }

    private void setup() {
        /*
            Добавление кнопки в верхную панель
        */

        imageAddUser.setBackground(view.getResources()
                .getDrawable(R.drawable.button));

        otherStatusLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        otherStatusLayout.addView(imageAddUser);
        otherStatusLayout.setPadding(0, 0, 20, 0);

        otherStatusLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int iconSize = (int) (otherStatusLayout.getHeight() * 0.7);

                        if (attr == MY_PROFILE) {
                            Picasso.with(view.getContext())
                                    .load(R.drawable.pen)
                                    .resize(iconSize, iconSize)
                                    .into(imageAddUser);
                        } else {
                            Picasso.with(view.getContext())
                                    .load(R.drawable.add_favorites)
                                    .resize(iconSize, iconSize)
                                    .into(imageAddUser);
                        }
                    }
                });

        /*
            Генерация основного слоя
        */

        photoLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));


        photoPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridButtonHeight);
        params.gravity = Gravity.BOTTOM;

        /*
            Таблица кнопок
        */
/****************************************************************************************
        Временное отключение кнопок
 *****************************************************************************************/

/*
        gridButtons.setLayoutParams(params);
        gridButtons.setBackgroundColor(Color.parseColor("#CCFFFFFF"));
        gridButtons.setHorizontalSpacing(0);
        gridButtons.setVerticalSpacing(0);
        gridButtons.setNumColumns(3);
        gridButtons.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
*/

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

//        params.bottomMargin = userData.getScreenWidth() / 100 * 20 - 10;

/****************************************************************************************
        Временное сдвигаем статус вниз
 *****************************************************************************************/
        params.bottomMargin = userData.getScreenWidth() / 100 * 5 - 10;

        /*
            Статус
        */

        statusText.setLayoutParams(params);
        statusText.setTextColor(Color.WHITE);
        statusText.setGravity(Gravity.CENTER);
        statusText.setText("");
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        statusText.setBackgroundResource(R.drawable.shape_status_text);

        /**
        *    Индикатор фотографии
        */

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.topMargin = mainWidth / 12;
/****************************************************************************************
        Временное отключение индикатора фоток
 *****************************************************************************************/

/*        pageIndicator.setLayoutParams(params);
        pageIndicator.setPageColor(Color.parseColor("#88FFFFFF"));
        pageIndicator.setStrokeColor(Color.parseColor("#00FFFFFF"));
        pageIndicator.setStrokeWidth(mainWidth / 96);
        pageIndicator.setRadius(mainWidth / 60);*/

        photoLayout.addView(photoPager);
/****************************************************************************************
        Временное отключение кнопок
 *****************************************************************************************/
//        photoLayout.addView(gridButtons);
        photoLayout.addView(statusText);
//        photoLayout.addView(pageIndicator);

        Drawable background = new ColorDrawable(0xFF373737);
        Drawable progess = new ColorDrawable(0xFF00B51C);
        ClipDrawable clipProgress = new ClipDrawable(progess, Gravity.LEFT,
                ClipDrawable.HORIZONTAL);

        LayerDrawable layerList = new LayerDrawable(new Drawable[]{
                background, clipProgress
        });
        layerList.setId(0, android.R.id.background);
        layerList.setId(1, android.R.id.progress);

/****************************************************************************************
        Временное отключение фоток
 *****************************************************************************************/

/*        gridPhoto.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gridPhoto.setExpanded(true);
        gridPhoto.setScrollContainer(false);
        gridPhoto.setHorizontalSpacing(0);
        gridPhoto.setVerticalSpacing(0);
        gridPhoto.setNumColumns(3);
        gridPhoto.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);*/

        listInfo.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listInfo.setScrollContainer(false);
        listInfo.setDividerHeight(0);
        listInfo.setFocusable(false);

        mainProfileLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        if (render) {
                            render = false;
                            mainProfileLayout.requestLayout();
                        }
                    }
                });

        mainProfileLayout.addView(photoLayout);
/****************************************************************************************
        Временное отключение фоток
*****************************************************************************************/
//        mainProfileLayout.addView(gridPhoto);
        mainProfileLayout.addView(listInfo);

    }

    /*
        Окно редактирования профиля
    */
    public void initiatePopupWindow() {

        final PopupWindow dimBackground = dimBackground();

        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.shape_status_edit_window);

        ListView listEditProfile = new ListView(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        listEditProfile.setLayoutParams(params);
        listEditProfile.setAdapter(adapterListEditProfile);
        listEditProfile.setDivider(new ColorDrawable(Color.parseColor("#f0f0f0")));
        listEditProfile.setDividerHeight(2);
        listEditProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mUpdateListener.OnChangeItem(i);
                if(DEBUG) Log.e(TAG, "onItemClick");
            }
        });

        LinearLayout editLayout = new LinearLayout(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editLayout.setLayoutParams(params);

        Button closeButton = new Button(getContext());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        closeButton.setLayoutParams(params);
        closeButton.setText("OK");
        closeButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainUserData.setTextSize(18));
        closeButton.setTextColor(Color.WHITE);
        closeButton.setBackgroundResource(R.drawable.button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateListener.OnUpdateProfile();
                statusEditWindow.dismiss();
            }
        });

        editLayout.addView(closeButton);

        layout.addView(listEditProfile);
        layout.addView(editLayout);

        statusEditWindow = new PopupWindow(layout,
                userData.getScreenWidth() / 10 * 9,
                userData.getScreenHeight() / 10 * 9,
                true);
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

    /*
        Эффект затемнения при открытии окна редактирования профиля
    */

    private PopupWindow dimBackground() {
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

    public View getView() {
        return this.view;
    }

    public ImageView getImageAddUser() {
        return this.imageAddUser;
    }

    public Context getContext() {
        return view.getContext();
    }

    public TextView getStatusText() {
        return statusText;
    }

    public SquareViewPager getPhotoPager() {
        return photoPager;
    }

/****************************************************************************************
    Временное отключение кнопок
 *****************************************************************************************/

/*    public GridView getGridButtons() {
        return gridButtons;
    }*/

/****************************************************************************************
    Временное отключение фоток
 *****************************************************************************************/

/*    public ExpandableHeightGridView getGridPhoto() {
        return gridPhoto;
    }*/

    public ListView getListInfo() {
        return listInfo;
    }

/****************************************************************************************
    Временное отключение индикатора фоток
*****************************************************************************************/

/*    public CirclePageIndicator getPageIndicator() {
        return pageIndicator;
    }*/

    public int getAttr() {
        return attr;
    }

    public int getGridButtonHeight() {
        return gridButtonHeight;
    }

    public void setUpdateListener(UpdateProfileListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }

    /*   **********  Setters  **********  */

    public void setAdapterListEditProfile(AdapterListEditProfile adapterListEditProfile) {
        this.adapterListEditProfile = adapterListEditProfile;
    }


    /*   **********  Other  **********  */

    public interface UpdateProfileListener {
        void OnUpdateProfile();
        void OnChangeItem(int pos);
    }
}