package com.lupolupo.android.views.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.Panel;
import com.lupolupo.android.views.fragments.FlipPagerFragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ritesh Shakya
 */
public class FlipPagerAdapter extends FragmentStatePagerAdapter {

    private List<Episode> mData;

    public FlipPagerAdapter(FragmentManager fm) {
        super(fm);
        mData = new LinkedList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Fragment getItem(int position) {
        return FlipPagerFragment.newInstance(mData.get(position));
    }

    public void setData(Set<Episode> result) {
        mData = new LinkedList<>(result);
        this.notifyDataSetChanged();
    }

    public List<Episode> getData() {
        return mData;
    }
}