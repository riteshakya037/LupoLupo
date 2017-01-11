package com.lupolupo.android.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.common.NotificationPref;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.MainPresenter;
import com.lupolupo.android.preseneters.MainPresenterImpl;
import com.lupolupo.android.preseneters.events.FragmentEvent;
import com.lupolupo.android.preseneters.events.SpinnerVisibilityEvent;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.views.MainView;
import com.lupolupo.android.views.activities.bases.PortraitActivity;
import com.lupolupo.android.views.fragments.ContactUsFragment;
import com.lupolupo.android.views.fragments.DashFragment;
import com.lupolupo.android.views.fragments.PrivacyPolicyFragment;
import com.lupolupo.android.views.fragments.TermOfUseFragment;
import com.lupolupo.android.views.fragments.bases.BackBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends PortraitActivity implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();
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
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.container_frame)
    View containerFrame;

    @BindView(R.id.mode_spinner)
    Spinner mSpinner;

    private MainPresenter mMainPresenter;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter.initializeViews();
        mMainPresenter.initializeMenuItem();
        if (savedInstanceState == null) {
            currentFragment = DashFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(getMainLayoutId(), currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof ContactUsFragment || currentFragment instanceof PrivacyPolicyFragment || currentFragment instanceof TermOfUseFragment) {
            ((BackBaseFragment) currentFragment).onBackPressed();
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
            Menu menu = navigationView.getMenu();
            LinearLayout linearLayout = (LinearLayout) menu.findItem(R.id.nav_new_release).getActionView();
            final SwitchCompat switchView = (SwitchCompat) linearLayout.findViewById(R.id.notification_switch);
            switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    NotificationPref.with(LupolupoAPIApplication.get()).save(b);
                }
            });
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
    public void setAdapter(ArrayAdapter<String> dataAdapter) {
        if (mSpinner != null) {
            mSpinner.setAdapter(dataAdapter);
            mSpinner.setSelection(AppLoader.getInstance().getMode().getPosition());
        }
    }

    @Override
    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        if (mSpinner != null) {
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
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

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleEvent(TitleEvent event) {
        titleText.setText(event.getTitleText());
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleEvent(SpinnerVisibilityEvent event) {
        mSpinner.setVisibility(event.isVisible() ? View.VISIBLE : View.GONE);
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

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentTransaction(FragmentEvent fragmentEvent) {
        currentFragment = fragmentEvent.getFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(fragmentEvent.isGoBack() ? R.anim.slideinleft : R.anim.slideinright, fragmentEvent.isGoBack() ? R.anim.slideoutright : R.anim.slideoutleft)
                .replace(getMainLayoutId(), fragmentEvent.getFragment(), fragmentEvent.getFragment().getClass().getSimpleName())
                .commit();
    }
}
