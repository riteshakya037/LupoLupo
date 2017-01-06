package com.lupolupo.android.preseneters;

import android.support.v7.widget.GridLayoutManager;

import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.preseneters.mappers.DashMapper;
import com.lupolupo.android.views.DashView;
import com.lupolupo.android.views.adaptors.DashAdapter;
import com.lupolupo.android.views.adaptors.DashLargePagerAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Ritesh Shakya
 */
public class DashPresenterImpl implements DashPresenter {
    private final DashView mView;
    private final DashMapper mMapper;
    private DashAdapter mDashAdapter;
    private DashLargePagerAdapter mDashPageAdapter;

    public DashPresenterImpl(DashView dashView, DashMapper dashMapper) {
        mView = dashView;
        mMapper = dashMapper;
    }

    @Override
    public void initializeViews() {
        mView.initializeRecyclerLayoutManager(new GridLayoutManager(mView.getActivity(), 2));
        mView.initializeBasePageView();
    }

    @Override
    public void initializeData() {
        if (AppLoader.getInstance().getComics().size() != 0) {
            mDashAdapter.setData(AppLoader.getInstance().getComics());
        }
        if (AppLoader.getInstance().getLargeComics().size() != 0) {
            mDashPageAdapter.setData(AppLoader.getInstance().getLargeComics());
            mMapper.setCurrentPage(0);
            setPage(0);
            mView.toggleCoverPagerLayout(true);
        } else {
            mView.toggleCoverPagerLayout(false);
        }
    }

    @Override
    public void initializeAdaptor() {
        mDashAdapter = new DashAdapter(mView.getActivity());
        mMapper.registerAdapter(mDashAdapter);

        mDashPageAdapter = new DashLargePagerAdapter(mView.getFragmentManager());
        mMapper.registerAdapter(mDashPageAdapter);
    }

    @Override
    public void setPage(int position) {
        EventBus.getDefault().post(new TitleEvent(mDashAdapter.getData().get(position).comic_name));
    }
}
