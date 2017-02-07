package com.lupolupo.android.preseneters;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.lupolupo.android.preseneters.bases.BaseMenuPresenter;
import com.lupolupo.android.preseneters.bases.BasePresenter;

public interface MainPresenter extends NavigationView.OnNavigationItemSelectedListener, BasePresenter,BaseMenuPresenter {

    @Override
    boolean onNavigationItemSelected(@NonNull MenuItem item);
}
