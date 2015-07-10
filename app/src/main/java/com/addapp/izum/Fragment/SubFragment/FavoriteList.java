package com.addapp.izum.Fragment.SubFragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addapp.izum.R;

/**
 * Created by ILDAR on 19.06.2015.
 */
public class FavoriteList extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.layout_favorite_list, container, false);
        }
}
