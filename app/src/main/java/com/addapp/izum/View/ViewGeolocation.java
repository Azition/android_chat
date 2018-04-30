package com.addapp.izum.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.Animations.GeoUserInfoBottomAnimation;
import com.addapp.izum.Animations.GeoUserInfoTopAnimation;
import com.addapp.izum.CustomViewComponents.GeoTimePickerDialog;
import com.addapp.izum.OtherClasses.MainUserData;
import com.addapp.izum.OtherClasses.Utils;
import com.addapp.izum.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by ILDAR on 21.07.2015.
 */
public class ViewGeolocation extends CommonView
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final boolean DEBUG = false;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private SupportMapFragment mapFragment;
    private FrameLayout mainLayout;
    private FrameLayout mapLayout;
    private ImageView checkButton;
    private ImageView zoomButton;
    private OnReadyMapListener listener;
    private GoogleApiClient mGoogleApiClient;

    int IDUserInfoLayout = 0;
    int ID = 0;

    public ViewGeolocation(View view, FragmentManager fragmentManager) {
        super(view, fragmentManager);
    }

    @Override
    protected void init(View view) {
        mapFragment = SupportMapFragment.newInstance();
        if(checkPlayServices()){
            Log.e("GeoLocation", "Service enable");
            buildGoogleApiClient();
        }

        IDUserInfoLayout = Utils.generateViewId();

        mainLayout = (FrameLayout)view.findViewById(R.id.mainLayout);
        mapLayout = new FrameLayout(getContext());
        mapLayout.setId(Utils.generateViewId());
        checkButton = new ImageView(getContext());
        zoomButton = new ImageView(getContext());
    }

    @Override
    protected void setup() {
        FrameLayout.LayoutParams params;
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.bottomMargin = 200;
        params.rightMargin = 14;
        zoomButton.setLayoutParams(params);
        zoomButton.setImageResource(R.drawable.icon_zoom);

        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.bottomMargin = 50;
        params.rightMargin = 14;
        checkButton.setLayoutParams(params);
        checkButton.setImageResource(R.drawable.icon_checkin);

        params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mapLayout.setLayoutParams(params);

        fragmentManager.beginTransaction()
                .add(mapLayout.getId(), mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        mainLayout.addView(mapLayout);
        mainLayout.addView(zoomButton);
        mainLayout.addView(checkButton);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (DEBUG) Log.e("ViewGeolocation", "onMapReady");
        if (googleMap.getUiSettings().isMapToolbarEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isMapToolbarEnabled: true");
            googleMap.getUiSettings().setMapToolbarEnabled(false);
        }
        if (googleMap.getUiSettings().isCompassEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isCompassEnabled: true");
            googleMap.getUiSettings().setCompassEnabled(false);
        }
        if (googleMap.getUiSettings().isRotateGesturesEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isRotateGesturesEnabled: true");
            googleMap.getUiSettings().setRotateGesturesEnabled(false);
        }
        if (googleMap.getUiSettings().isIndoorLevelPickerEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isIndoorLevelPickerEnabled: true");
            googleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        }
        if (googleMap.getUiSettings().isMyLocationButtonEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isMyLocationButtonEnabled: true");
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        if (googleMap.getUiSettings().isTiltGesturesEnabled()) {
            if (DEBUG) Log.e("ViewGeolocation", "isTiltGesturesEnabled: true");
            googleMap.getUiSettings().setTiltGesturesEnabled(false);
        }

        listener.ReadyMap(googleMap);
    }

    public ImageView getCheckButton() {
        return checkButton;
    }

    public void setListener(OnReadyMapListener listener){
        if (DEBUG) Log.e("ViewGeolocation", "setListener");
        this.listener = listener;
    }

    /*  Метод отображения окна поиска при входе в геолокацию */

    public void showSearchUser(){
        checkButton.setVisibility(View.GONE);
        zoomButton.setVisibility(View.GONE);

        int winHeight = MainUserData.percentToPix(16,MainUserData.HEIGHT);

        LinearLayout windowStatus = new LinearLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    winHeight);
        params.gravity = Gravity.BOTTOM;
        windowStatus.setLayoutParams(params);
        windowStatus.setGravity(Gravity.CENTER_VERTICAL);
        windowStatus.setOrientation(LinearLayout.HORIZONTAL);
        windowStatus.setBackgroundColor(Color.WHITE);

        TextView statusText = new TextView(getContext());
        LinearLayout.LayoutParams linearParams = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.weight = 1;
        linearParams.leftMargin = MainUserData
                .percentToPix(3, MainUserData.WIDTH);
        statusText.setLayoutParams(linearParams);
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        statusText.setTextColor(getContext().getResources()
                .getColor(R.color.izum_color));
        statusText.setText("Пытаемся найти вас...");

        ImageView statusImg = new ImageView(getContext());
        linearParams = new LinearLayout
                .LayoutParams((int)(winHeight * 0.6),
                (int)(winHeight * 0.6));
        linearParams.rightMargin = (int)(winHeight / 2 * 0.4);
        statusImg.setLayoutParams(linearParams);
        Picasso.with(getContext())
                .load(R.drawable.geo_search_icon)
                .into(statusImg);
        ID = Utils.generateViewId();
        windowStatus.setId(ID);

        windowStatus.addView(statusText);
        windowStatus.addView(statusImg);
        mainLayout.addView(windowStatus);
    }

    public void showCheckinMsg(String msg){

        TableLayout windowStatus = new TableLayout(getContext());
        int winHeight = MainUserData.percentToPix(16,MainUserData.HEIGHT);
        int halfWinWidth = MainUserData.percentToPix(50,MainUserData.WIDTH);

        for (int i = 0; i < mainLayout.getChildCount(); i++){
            if(mainLayout.getChildAt(i) instanceof LinearLayout){
                Log.e("ViewGeolocation", "LinearLayout найден: №" + i);
                mainLayout.removeViewAt(i);
            }
        }

        FrameLayout.LayoutParams params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        windowStatus.setLayoutParams(params);
        windowStatus.setBackgroundColor(Color.WHITE);
        windowStatus.setStretchAllColumns(true);
        windowStatus.setShrinkAllColumns(true);

        TableRow rowStatus = new TableRow(getContext());
        rowStatus.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowButtons = new TableRow(getContext());

        TextView statusText = new TextView(getContext());
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        statusText.setGravity(Gravity.CENTER);
        statusText.setTextColor(getContext().getResources()
                .getColor(R.color.izum_color));
        statusText.setText(msg);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
        rowParams.span = 2;
        rowParams.topMargin = (int) (winHeight * 0.1);
        rowParams.bottomMargin = (int) (winHeight * 0.1);
        rowParams.rightMargin =  MainUserData
                .percentToPix(8,MainUserData.WIDTH);
        rowParams.leftMargin =  MainUserData
                .percentToPix(8, MainUserData.WIDTH);

        rowStatus.addView(statusText, rowParams);

        Button checkin = new Button(getContext());
        checkin.setText("Отметиться");
        checkin.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        checkin.setTextColor(Color.WHITE);
        checkin.setBackgroundResource(R.drawable.button);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSearchUser();
                GeoTimePickerDialog dialog =
                        new GeoTimePickerDialog(getContext());
                dialog.show();
            }
        });

        Button cancel = new Button(getContext());
        cancel.setText("Отмена");
        cancel.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(22));
        cancel.setTextColor(Color.WHITE);
        cancel.setBackgroundResource(R.drawable.button);

        rowParams = new TableRow.LayoutParams(halfWinWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.topMargin = 8;
        rowParams.bottomMargin = 8;
        rowParams.rightMargin = (int) (halfWinWidth * 0.1);
        rowParams.leftMargin = (int) (halfWinWidth * 0.1);

        rowButtons.addView(checkin, rowParams);
        rowButtons.addView(cancel, rowParams);

        windowStatus.addView(rowStatus);
        windowStatus.addView(rowButtons);

        mainLayout.addView(windowStatus);
    }

    public void hideSearchUser(){
        Log.e("ViewGeolocation", "Child count = " + mainLayout.getChildCount());
        for (int i = 0; i < mainLayout.getChildCount(); i++){
            if(mainLayout.getChildAt(i) instanceof LinearLayout ||
                    mainLayout.getChildAt(i) instanceof TableLayout){
                Log.e("ViewGeolocation", "LinearLayout найден: №" + i);
                mainLayout.removeViewAt(i);
            }
        }
        checkButton.setVisibility(View.VISIBLE);
        zoomButton.setVisibility(View.VISIBLE);
    }

    public void showUserInfo(){
        checkButton.setVisibility(View.GONE);
        zoomButton.setVisibility(View.GONE);

        int winHeight = MainUserData.percentToPix(10,MainUserData.HEIGHT);

        final FrameLayout infoLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        infoLayout.setLayoutParams(params);
        infoLayout.setId(IDUserInfoLayout);

        final FrameLayout buttonMsgLayout = new FrameLayout(getContext());
        params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                winHeight);
        params.gravity = Gravity.TOP;
        buttonMsgLayout.setLayoutParams(params);
        buttonMsgLayout.setBackgroundResource(R.drawable.rectangle_button);
        buttonMsgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView sendMsg = new TextView(getContext());
        RelativeLayout.LayoutParams relativeParams =
                new RelativeLayout
                        .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        sendMsg.setLayoutParams(params);
        sendMsg.setId(Utils.generateViewId());
        sendMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(21));
        sendMsg.setTextColor(Color.WHITE);
        sendMsg.setText("Написать сообщение");

        View closeLayout = new View(getContext()){

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

                int width = View.MeasureSpec.getSize(widthMeasureSpec);
                int height = View.MeasureSpec.getSize(heightMeasureSpec);

                Log.e("ViewGeolocation", "onMeasure: " +
                        "width: " + width + " height: " + height);

                int widthMode = MeasureSpec.EXACTLY;

                int maxSize = Math.min(width, height);

                Log.e("ViewGeolocation", "onMeasure: " +
                        "maxSize: " + maxSize);

                maxSize = View.MeasureSpec
                        .makeMeasureSpec(maxSize, widthMode);
                super.onMeasure(maxSize, maxSize);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Rect rect = canvas.getClipBounds();
                Log.e("ViewGeolocation", "rect.bottom: " + rect.bottom);
                Log.e("ViewGeolocation", "rect.right: " + rect.right);
                float widthLayout = rect.right;
                float heightLayout = rect.bottom;
                float offset = widthLayout * 0.04f;
                Bitmap icon = BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.user_click_close);
                float width = icon.getWidth() * 0.5f;
                float height = icon.getHeight() * 0.5f;
                RectF r = new RectF(widthLayout/2 - width/2 + offset,
                        heightLayout/2 - height/2,
                        widthLayout/2 + width/2 + offset,
                        heightLayout/2 + height/2);
                canvas.drawBitmap(icon, null, r, null);
            }
        };
        relativeParams = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.RIGHT;
        closeLayout.setLayoutParams(params);
        closeLayout
                .setBackgroundResource(R.drawable.rectangle_button_transparent);

        winHeight = MainUserData.percentToPix(20,MainUserData.HEIGHT);

        final RelativeLayout userInfoLayout = new RelativeLayout(getContext());
        params = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                winHeight);
        params.gravity = Gravity.BOTTOM;
        userInfoLayout.setLayoutParams(params);
        userInfoLayout.setBackgroundResource(R.color.izum_color);

        TextView userNameAge = new TextView(getContext());
        relativeParams = new RelativeLayout
                        .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.topMargin = 20;
        relativeParams.leftMargin = 20;
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        userNameAge.setLayoutParams(relativeParams);
        userNameAge.setId(Utils.generateViewId());
        userNameAge.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(21));
        userNameAge.setTextColor(Color.WHITE);
        userNameAge.setText("Guzel1991, 23");

        TextView userStatus = new TextView(getContext());
        relativeParams = new RelativeLayout
                    .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.topMargin = 20;
        relativeParams.leftMargin = 20;
        relativeParams.addRule(RelativeLayout.BELOW, userNameAge.getId());
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        userStatus.setLayoutParams(relativeParams);
        userStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                MainUserData.setTextSize(18));
        userStatus.setTextColor(Color.WHITE);
        userStatus.setText("Привет котаны, че не спите?\nГо ко мне )");

        View enterProfile = new View(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Rect rect = canvas.getClipBounds();
                Log.e("ViewGeolocation", "rect.bottom: " + rect.bottom);
                Log.e("ViewGeolocation", "rect.right: " + rect.right);
                float widthLayout = rect.right;
                float heightLayout = rect.bottom;
                float offset = widthLayout * 0.04f;
                Bitmap icon = BitmapFactory
                        .decodeResource(getResources(), R.drawable.right_geo);
                float width = icon.getWidth() * 0.6f;
                float height = icon.getHeight() * 0.6f;
                RectF r = new RectF(widthLayout/2 - width/2 + offset,
                        heightLayout/2 - height/2,
                        widthLayout/2 + width/2 + offset,
                        heightLayout/2 + height/2);
                canvas.drawBitmap(icon, null, r, null);
            }
        };
        relativeParams = new RelativeLayout
                .LayoutParams(MainUserData.percentToPix(25, MainUserData.WIDTH),
                ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        enterProfile.setLayoutParams(relativeParams);
        enterProfile.setBackgroundResource(R.drawable.rectangle_button);

        /************************************************
         (begin) buttons init
         ************************************************/
        closeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /************************************************
                 (begin) hide animation start
                 ************************************************/

                buttonMsgLayout.startAnimation(new GeoUserInfoTopAnimation(GeoUserInfoTopAnimation.HIDE));
                GeoUserInfoBottomAnimation animation = new GeoUserInfoBottomAnimation(GeoUserInfoBottomAnimation.HIDE);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        for (int i = 0; i < mainLayout.getChildCount(); i++) {
                            if (mainLayout.getChildAt(i).getId() == IDUserInfoLayout) {
                                Log.e("ViewGeolocation", "infoLayout удален");
                                mainLayout.removeViewAt(i);
                            }
                        }
                        checkButton.setVisibility(View.VISIBLE);
                        zoomButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                userInfoLayout.startAnimation(animation);

                /************************************************
                 (end) hide animation start
                 ************************************************/
            }
        });

        enterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /************************************************
         (end) buttons init
         ************************************************/


        buttonMsgLayout.addView(sendMsg);
        buttonMsgLayout.addView(closeLayout);

        userInfoLayout.addView(enterProfile);
        userInfoLayout.addView(userNameAge);
        userInfoLayout.addView(userStatus);

        infoLayout.addView(buttonMsgLayout);
        infoLayout.addView(userInfoLayout);
        mainLayout.addView(infoLayout);

        /************************************************
         (begin) show animation start
         ************************************************/

        buttonMsgLayout.startAnimation(new GeoUserInfoTopAnimation(GeoUserInfoTopAnimation.SHOW));
        userInfoLayout.startAnimation(new GeoUserInfoBottomAnimation(GeoUserInfoBottomAnimation.SHOW));

        /************************************************
         (end) show animation start
         ************************************************/
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("ViewGeolocation", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("ViewGeolocation", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("ViewGeolocation", "onConnectionFailed");
    }

    public interface OnReadyMapListener{
        void ReadyMap(GoogleMap map);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (FragmentActivity)getContext(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient(){
        Log.e("ViewGeolocation", "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
