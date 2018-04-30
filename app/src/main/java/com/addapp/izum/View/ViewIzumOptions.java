package com.addapp.izum.View;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.R;

/**
 * Created by ILDAR on 16.09.2015.
 */
public class ViewIzumOptions extends CommonView {

    private LinearLayout mainLayout;
    private ListView optionList;

    public ViewIzumOptions(View view) {
        super(view);
    }

    @Override
    protected void init(View view) {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        optionList = new ListView(getContext());
    }

    @Override
    protected void setup() {
        optionList.setDividerHeight(0);
        optionList.setVerticalScrollBarEnabled(false);

        mainLayout.addView(optionList);
    }

    public ListView getOptionList() {
        return optionList;
    }
}
