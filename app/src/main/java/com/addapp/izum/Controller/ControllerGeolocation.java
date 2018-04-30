package com.addapp.izum.Controller;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.addapp.izum.Adapter.AdapterMarkerMap;
import com.addapp.izum.Model.ModelGeolocation;
import com.addapp.izum.OtherClasses.ImageHandler;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewGeolocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by ILDAR on 21.07.2015.
 */
public class ControllerGeolocation {

    private static final long POLLING_FREQ = 1000 * 10;
    private static final float MIN_DISTANCE = 10.0f;

    private ModelGeolocation modelGeolocation;
    private ViewGeolocation viewGeolocation;
    private AdapterMarkerMap adapterMarkerMap;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private GoogleMap map;

    public ControllerGeolocation(ModelGeolocation modelGeolocation, ViewGeolocation viewGeolocation){
        this.modelGeolocation = modelGeolocation;
        this.viewGeolocation = viewGeolocation;

        mLocationManager = (LocationManager)viewGeolocation.getContext()
                .getSystemService(Context.LOCATION_SERVICE);
        bindListener();
    }

    private void bindListener() {

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                final double latitude = location.getLatitude(),
                        longitude = location.getLongitude();
                Log.e("ControllerGeolocation", "onLocationChanged: " +
                        "Location( " + latitude + ", " +
                        longitude + " )");

                new IconLoader(viewGeolocation
                        .getContext(),
                        R.drawable.ya,
                        new IconLoader.OnLoadListener() {
                            @Override
                            public void OnLoadIcon(Bitmap icon) {
                                Log.e("ControllerGeolocation", "OnLoadIcon");

                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        .icon(BitmapDescriptorFactory.fromBitmap(icon)));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(latitude, longitude))
                                        .zoom(12)
                                        .build();
                                map.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));
                            }
                        });
                mLocationManager.removeUpdates(mLocationListener);
                viewGeolocation.showCheckinMsg("Отметиться на карте, чтобы " +
                        "люди смогли увидеть вас.");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.e("ControllerGeolocation", "onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.e("ControllerGeolocation", "onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.e("ControllerGeolocation", "onProviderDisabled0.");
            }
        };

        viewGeolocation.setListener(new ViewGeolocation.OnReadyMapListener() {
            @Override
            public void ReadyMap(GoogleMap map) {
                Log.e("ControllerGeolocation", "ReadyMap");

                ControllerGeolocation.this.map = map;

                adapterMarkerMap
                        = new AdapterMarkerMap(viewGeolocation.getContext(),
                            modelGeolocation.getArray(),
                            map);

                viewGeolocation.showSearchUser();
                setLocationListener();
            }
        });

        viewGeolocation.getCheckButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewGeolocation.showUserInfo();
            }
        });
    }

    public void setLocationListener() {

        if (null != mLocationManager
                .getProvider(LocationManager.NETWORK_PROVIDER)){
            Log.e("ControllerGeolocation", "NETWORK_PROVIDER");
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, POLLING_FREQ,
                    MIN_DISTANCE, mLocationListener);
        }

        if (null != mLocationManager
                .getProvider(LocationManager.GPS_PROVIDER)){
            Log.e("ControllerGeolocation", "GPS_PROVIDER");
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, POLLING_FREQ,
                    MIN_DISTANCE, mLocationListener);
        }
    }

    private static class IconLoader implements Runnable{

        private Thread t;
        private Bitmap bm = null;
        private Context context;
        private int resIcon;
        private OnLoadListener mListener;
        private static final int HANDLE = 0;

        public IconLoader(Context context,
                          int resIcon,
                          OnLoadListener mListener) {
            this.context = context;
            this.resIcon = resIcon;
            this.mListener = mListener;
            t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HANDLE:
                        mListener.OnLoadIcon(bm);
                        break;
                }
            }
        };

        @Override
        public void run() {
            try {
                bm = ImageHandler.getSharedInstance(context)
                        .load(resIcon)
                        .resize(100, 0)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized (this){
                while (bm == null){
                    try{
                        wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            handler.obtainMessage(HANDLE).sendToTarget();
        }

        public interface OnLoadListener{
            void OnLoadIcon(Bitmap icon);
        }
    }
}
