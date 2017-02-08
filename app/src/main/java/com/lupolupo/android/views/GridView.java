package com.lupolupo.android.views;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseRecyclerView;
import com.lupolupo.android.views.bases.BaseSpinnerView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface GridView extends BaseToolbarView, BaseContextView, BaseRecyclerView, BaseSpinnerView {
    void initializeBasePageView();

    void toggleCoverPagerLayout(boolean isVisible);

}
