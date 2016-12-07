package com.blues.lupolupo.views.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.views.fragments.LargeImageViewFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class DashLargePagerAdapter extends FragmentStatePagerAdapter {


    private List<Comic> mData;

    public DashLargePagerAdapter(FragmentManager fm) {
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
}
