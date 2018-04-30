package com.addapp.izum.View;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.addapp.izum.AbstractClasses.CommonView;
import com.addapp.izum.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

/**
 * Created by ILDAR on 31.08.2015.
 */
public class ViewNewPeople extends CommonView {

    private LinearLayout mainLayout;
    private PullToRefreshGridView toRefreshGridView;
    private GridView gridFindContext;

    public ViewNewPeople(View view) {
        super(view);
    }

    @Override
    protected void init(View view) {
        mainLayout = (LinearLayout)view.findViewById(R.id.main_layout);
        toRefreshGridView = new PullToRefreshGridView(getContext());
        gridFindContext = toRefreshGridView.getRefreshableView();
    }

    @Override
    protected void setup() {

        gridFindContext.setVerticalSpacing(0);
        gridFindContext.setHorizontalSpacing(0);

        gridFindContext.setNumColumns(3);
        gridFindContext.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        toRefreshGridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        toRefreshGridView.setScrollingWhileRefreshingEnabled(true);
        toRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        mainLayout.addView(toRefreshGridView);
    }

    public GridView getGridFindContext(){ return this.gridFindContext; }

    public PullToRefreshGridView getToRefreshGridView() {
        return toRefreshGridView;
    }
}
