package com.addapp.izum.Interface;

import com.addapp.izum.Model.ModelFindPeople;

/**
 * Created by ILDAR on 13.07.2015.
 */
public interface OnShowFragment {
    public void showFragment(ModelFindPeople.FindListItem item, int attr);
    public void rewriteTitle(int position);
}
