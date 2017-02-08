package com.lupolupo.android.preseneters;

import android.support.v7.app.AppCompatActivity;

import com.lupolupo.android.model.loaders.FlipLoader;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.preseneters.mappers.DashFragmentMapper;
import com.lupolupo.android.views.DashFragmentView;
import com.lupolupo.android.views.adaptors.FlipPagerAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Ritesh Shakya
 */
public class DashFragmentPresenterImpl implements DashFragmentPresenter {
    private final DashFragmentView mView;
    private final DashFragmentMapper mMapper;
    private FlipPagerAdapter mDashPageAdapter;

    public DashFragmentPresenterImpl(DashFragmentView mView, DashFragmentMapper mMapper) {
        this.mView = mView;
        this.mMapper = mMapper;
    }

    @Override
    public void initializeData() {
        if (FlipLoader.getInstance().getFlipMap().size() != 0) {
            mDashPageAdapter.setData(FlipLoader.getInstance().getFlipMap().keySet());
            mMapper.setCurrentPage(0);
            setPage(0);
        }
    }

    @Override
    public void initializeAdaptor() {
        mDashPageAdapter = new FlipPagerAdapter(((AppCompatActivity) mView.getActivity()).getSupportFragmentManager());
        mMapper.registerAdapter(mDashPageAdapter);
    }

    @Override
    public void setPage(int position) {
        EventBus.getDefault().post(new TitleEvent(mDashPageAdapter.getData().get(position).episode_name));
    }

}
