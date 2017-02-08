package com.lupolupo.android.views;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseRecyclerView;
import com.lupolupo.android.views.bases.BaseSpinnerView;

/**
 * @author Ritesh Shakya
 */
public interface GridView extends BaseContextView, BaseRecyclerView, BaseSpinnerView {
    void initializeBasePageView();

    void toggleCoverPagerLayout(boolean isVisible);

}
