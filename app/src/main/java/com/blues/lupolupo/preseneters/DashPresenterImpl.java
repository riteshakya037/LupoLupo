package com.blues.lupolupo.preseneters;

import android.support.v7.widget.LinearLayoutManager;

import com.blues.lupolupo.preseneters.bases.DashPresenter;
import com.blues.lupolupo.preseneters.mappers.DashMapper;
import com.blues.lupolupo.views.DashView;

/**
 * @author Ritesh Shakya
 */
public class DashPresenterImpl implements DashPresenter {
    private DashView mDashView;
    private DashMapper mDashMapper;

    public DashPresenterImpl(DashView dashView, DashMapper dashMapper) {
        mDashView = dashView;
        mDashMapper = dashMapper;
    }

    @Override
    public void initializeViews() {
        mDashView.initializeRecyclerLayoutManager(new LinearLayoutManager(mDashView.getActivity()));
    }
}
