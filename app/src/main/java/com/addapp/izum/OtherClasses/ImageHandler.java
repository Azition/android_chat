package com.addapp.izum.OtherClasses;

import android.content.Context;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

/**
 * Created by ILDAR on 08.09.2015.
 */
public class ImageHandler {

    private static Picasso instance;

    public static Picasso getSharedInstance(Context context){
        if (instance == null){
            instance = new Picasso.Builder(context)
                    .executor(Executors.newSingleThreadExecutor())
                    .memoryCache(Cache.NONE)
                    .build();
        }
        return instance;
    }
}
