package com.lupolupo.android.preseneters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.lupolupo.android.R;
import com.lupolupo.android.views.MainView;
import com.lupolupo.android.views.fragments.DashFragment;
import com.lupolupo.android.views.fragments.OpenSourceFragment;
import com.lupolupo.android.views.fragments.TermOfUseFragment;
import com.lupolupo.android.views.fragments.VersionFragment;

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
        switch (id) {
            case R.id.nav_home:
                fragmentTransaction(DashFragment.newInstance());
                break;
            case R.id.nav_term_of_use:
                fragmentTransaction(TermOfUseFragment.newInstance());
                break;
            case R.id.nav_open_source:
                fragmentTransaction(OpenSourceFragment.newInstance());
                break;
            case R.id.nav_app_version:
                fragmentTransaction(VersionFragment.newInstance());
                break;
            case R.id.nav_new_release:
                break;
            default:
                fragmentTransaction(DashFragment.newInstance());
        }
        return true;
    }

}
