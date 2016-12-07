package com.blues.lupolupo.views;

import com.blues.lupolupo.views.bases.BaseContextView;
import com.blues.lupolupo.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface MainView extends BaseContextView, BaseToolbarView {
    void initializeDrawerLayout();

    void closeDrawerLayout();

    @SuppressWarnings("SameReturnValue")
    int getMainLayoutId();
}
