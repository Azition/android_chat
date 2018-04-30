package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerGeolocation;
import com.addapp.izum.Model.ModelGeolocation;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewGeolocation;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class Geolocation extends Fragment {

    private ViewGeolocation viewGeolocation;
    private ModelGeolocation modelGeolocation;
    private ControllerGeolocation controllerGeolocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewGeolocation = new ViewGeolocation(inflater
                .inflate(R.layout.layout_frame_clear, container, false),
                getChildFragmentManager());
        modelGeolocation = new ModelGeolocation();
        controllerGeolocation = new ControllerGeolocation(modelGeolocation, viewGeolocation);

        return viewGeolocation.getView();
    }
}
