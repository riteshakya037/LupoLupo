package com.blues.lupolupo.views.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blues.lupolupo.model.Panel;
import com.blues.lupolupo.views.fragments.PanelFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class PanelPagerAdapter extends FragmentStatePagerAdapter {
    private List<Panel> mData;
    private String comic_id;

    public PanelPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        mData = new LinkedList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return PanelFragment.newInstance(mData.get(position), comic_id);
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    public void setData(List<Panel> result) {
        mData = result;
        this.notifyDataSetChanged();
    }

    public void setComicId(String comic_id) {
        this.comic_id = comic_id;
    }
}
