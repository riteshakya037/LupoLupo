package com.blues.lupolupo.preseneters;

import android.content.Intent;
import android.view.MenuItem;

import com.blues.lupolupo.R;
import com.blues.lupolupo.views.MainView;

/**
 * @author Ritesh Shakya
 */
public class MainPresenterImpl implements MainPresenter {
    private final MainView mMainView;


    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
    }

    @Override
    public void initializeViews() {
        mMainView.initializeToolbar();
        mMainView.initializeDrawerLayout();
    }

    @Override
    public void initializeMainLayout(Intent argument) {

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mMainView.closeDrawerLayout();

        return true;
    }

}
