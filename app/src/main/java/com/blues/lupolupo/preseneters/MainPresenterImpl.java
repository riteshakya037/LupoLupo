package com.blues.lupolupo.preseneters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

    public void fragmentTransaction(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) mMainView.getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slideinleft, R.anim.slideoutright)
                .replace(mMainView.getMainLayoutId(), fragment, fragment.getClass().getSimpleName())
                .commit();
        //close navigation menu if fragment change
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != R.id.nav_new_release)
            mMainView.closeDrawerLayout();

        return true;
    }

}
