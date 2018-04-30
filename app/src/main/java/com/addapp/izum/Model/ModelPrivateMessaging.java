package com.addapp.izum.Model;

import android.content.Context;

import com.addapp.izum.Adapter.AdapterPrivateMessaging;
import com.addapp.izum.Adapter.AdapterPrivateMessaging.ArrayImage;
import com.addapp.izum.OtherClasses.MainUserData;

import org.json.JSONException;

/**
 * Created by ILDAR on 05.08.2015.
 */
public class ModelPrivateMessaging {

    private MainUserData userData = MainUserData.getInstance();

    private ArrayImage images = new ArrayImage();

    public ModelPrivateMessaging(Context context) throws JSONException {

    }

    public AdapterPrivateMessaging.ArrayImage getImages() {
        return images.getImages();
    }

    public void setImages(ArrayImage images) {
        this.images.setImages(images);
    }

    public void removeImage(){
        this.images.removeImages();
    }
}
