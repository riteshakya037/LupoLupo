package com.lupolupo.android.preseneters.mappers;

import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Ritesh Shakya
 */
public interface DashFragmentMapper {

    void registerAdapter(FragmentStatePagerAdapter adapter);

    void setCurrentPage(int position);
}