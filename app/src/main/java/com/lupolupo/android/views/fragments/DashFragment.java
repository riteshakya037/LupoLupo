package com.lupolupo.android.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.DashFragmentPresenter;
import com.lupolupo.android.preseneters.DashFragmentPresenterImpl;
import com.lupolupo.android.preseneters.mappers.DashFragmentMapper;
import com.lupolupo.android.views.DashFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashFragment extends Fragment implements DashFragmentView, DashFragmentMapper, ViewPager.OnPageChangeListener {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.container)
    ViewPager mViewPager;

    public DashFragment() {
        // Required empty public constructor
    }

    private DashFragmentPresenter mPresenter;


    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gridFrag = inflater.inflate(R.layout.fragment_flip, container, false);
        ButterKnife.bind(this, gridFrag);
        mPresenter = new DashFragmentPresenterImpl(this, this);
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
        return gridFrag;
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
