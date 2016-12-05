package com.blues.lupolupo.views.fragments;


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
import android.widget.RelativeLayout;

import com.blues.lupolupo.R;
import com.blues.lupolupo.preseneters.DashPresenter;
import com.blues.lupolupo.preseneters.DashPresenterImpl;
import com.blues.lupolupo.preseneters.mappers.DashMapper;
import com.blues.lupolupo.views.DashView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashFragment extends Fragment implements DashView, DashMapper {
    private static final String TAG = DashFragment.class.getSimpleName();

    private DashPresenter dashPresenter;

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
    @BindView(R.id.emptyLoadingView)
    RelativeLayout emptyLoadingView;

    public DashFragment() {
        // Required empty public constructor
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
    public void hideEmptyRelativeLayout() {
        if (emptyLoadingView != null) {
            emptyLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyRelativeLayout() {
        if (emptyLoadingView != null) {
            emptyLoadingView.setVisibility(View.VISIBLE);
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

    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void registerAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
        }
    }

    @Override
    public void initializeBasePageView() {
        if (mTabLayout != null && mViewPager != null) {
            mTabLayout.setupWithViewPager(mViewPager, true);
        }
    }

}
