package com.lupolupo.android.views;

import android.support.v4.app.FragmentManager;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseRecyclerView;

/**
 * @author Ritesh Shakya
 */
public interface DashView extends BaseContextView, BaseRecyclerView {
    void initializeBasePageView();

    FragmentManager getFragmentManager();
}
