package com.addapp.izum.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.addapp.izum.Activity.ImagePickerActivity;
import com.addapp.izum.Controller.ControllerProfile;
import com.addapp.izum.Interface.OnCustomClickListener;
import com.addapp.izum.Model.ModelProfile;
import com.addapp.izum.OtherClasses.SocketIO;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewProfile;

import nl.changer.polypicker.Config;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class Profile extends Fragment {

    private ViewProfile viewProfile;
    private ModelProfile modelProfile;
    private ControllerProfile controllerProfile;

    private static final int INTENT_REQUEST_GET_IMAGES = 20;
    private final String TAG = getClass().getSimpleName();

    private LinearLayout otherStatusLayout;
    private SocketIO mSocket = SocketIO.getInstance();
    private int state;                          // определение профиля (Свой или чужой)

    public Profile(LinearLayout otherStatusLayout, int state){
        this.state = state;
        this.otherStatusLayout = otherStatusLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        modelProfile = new ModelProfile(container.getContext());
        viewProfile = new ViewProfile(inflater.inflate(R.layout.layout_profile, container, false),
            otherStatusLayout, state);
        controllerProfile = new ControllerProfile(modelProfile, viewProfile);
        controllerProfile.setListener(clickAddPhotoListener);
        controllerProfile.bindListener();

        return viewProfile.getView();
    }

    private OnCustomClickListener clickAddPhotoListener =
            new OnCustomClickListener() {
                @Override
                public void onClick(int value) {
                    Intent intent = new Intent(getActivity(),
                            ImagePickerActivity.class);
                    Config config = new Config.Builder()
                            .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                            .setTabSelectionIndicatorColor(R.color.blue)
                            .setCameraButtonColor(R.color.orange)
                            .setSelectionLimit(1)    // set photo selection limit. Default unlimited selection.
                            .build();
                    ImagePickerActivity.setConfig(config);
                    ImagePickerActivity.setCurrentItem(value);
                    startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
                }
            };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case INTENT_REQUEST_GET_IMAGES:
                if(resultCode == FragmentActivity.RESULT_OK){
                    Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                    if (parcelableUris == null) {
                        return;
                    }

                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
                    for (Uri uri : uris)
//                        Log.e(TAG, "uri : " + uri.toString());
                        mSocket.sendURI(uri.toString());
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
