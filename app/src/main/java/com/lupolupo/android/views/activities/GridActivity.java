package com.lupolupo.android.views.activities;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.GridActivityPresenter;
import com.lupolupo.android.preseneters.GridActivityPresenterImpl;
import com.lupolupo.android.preseneters.SpinnerInteractionListener;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.preseneters.mappers.GridMapper;
import com.lupolupo.android.views.GridView;
import com.lupolupo.android.views.activities.bases.PortraitActivity;
import com.lupolupo.android.views.custom.NDSpinner;
import com.lupolupo.android.views.fragments.DashFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridActivity extends PortraitActivity implements GridView, GridMapper, ViewPager.OnPageChangeListener {

    public static final String INTENT_GRID = "flip_intent";
    private static final String TAG = DashFragment.class.getSimpleName();
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverPager)
    ViewPager mViewPager;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tabDots)
    TabLayout mTabLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.dashViewRecycler)
    RecyclerView dashViewRecycler;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverPagerHolder)
    ViewGroup coverPagerHolder;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.no_feature_text)
    TextView noFeatureText;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView titleText;

    @BindView(R.id.mode_spinner)
    NDSpinner mSpinner;

    private GridActivityPresenter mPresenter;

    private int currentPage = 0;
    private Handler handler;
    private int delay = 5000;

    Runnable runnable = new Runnable() {
        public void run() {
            if (mViewPager.getAdapter() != null) {
                if (mViewPager.getAdapter().getCount() == currentPage) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                mViewPager.setCurrentItem(currentPage);
            }
            handler.postDelayed(this, delay);
        }
    };

    public GridActivity() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        mPresenter = new GridActivityPresenterImpl(this, this);
        mPresenter.initializeViews();
        mPresenter.initializeAdaptor();
        mPresenter.initializeMenuItem();
        mPresenter.initializeData();
        handler = new Handler();
    }

    @Override
    public void registerAdapter(RecyclerView.Adapter<?> adapter) {
        if (dashViewRecycler != null) {
            dashViewRecycler.setAdapter(adapter);
        }
    }

    @Override
    public void initializeRecyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (dashViewRecycler != null) {
            dashViewRecycler.setLayoutManager(layoutManager);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void initializeToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void registerAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void setCurrentPage(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void toggleCoverPagerLayout(boolean isVisible) {
        coverPagerHolder.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        noFeatureText.setVisibility(!isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initializeBasePageView() {
        if (mTabLayout != null && mViewPager != null) {
            mTabLayout.setupWithViewPager(mViewPager, true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setPage(position);
        currentPage = position;
        // Reset delay
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setAdapter(ArrayAdapter<String> dataAdapter) {
        if (mSpinner != null) {
            mSpinner.setAdapter(dataAdapter);
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleEvent(TitleEvent event) {
        titleText.setText(event.getTitleText());
    }

    @Override
    public void setListeners(SpinnerInteractionListener onItemSelectedListener) {
        if (mSpinner != null) {
            mSpinner.setOnTouchListener(onItemSelectedListener);
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
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
