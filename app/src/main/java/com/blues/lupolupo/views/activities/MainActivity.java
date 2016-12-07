package com.blues.lupolupo.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.preseneters.MainPresenter;
import com.blues.lupolupo.preseneters.MainPresenterImpl;
import com.blues.lupolupo.preseneters.events.TitleEvent;
import com.blues.lupolupo.views.MainView;
import com.blues.lupolupo.views.fragments.DashFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter mMainPresenter;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView titleText;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.container_frame)
    View containerFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter.initializeViews();
        if (savedInstanceState == null) {
            //  fragmentTransaction(TimelinePageFragment.newInstance(), timelineFragment);
            mMainPresenter.fragmentTransaction(DashFragment.newInstance());
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void initializeDrawerLayout() {
        if (mDrawerLayout != null) {
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    containerFrame.setTranslationX(slideOffset * drawerView.getWidth());
                    mDrawerLayout.bringChildToFront(drawerView);
                    mDrawerLayout.requestLayout();
                }
            };
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
            navigationView.setNavigationItemSelectedListener(mMainPresenter);
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void closeDrawerLayout() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(navigationView);
        }
    }

    @Override
    public int getMainLayoutId() {
        return R.id.content_main;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initializeToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleEvent(TitleEvent event) {
        titleText.setText(event.getTitleText());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
