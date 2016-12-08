package com.blues.lupolupo.preseneters;

import android.support.v7.widget.GridLayoutManager;

import com.blues.lupolupo.model.loaders.AppLoader;
import com.blues.lupolupo.preseneters.events.TitleEvent;
import com.blues.lupolupo.preseneters.mappers.DashMapper;
import com.blues.lupolupo.views.DashView;
import com.blues.lupolupo.views.adaptors.DashAdapter;
import com.blues.lupolupo.views.adaptors.DashLargePagerAdapter;

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
            mDashPageAdapter.setData(AppLoader.getInstance().getComics());
            setPage(0);
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
