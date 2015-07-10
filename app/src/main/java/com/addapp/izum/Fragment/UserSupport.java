package com.addapp.izum.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.OtherClasses.NotificationService;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class UserSupport extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_support, container, false);
    }
}
