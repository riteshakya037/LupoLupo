package com.blues.lupolupo.preseneters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.blues.lupolupo.preseneters.bases.BasePresenter;

public interface MainPresenter extends NavigationView.OnNavigationItemSelectedListener, BasePresenter {

    void initializeMainLayout(Intent argument);

    @Override
    boolean onNavigationItemSelected(@NonNull MenuItem item);
}
