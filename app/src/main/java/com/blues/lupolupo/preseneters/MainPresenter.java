package com.blues.lupolupo.preseneters;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.blues.lupolupo.preseneters.bases.BasePresenter;

public interface MainPresenter extends NavigationView.OnNavigationItemSelectedListener, BasePresenter {

    @Override
    boolean onNavigationItemSelected(@NonNull MenuItem item);

    void fragmentTransaction(Fragment fragment);
}
