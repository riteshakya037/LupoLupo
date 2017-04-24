package com.lupolupo.android.views.custom.infinite_view_pager;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Ritesh Shakya
 */
public abstract class InfinitePagerAdapter extends StatePagerAdapter {


    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }

    @Override
    public final int getCount() {
        return getItemCount() * ViewPagerCustomDuration.FakePositionHelper.MULTIPLIER;
    }

    @Deprecated
    protected View getViewInternal(int position, View convertView, ViewGroup container) {
        if (getItemCount() == 0)
            return null;
        return getView(position % getItemCount(), convertView, container);
    }

    public abstract int getItemCount();

}