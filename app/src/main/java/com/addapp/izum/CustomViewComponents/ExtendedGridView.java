package com.addapp.izum.CustomViewComponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by ILDAR on 21.07.2015.
 */
public class ExtendedGridView extends GridView {

    private static boolean DEBUG = false;

    private static class FixedViewInfo{

        public View view;
        public ViewGroup viewContainer;

        public Object data;

        public boolean isSelectable;
    }

    private int mNumColumns = AUTO_FIT;
    private int mRowHeight = -1;
    private View mViewForMeasureRowHeight = null;
    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<FixedViewInfo>();
    private static final String LOG_TAG = "GridViewHeaderAndFooter";
    private ListAdapter mOriginalAdapter;

    public ExtendedGridView(Context context) {
        super(context);
    }

    public ExtendedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = getAdapter();
        if(adapter != null && adapter instanceof HeaderViewGridAdapter){
            ((HeaderViewGridAdapter)adapter).setNumColumns(getNumColumnsCompatible());
            ((HeaderViewGridAdapter)adapter).setRowHeight(getRowHeight());
        }
    }

    public void addHeaderView(View v){
        addHeaderView(v, null, true);
    }

    public void addHeaderView(View v, Object data, boolean isSelectable){
        ListAdapter mAdapter = getAdapter();
        if(mAdapter != null && !(mAdapter instanceof HeaderViewGridAdapter)){
            throw new IllegalStateException(
                "Cannot add header view to grid -- setAdapter has already been called.");
        }
        ViewGroup.LayoutParams lyp = v.getLayoutParams();

        FixedViewInfo info = new FixedViewInfo();
        FrameLayout fl = new FullWidthFixedViewLayout(getContext());

        if(lyp != null){
            v.setLayoutParams(new FrameLayout.LayoutParams(lyp.width, lyp.height));
            fl.setLayoutParams(new AbsListView.LayoutParams(lyp.width, lyp.height));
        }
        fl.addView(v);
        info.view = v;
        info.viewContainer = fl;
        info.data = data;
        info.isSelectable = isSelectable;

        if(mAdapter != null){
            ((HeaderViewGridAdapter) mAdapter).notifyDataSetChanged();
        }
    }

    @TargetApi(11)
    private int getNumColumnsCompatible(){
        if (Build.VERSION.SDK_INT >= 11){
            return super.getNumColumns();
        } else {
            try {
                Field numColumns = GridView.class.getDeclaredField("mNumColumns");
                numColumns.setAccessible(true);
                return numColumns.getInt(this);
            } catch (Exception e){
                if(mNumColumns != -1){
                    return mNumColumns;
                }
                throw new RuntimeException("Can not determine the mNumColumns for this API platform, please call setNumColumns to set it.");
            }
        }
    }

    @TargetApi(16)
    private int getColumnWidthCompatible(){
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getColumnWidth();
        } else {
            try {
                Field numColumns = GridView.class.getDeclaredField("mColumnWidth");
                numColumns.setAccessible(true);
                return numColumns.getInt(this);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getRowHeight(){
        if(mRowHeight > 0){
            return mRowHeight;
        }
        ListAdapter adapter = getAdapter();
        int numColumns = getNumColumnsCompatible();

        if(adapter == null || adapter.getCount() <= numColumns * mHeaderViewInfos.size()){
            return -1;
        }

        int mColumnWidth = getColumnWidthCompatible();
        View view = getAdapter().getView(numColumns * mHeaderViewInfos.size(), mViewForMeasureRowHeight, this);
        AbsListView.LayoutParams p = (AbsListView.LayoutParams)view.getLayoutParams();
        if(p == null){
            p = new AbsListView.LayoutParams(-1, -2, 0);
            view.setLayoutParams(p);
        }
        int childHeightSpec = getChildMeasureSpec(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 0, p.height);
        int childWidthSpec = getChildMeasureSpec(
                MeasureSpec.makeMeasureSpec(mColumnWidth, MeasureSpec.EXACTLY), 0, p.width);
        view.measure(childWidthSpec, childHeightSpec);
        mViewForMeasureRowHeight = view;
        mRowHeight = view.getMeasuredHeight();
        return mRowHeight;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mOriginalAdapter = adapter;
        if(mHeaderViewInfos.size() > 0){
            HeaderViewGridAdapter headerViewGridAdapter = new HeaderViewGridAdapter(mHeaderViewInfos, adapter);
            int numColumns = getNumColumnsCompatible();
            if (numColumns > 1){
                headerViewGridAdapter.setNumColumns(numColumns);
            }
            headerViewGridAdapter.setRowHeight(getRowHeight());
            super.setAdapter(headerViewGridAdapter);
        } else{
            super.setAdapter(adapter);
        }
    }



    private class FullWidthFixedViewLayout extends FrameLayout {

        public FullWidthFixedViewLayout(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int realLeft = ExtendedGridView.this.getPaddingLeft() + getPaddingLeft();
            // Try to make where it should be, from left, full width
            if (realLeft != left) {
                offsetLeftAndRight(realLeft - left);
            }
            super.onLayout(changed, left, top, right, bottom);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int targetWidth = ExtendedGridView.this.getMeasuredWidth()
                    - ExtendedGridView.this.getPaddingLeft()
                    - ExtendedGridView.this.getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth,
                    MeasureSpec.getMode(widthMeasureSpec));
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        mNumColumns = numColumns;
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter instanceof HeaderViewGridAdapter) {
            ((HeaderViewGridAdapter) adapter).setNumColumns(numColumns);
        }
    }

    private static class HeaderViewGridAdapter implements WrapperListAdapter, Filterable{

        private final DataSetObservable mDataSetObservable = new DataSetObservable();
        private ListAdapter mAdapter;
        static final ArrayList<FixedViewInfo> EMPTY_INFO_LIST =
                new ArrayList<FixedViewInfo>();

        ArrayList<FixedViewInfo> mHeaderViewInfos;
        private final boolean mIsFilterable;
        boolean mAreAllFixedViewsSelectable;
        private int mNumColumns = 1;
        private int mRowHeight = -1;
        private boolean mCachePlaceHoldView = true;
        private boolean mCacheFirstHeaderView = false;

        public HeaderViewGridAdapter(ArrayList<FixedViewInfo> headerViewInfos, ListAdapter adapter){
            mAdapter = adapter;
            mIsFilterable = adapter instanceof Filterable;
            if (headerViewInfos == null){
                mHeaderViewInfos = EMPTY_INFO_LIST;
            } else {
                mHeaderViewInfos = headerViewInfos;
            }

            mAreAllFixedViewsSelectable = areAllListInfosSelectable(mHeaderViewInfos);
        }

        private boolean areAllListInfosSelectable(ArrayList<FixedViewInfo> infos){
            if(infos != null){
                for(FixedViewInfo info : infos){
                    if(!info.isSelectable){
                        return false;
                    }
                }
            }
            return true;
        }

        public void setNumColumns(int numColumns) {
            if (numColumns < 1) {
                return;
            }
            if (mNumColumns != numColumns) {
                mNumColumns = numColumns;
                notifyDataSetChanged();
            }
        }

        public void setRowHeight(int height) {
            mRowHeight = height;
        }

        public int getHeadersCount() {
            return mHeaderViewInfos.size();
        }

        private int getAdapterAndPlaceHolderCount() {
            return (int) (Math.ceil(1f * mAdapter.getCount() / mNumColumns) * mNumColumns);
        }

        @Override
        public ListAdapter getWrappedAdapter() {
            return mAdapter;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return mAdapter == null || mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
        }

        @Override
        public boolean isEnabled(int i) {
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (i < numHeadersAndPlaceholders){
                return i % mNumColumns == 0
                        && mHeaderViewInfos.get(i / mNumColumns).isSelectable;
            }

            final int adjPosition = i - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null){
                adapterCount = getAdapterAndPlaceHolderCount();
                if (adjPosition < adapterCount){
                    return adjPosition < mAdapter.getCount() && mAdapter.isEnabled(adjPosition);
                }
            }
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            mDataSetObservable.registerObserver(dataSetObserver);
            if(mAdapter != null){
                mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            mDataSetObservable.unregisterObserver(dataSetObserver);
            if(mAdapter != null){
                mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }

        @Override
        public int getCount() {
            if (mAdapter != null){
                return getHeadersCount() * mNumColumns + getAdapterAndPlaceHolderCount();
            } else {
                return getHeadersCount() * mNumColumns;
            }
        }

        @Override
        public Object getItem(int i) {
            int numHeaderAndPlaceholders = getHeadersCount() * mNumColumns;
            if(i < numHeaderAndPlaceholders){
                if(i % mNumColumns == 0){
                    return mHeaderViewInfos.get(i / mNumColumns).data;
                }
                return null;
            }

            final int adjPosition = i - numHeaderAndPlaceholders;
            int adapterCount;
            if (mAdapter != null){
                adapterCount = getAdapterAndPlaceHolderCount();
                if (adjPosition < adapterCount){
                    if (adjPosition < mAdapter.getCount()){
                        return mAdapter.getItem(adjPosition);
                    } else {
                        return null;
                    }
                }
            }

            return null;
        }

        @Override
        public long getItemId(int i) {
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if(mAdapter != null && i >= numHeadersAndPlaceholders){
                int adjPosition = i - numHeadersAndPlaceholders;
                int adapterCount = mAdapter.getCount();
                if(adjPosition < adapterCount){
                    return mAdapter.getItemId(adjPosition);
                }
            }

            return -1;
        }

        @Override
        public boolean hasStableIds() {
            return mAdapter != null && mAdapter.hasStableIds();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(DEBUG){
                Log.d(LOG_TAG,String.format("getView: %s, reused: %s", i, view == null));
            }

            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if(i < numHeadersAndPlaceholders){
                View headerViewContainer = mHeaderViewInfos
                        .get(i / mNumColumns).viewContainer;
                if(i % mNumColumns == 0){
                    return headerViewContainer;
                } else {
                    if(view == null){
                        view = new View(viewGroup.getContext());
                    }

                    view.setVisibility(INVISIBLE);
                    view.setMinimumHeight(headerViewContainer.getHeight());
                    return view;
                }
            }

            final int adjPosition  = i - numHeadersAndPlaceholders;
            int adapterCount;
            if (mAdapter != null){
                adapterCount = getAdapterAndPlaceHolderCount();
                if(adjPosition < adapterCount){
                    if(adjPosition < mAdapter.getCount()){
                        return  mAdapter.getView(adjPosition, view, viewGroup);
                    } else {
                        if (view == null){
                            view = new View(viewGroup.getContext());
                        }

                        view.setVisibility(INVISIBLE);
                        view.setMinimumHeight(mRowHeight);
                        return view;
                    }
                }
            }
            throw new ArrayIndexOutOfBoundsException(i);
        }

        @Override
        public int getItemViewType(int i) {
            final int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            final int adapterViewTypeStart = mAdapter == null ? 0 : mAdapter.getViewTypeCount() - 1;
            int type = AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
            if(mCachePlaceHoldView){
                if (i < numHeadersAndPlaceholders){
                    if(i == 0){
                        if(mCacheFirstHeaderView){
                            type = adapterViewTypeStart + mHeaderViewInfos.size() + 1;
                        }
                    }
                    if(i % mNumColumns != 0){
                        type = adapterViewTypeStart + (i / mNumColumns + 1);
                    }
                }
            }

            final int adjPosition = i - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = getAdapterAndPlaceHolderCount();
                if (adjPosition >= 0 && adjPosition < adapterCount) {
                    if (adjPosition < mAdapter.getCount()) {
                        type = mAdapter.getItemViewType(adjPosition);
                    } else {
                        if (mCachePlaceHoldView) {
                            type = adapterViewTypeStart + mHeaderViewInfos.size() + 1;
                        }
                    }
                }
            }

            if (DEBUG) {
                Log.d(LOG_TAG, String.format("getItemViewType: pos: %s, result: %s", i, type, mCachePlaceHoldView, mCacheFirstHeaderView));
            }
            return type;
        }

        @Override
        public int getViewTypeCount() {
            int count = mAdapter == null ? 1 : mAdapter.getViewTypeCount();
            if(mCachePlaceHoldView){
                int offset = mHeaderViewInfos.size() + 1;
                if (mCacheFirstHeaderView){
                    offset += 1;
                }
                count += offset;
            }

            if(DEBUG){
                Log.d(LOG_TAG, String.format("getViewTypeCount: %s", count));
            }
            return count;
        }

        @Override
        public boolean isEmpty() {
            return (mAdapter == null || mAdapter.isEmpty());
        }

        @Override
        public Filter getFilter() {
            if (mIsFilterable) {
                return ((Filterable) mAdapter).getFilter();
            }
            return null;
        }

        public void notifyDataSetChanged() {
            mDataSetObservable.notifyChanged();
        }
    }
}
