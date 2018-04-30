package com.addapp.izum.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.addapp.izum.OtherClasses.ImageLoader;
import com.addapp.izum.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ILDAR on 04.09.2015.
 */
public class AdapterMarkerMap implements Runnable {

    private static final int ADDMARKER = 0;

    private GoogleMap map;
    private ArrayList<MapItem> items;
    private MapItem item;
    private Marker marker;
    private Context context;
    private LayoutInflater inflater;
    private View markerView;
    private ImageView img;
    private Thread loadStream;
    private ImageHandler imageHandle;

    public AdapterMarkerMap(Context context, ArrayList<MapItem> items, GoogleMap map) {
        this.items = items;
        this.map = map;
        this.context = context;
        inflater = LayoutInflater.from(context);


        loadStream = new Thread(this);
        loadStream.setDaemon(true);

        imageHandle = new ImageHandler();

//        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        map.clear();

        markerView = inflater.inflate(R.layout.custom_marker_layout, null);

        img = (ImageView)markerView.findViewById(R.id.userImg);
        loadStream.start();

//        Bitmap bm;
//
//        for (int i = 0; i < items.size(); i++){
//
//            bm = null;
//
//            item = items.get(i);
//
//            ImageLoader il = new ImageLoader(context, item);
//
//            synchronized (this){
//                while(bm == null){
//                    try {
//                        wait(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    bm = il.getBitmap();
//                }
//            }

/*            img.setImageBitmap(bm);

            marker = map.addMarker(new MarkerOptions()
                    .position(item.getLatLng())
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(createDrawableFromView(context,
                                    markerView)))
                    .anchor(0.4f, 1f));
            items.get(i).setMarkerID(marker.getId());*/
//        }
    }

    public void setItems(ArrayList<MapItem> items) {
        this.items = items;
    }

    @Override
    public void run() {
        Bitmap bm;
        final boolean[] nextLoop = new boolean[1];

        Log.e("AdapterMarkerMap", "size: " + items.size());

        for (int i = 0; i < items.size(); i++){

            Log.e("AdapterMarkerMap", "1. loop( i = " + i + " )");

            bm = null;
            nextLoop[0] = false;

            item = items.get(i);

            ImageLoader il = new ImageLoader(context, item);

            synchronized (this){
                while(bm == null){
                    try {
                        wait(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bm = il.getBitmap();
                }
            }
            Log.e("AdapterMarkerMap", "2. loop( i = " + i + " )");
            Message msg = Message.obtain(imageHandle, ADDMARKER, i, 0, bm);

            imageHandle.setListener(new Listener() {
                @Override
                public void onNextLoop() {
                    nextLoop[0] = true;
                }
            });
            imageHandle.sendMessage(msg);
            Log.e("AdapterMarkerMap", "3. loop( i = " + i + " )");

            synchronized (this){
                while (!nextLoop[0]){
                    try {
                        wait(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.e("AdapterMarkerMap", "4. loop( i = " + i + " )");
        }
    }

    public static class MapItem{

        private final String userID;
        private final String userName;
        private LatLng latLng;
        private String markerID;
        private String url;

        private MapItem(Builder builder){
            userID = builder.userID;
            userName = builder.userName;
            latLng = builder.latLng;
            url = builder.url;
        }

        /* ------ Setters ------ */

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public void setMarkerID(String markerID) {
            this.markerID = markerID;
        }

        /* ------ Getters ------ */

        public LatLng getLatLng() {
            return latLng;
        }

        public String getMarkerID() {
            return markerID;
        }

        public String getUserID() {
            return userID;
        }

        public String getUserName() {
            return userName;
        }

        public String getUrl() {
            return url;
        }

        public static class Builder{
            private final String userID;
            private final String userName;
            private LatLng latLng;
            private String url;

            public Builder(String userID, String userName) {
                this.userID = userID;
                this.userName = userName;
            }

            public Builder setLatLng(LatLng latLng) {
                this.latLng = latLng;
                return this;
            }

            public Builder setAvatarUrl(String url){
                this.url = url;
                return this;
            }

            public MapItem build(){
                return new MapItem(this);
            }
        }
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private class ImageHandler extends Handler{

        private Listener mListener;

        public void setListener(Listener listener){
            mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ADDMARKER:

                    Log.e("AdapterMarkerMap", "Message: what = " + msg.what +
                            " arg1 = " + msg.arg1 + " arg2 = " + msg.arg2);

                    img.setImageBitmap((Bitmap) msg.obj);

                    marker = map.addMarker(new MarkerOptions()
                            .position(item.getLatLng())
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(createDrawableFromView(context,
                                            markerView)))
                            .anchor(0.4f, 1f));
                    items.get(msg.arg1).setMarkerID(marker.getId());
                    mListener.onNextLoop();
                    break;
            }
        }
    }

    public interface Listener{
        void onNextLoop();
    }
}
