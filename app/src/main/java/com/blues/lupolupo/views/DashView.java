package com.blues.lupolupo.views;

import android.support.v4.app.FragmentManager;

import com.blues.lupolupo.views.bases.BaseContextView;
import com.blues.lupolupo.views.bases.BaseEmptyRelativeLayoutView;
import com.blues.lupolupo.views.bases.BaseRecyclerView;

/**
 * @author Ritesh Shakya
 */
public interface DashView extends BaseContextView, BaseEmptyRelativeLayoutView, BaseRecyclerView {
    void initializeBasePageView();

    FragmentManager getFragmentManager();
}
