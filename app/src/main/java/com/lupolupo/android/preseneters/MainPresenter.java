package com.lupolupo.android.preseneters;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.lupolupo.android.preseneters.bases.BasePresenter;

public interface MainPresenter extends NavigationView.OnNavigationItemSelectedListener, BasePresenter {

    void initializeMenuItem();

    @Override
    boolean onNavigationItemSelected(@NonNull MenuItem item);

    void fragmentTransaction(Fragment fragment);
}
