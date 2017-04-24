package com.lupolupo.android.preseneters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.preseneters.mappers.GridMapper;
import com.lupolupo.android.views.GridView;
import com.lupolupo.android.views.adaptors.DashAdapter;
import com.lupolupo.android.views.adaptors.DashLargeInvisiblePagerAdapter;
import com.lupolupo.android.views.adaptors.DashLargePagerAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Ritesh Shakya
 */
public class GridActivityPresenterImpl implements GridActivityPresenter {
    private final GridView mView;
    private final GridMapper mMapper;
    private DashAdapter mDashAdapter;
    private DashLargePagerAdapter mDashPageAdapter;
    private DashLargeInvisiblePagerAdapter mInvisiblePageAdapter;

    public GridActivityPresenterImpl(GridView gridView, GridMapper gridMapper) {
        mView = gridView;
        mMapper = gridMapper;
    }

    @Override
    public void initializeViews() {
        mView.initializeRecyclerLayoutManager(new GridLayoutManager(mView.getActivity(), 2));
    }

    @Override
    public void initializeData() {
        if (AppLoader.getInstance().getComics().size() != 0) {
            mDashAdapter.setData(AppLoader.getInstance().getComics());
        }
        if (AppLoader.getInstance().getLargeComics().size() != 0) {
            mDashPageAdapter.setData(AppLoader.getInstance().getLargeComics());
            mInvisiblePageAdapter.setData(AppLoader.getInstance().getLargeComics());
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

        mDashPageAdapter = new DashLargePagerAdapter(mView.getActivity());
        mMapper.registerAdapter(mDashPageAdapter);
        mInvisiblePageAdapter = new DashLargeInvisiblePagerAdapter(((AppCompatActivity) mView.getActivity()).getSupportFragmentManager());
        mMapper.registerInvisibleAdapter(mInvisiblePageAdapter);
        mView.initializeBasePageView();
    }

    @Override
    public void setPage(int position) {
        EventBus.getDefault().post(new TitleEvent(mInvisiblePageAdapter.getData().get(position).comic_name));
    }

}
