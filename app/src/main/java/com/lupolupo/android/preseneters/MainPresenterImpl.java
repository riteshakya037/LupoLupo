package com.lupolupo.android.preseneters;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.events.FragmentEvent;
import com.lupolupo.android.views.MainView;
import com.lupolupo.android.views.fragments.AboutUsFragment;
import com.lupolupo.android.views.fragments.DashFragment;
import com.lupolupo.android.views.fragments.VersionFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

/**
 * @author Ritesh Shakya
 */
public class MainPresenterImpl implements MainPresenter {
    private final MainView mMainView;


    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
    }

    @Override
    public void initializeViews() {
        mMainView.initializeToolbar();
        mMainView.initializeDrawerLayout();
    }


    @Override
    public void initializeMenuItem() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mMainView.getActivity(),
                R.layout.toolbar_spinner_item_actionbar, Arrays.asList(mMainView.getActivity().getResources().getStringArray(R.array.spinner_list_item_array)));
        dataAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        mMainView.setAdapter(dataAdapter);
        SpinnerInteractionListener listener = new SpinnerInteractionListener(mMainView.getActivity());
        mMainView.setListeners(listener);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mMainView.closeDrawerLayout();
        switch (id) {
            case R.id.nav_home:
                EventBus.getDefault().post(new FragmentEvent(DashFragment.newInstance(), false));
                break;
            case R.id.nav_about_us:
                EventBus.getDefault().post(new FragmentEvent(AboutUsFragment.newInstance(), false));
                break;
            case R.id.nav_app_version:
                EventBus.getDefault().post(new FragmentEvent(VersionFragment.newInstance(), false));
                break;
            default:
                EventBus.getDefault().post(new FragmentEvent(DashFragment.newInstance(), false));
        }
        return true;
    }

}
