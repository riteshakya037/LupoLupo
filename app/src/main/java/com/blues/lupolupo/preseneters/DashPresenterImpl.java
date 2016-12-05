package com.blues.lupolupo.preseneters;

import android.support.v7.widget.GridLayoutManager;

import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.preseneters.mappers.DashMapper;
import com.blues.lupolupo.views.DashView;
import com.blues.lupolupo.views.adaptors.DashAdapter;
import com.blues.lupolupo.views.adaptors.DashLargePagerAdapter;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

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
        LupolupoHTTPManager.getInstance().getComics().onSuccess(new Continuation<List<Comic>, Void>() {
            @Override
            public Void then(Task<List<Comic>> task) throws Exception {
                if (task.getResult() != null && task.getResult().size() != 0) {
                    mView.hideEmptyRelativeLayout();
                    mDashAdapter.setData(task.getResult());
                    mDashPageAdapter.setData(task.getResult());
                }
                return null;
            }
        });
    }

    @Override
    public void initializeAdaptor() {
        mDashAdapter = new DashAdapter();
        mMapper.registerAdapter(mDashAdapter);

        mDashPageAdapter = new DashLargePagerAdapter(mView.getFragmentManager());
        mMapper.registerAdapter(mDashPageAdapter);
    }
}
