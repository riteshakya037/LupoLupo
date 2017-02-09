package com.lupolupo.android.views;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseRecyclerView;

/**
 * @author Ritesh Shakya
 */
public interface GridView extends BaseContextView, BaseRecyclerView {
    void initializeBasePageView();

    void toggleCoverPagerLayout(boolean isVisible);

}
