package com.lupolupo.android.views.fragments;


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

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.DashPresenter;
import com.lupolupo.android.preseneters.DashPresenterImpl;
import com.lupolupo.android.preseneters.mappers.DashMapper;
import com.lupolupo.android.views.DashView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashFragment extends Fragment implements DashView, DashMapper, ViewPager.OnPageChangeListener {
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
    private DashPresenter dashPresenter;


    public DashFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashPresenter = new DashPresenterImpl(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gridFrag = inflater.inflate(R.layout.fragment_dash, container, false);
        ButterKnife.bind(this, gridFrag);
        return gridFrag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dashPresenter.initializeViews();
        if (savedInstanceState == null) {
            dashPresenter.initializeAdaptor();
            dashPresenter.initializeData();
        }
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
    public void registerAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(this);
        }
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
        dashPresenter.setPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
