package com.lupolupo.android.views.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lupolupo.android.model.Comic;
import com.lupolupo.android.views.fragments.LargeImageViewFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class DashLargeInvisiblePagerAdapter extends FragmentStatePagerAdapter {


    private List<Comic> mData;

    public DashLargeInvisiblePagerAdapter(FragmentManager fm) {
        super(fm);
        mData = new LinkedList<>();
    }

    @Override
    public int getCount() {
        return mData.size() > 5 ? 5 : mData.size();
    }

    @Override
    public Fragment getItem(int position) {
        return LargeImageViewFragment.newInstance(mData.get(position));
    }

    public void setData(List<Comic> result) {
        mData = result;
        this.notifyDataSetChanged();
    }


    public List<Comic> getData() {
        return mData;
    }
}
