package com.lupolupo.android.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.GridActivityPresenter;
import com.lupolupo.android.preseneters.GridActivityPresenterImpl;
import com.lupolupo.android.preseneters.events.SpinnerVisibilityEvent;
import com.lupolupo.android.preseneters.mappers.GridMapper;
import com.lupolupo.android.views.GridView;
import com.lupolupo.android.views.custom.infinite_view_pager.InfinitePagerAdapter;
import com.lupolupo.android.views.custom.infinite_view_pager.ViewPagerCustomDuration;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashFragment extends Fragment implements GridView, GridMapper, ViewPager.OnPageChangeListener {
    private static final String TAG = DashFragment.class.getSimpleName();
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverPager)
    ViewPagerCustomDuration mViewPager;
    @BindView(R.id.coverInvisiblePager)
    ViewPager mInvisibleViewPager;
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
    @BindView(R.id.no_feature_text)
    TextView noFeatureText;

    private GridActivityPresenter dashPresenter;


    public DashFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashPresenter = new GridActivityPresenterImpl(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gridFrag = inflater.inflate(R.layout.fragment_grid, container, false);
        ButterKnife.bind(this, gridFrag);
        return gridFrag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dashPresenter.initializeViews();
        dashPresenter.initializeAdaptor();
        dashPresenter.initializeData();
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

    @Override
    public void registerAdapter(InfinitePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            mViewPager.setAutoScrollTime(5000);
            mViewPager.startAutoScroll();
            mViewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void registerInvisibleAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mInvisibleViewPager.setAdapter(adapter);
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
            mTabLayout.setupWithViewPager(mInvisibleViewPager, true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mViewPager != null)
            mViewPager.startAutoScroll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().post(new SpinnerVisibilityEvent(true));
    }

    @Override
    public void onStop() {
        if (mViewPager != null)
            mViewPager.stopAutoScroll();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        dashPresenter.setPage(mViewPager.getCurrentItem());
        mInvisibleViewPager.setCurrentItem(mViewPager.getCurrentItem());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
