package com.lupolupo.android.views;

import android.widget.ArrayAdapter;

import com.lupolupo.android.preseneters.MainPresenterImpl;
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

    void setAdapter(ArrayAdapter<String> dataAdapter);

    void setListeners(MainPresenterImpl.SpinnerInteractionListener onItemSelectedListener);
}
