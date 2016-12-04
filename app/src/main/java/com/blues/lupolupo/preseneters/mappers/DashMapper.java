package com.blues.lupolupo.preseneters.mappers;


import android.support.v4.app.FragmentStatePagerAdapter;

import com.blues.lupolupo.preseneters.bases.BaseRecycleAdapterMapper;

/**
 * @author Ritesh Shakya
 */
public interface DashMapper extends BaseRecycleAdapterMapper {

    void registerAdapter(FragmentStatePagerAdapter adapter);
}
