package com.lupolupo.android.views.custom.infinite_view_pager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author Ritesh Shakya
 */

abstract class StatePagerAdapter extends PagerAdapter {
    private static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;

    private final StateBin stateBin;

    StatePagerAdapter() {
        this(new StateBin());
    }

    private StatePagerAdapter(StateBin stateBin) {
        this.stateBin = stateBin;
        stateBin.setViewTypeCount(getViewTypeCount());
    }

    @Override
    public void notifyDataSetChanged() {
        stateBin.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        int viewType = getItemViewType();
        View view = null;
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            view = stateBin.getScrapView(position, viewType);
        }
        view = getViewInternal(position, view, container);
        container.addView(view);
        return view;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        int viewType = getItemViewType();
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            stateBin.addScrapView(view, position, viewType);
        }
    }

    protected View getViewInternal(int position, View convertView, ViewGroup container) {
        return getView(position, convertView, container);
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private int getViewTypeCount() {
        return 1;
    }

    private int getItemViewType() {
        return 0;
    }

    public abstract View getView(int position, View convertView, ViewGroup container);
}