package com.lupolupo.android.preseneters.mappers;


import android.support.v4.app.FragmentStatePagerAdapter;

import com.lupolupo.android.preseneters.bases.BaseRecycleAdapterMapper;
import com.lupolupo.android.views.custom.infinite_view_pager.InfinitePagerAdapter;

/**
 * @author Ritesh Shakya
 */
public interface GridMapper extends BaseRecycleAdapterMapper {

    void registerAdapter(InfinitePagerAdapter adapter);

    void registerInvisibleAdapter(FragmentStatePagerAdapter adapter);

    void setCurrentPage(int position);
}
