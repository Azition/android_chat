package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.Controller.ControllerIzumOptions;
import com.addapp.izum.Model.ModelIzumOptions;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewIzumOptions;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class IzumOptions extends Fragment {

    private ControllerIzumOptions cOptions;
    private ModelIzumOptions mOptions;
    private ViewIzumOptions vOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vOptions = new ViewIzumOptions(inflater.inflate(R.layout.layout_clear, container, false));
        mOptions = new ModelIzumOptions();
        cOptions = new ControllerIzumOptions(mOptions, vOptions);

        return vOptions.getView();
    }
}
