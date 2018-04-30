package com.addapp.izum.View;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class ViewCommonPeople extends CommonView {

    private RelativeLayout mainLayout;
    private ListView rationList;
    private PullToRefreshListView toRefreshListView;

    public ViewCommonPeople(View view) {
        super(view);
    }

    @Override
    protected void init(View view) {
        mainLayout = (RelativeLayout)view.findViewById(R.id.main_layout);
        toRefreshListView = new PullToRefreshListView(getContext());
        rationList = toRefreshListView.getRefreshableView();
    }

    @Override
    protected void setup() {
        rationList.setDividerHeight(0);
        rationList.setVerticalScrollBarEnabled(false);

        toRefreshListView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        toRefreshListView.setScrollingWhileRefreshingEnabled(true);
        toRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        mainLayout.addView(toRefreshListView);
    }

    public ListView getRationList() {
        return rationList;
    }

    public PullToRefreshListView getToRefreshListView() {
        return toRefreshListView;
    }
}
