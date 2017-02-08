package com.lupolupo.android.preseneters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.events.TitleEvent;
import com.lupolupo.android.preseneters.mappers.GridMapper;
import com.lupolupo.android.views.GridView;
import com.lupolupo.android.views.adaptors.DashAdapter;
import com.lupolupo.android.views.adaptors.DashLargePagerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

/**
 * @author Ritesh Shakya
 */
public class GridActivityPresenterImpl implements GridActivityPresenter {
    private final GridView mView;
    private final GridMapper mMapper;
    private DashAdapter mDashAdapter;
    private DashLargePagerAdapter mDashPageAdapter;

    public GridActivityPresenterImpl(GridView gridView, GridMapper gridMapper) {
        mView = gridView;
        mMapper = gridMapper;
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

        mDashPageAdapter = new DashLargePagerAdapter(((AppCompatActivity) mView.getActivity()).getSupportFragmentManager());
        mMapper.registerAdapter(mDashPageAdapter);
    }

    @Override
    public void setPage(int position) {
        EventBus.getDefault().post(new TitleEvent(mDashAdapter.getData().get(position).comic_name));
    }

    @Override
    public void initializeMenuItem() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mView.getActivity(),
                R.layout.toolbar_spinner_item_actionbar, Arrays.asList(mView.getActivity().getResources().getStringArray(R.array.spinner_list_item_array)));
        dataAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        mView.setAdapter(dataAdapter);
        SpinnerInteractionListener listener = new SpinnerInteractionListener(mView.getActivity());
        mView.setListeners(listener);
    }
}
