package com.addapp.izum.Fragment.SubFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerPrivateMessaging;
import com.addapp.izum.CustomViewComponents.ChatBottom.OnClickAddPhotoListener;
import com.addapp.izum.Model.ModelPrivateMessaging;
import com.addapp.izum.Activity.ImagePickerActivity;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewPrivateMessaging;
import com.addapp.izum.Adapter.AdapterPrivateMessaging.ArrayImage;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import org.json.JSONException;

import nl.changer.polypicker.Config;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class PrivateMessaging extends Fragment implements EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener  {

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final String TAG = PrivateMessaging.class.getSimpleName();

    private ControllerPrivateMessaging cPM;
    private ModelPrivateMessaging mPM;
    private ViewPrivateMessaging vPM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            mPM = new ModelPrivateMessaging(container.getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vPM = new ViewPrivateMessaging(inflater.inflate(R.layout.layout_clear, container, false), getChildFragmentManager());
        cPM = new ControllerPrivateMessaging(vPM, mPM);
        cPM.setListener(clickAddPhotoListener);

        return vPM.getView();
    }

    private OnClickAddPhotoListener clickAddPhotoListener = new OnClickAddPhotoListener() {
        @Override
        public void onAddPhoto() {
            Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
            Config config = new Config.Builder()
                    .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                    .setTabSelectionIndicatorColor(R.color.blue)
                    .setCameraButtonColor(R.color.orange)
                    .setSelectionLimit(5)    // set photo selection limit. Default unlimited selection.
                    .build();
            ImagePickerActivity.setConfig(config);
            startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "RequestCode: " + requestCode + "  ResultCode: " + resultCode);
        switch (requestCode){
            case INTENT_REQUEST_GET_IMAGES:
                if(resultCode == FragmentActivity.RESULT_OK){
                    Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                    if (parcelableUris == null) {
                        return;
                    }

                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
                    ArrayImage images = new ArrayImage();
                    for(Uri uri : uris)
                        images.addImage(uri.toString());
                    mPM.setImages(images);
                    cPM.showPhotos();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }



    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(vPM.getChatBottom().getMsgText());
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(vPM.getChatBottom().getMsgText(), emojicon);
    }
}
