package com.addapp.izum.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.addapp.izum.Controller.ControllerProfile;
import com.addapp.izum.Model.ModelProfile;
import com.addapp.izum.R;
import com.addapp.izum.View.ViewProfile;

import org.json.JSONException;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class Profile extends Fragment {

    private ViewProfile viewProfile;
    private ModelProfile modelProfile;
    private ControllerProfile controllerProfile;

    private LinearLayout otherStatusLayout;

    public Profile(LinearLayout otherStatusLayout){
        this.otherStatusLayout = otherStatusLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewProfile = new ViewProfile(inflater.inflate(R.layout.layout_profile, container, false), otherStatusLayout, ViewProfile.MY_PROFILE);
        try {
            modelProfile = new ModelProfile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controllerProfile = new ControllerProfile(modelProfile, viewProfile);

        controllerProfile.bindListener();
/*
        LinearLayout pl = (LinearLayout)v.findViewById(R.id.profile_layout);

        for (int i = 1; i < 4; i++){
            ImageView iv = new ImageView(v.getContext());
            iv.setLayoutParams(new LinearLayout.LayoutParams(400, 400));

            int resId = v.getResources().getIdentifier("photo_" + i, "drawable", v.getContext().getPackageName());

            Picasso.with(v.getContext())
                    .load(resId)
                    .fit()
                    .centerCrop()
                    .into(iv);
            pl.addView(iv);
        }
*/

        return viewProfile.getView();
    }
}
