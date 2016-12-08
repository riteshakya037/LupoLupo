package com.lupolupo.android.preseneters.mappers;


import android.support.v4.app.FragmentStatePagerAdapter;

import com.lupolupo.android.preseneters.bases.BaseRecycleAdapterMapper;

/**
 * @author Ritesh Shakya
 */
public interface DashMapper extends BaseRecycleAdapterMapper {

    void registerAdapter(FragmentStatePagerAdapter adapter);
}
