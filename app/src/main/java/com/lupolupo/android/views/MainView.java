package com.lupolupo.android.views;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface MainView extends BaseContextView, BaseToolbarView {
    void initializeDrawerLayout();

    void closeDrawerLayout();

    @SuppressWarnings("SameReturnValue")
    int getMainLayoutId();
}
