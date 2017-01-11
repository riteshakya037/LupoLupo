package com.lupolupo.android.preseneters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.events.FragmentEvent;
import com.lupolupo.android.views.MainView;
import com.lupolupo.android.views.activities.AllComicActivity;
import com.lupolupo.android.views.activities.SplashActivity;
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
        SpinnerInteractionListener listener = new SpinnerInteractionListener();
        mMainView.setListeners(listener);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != R.id.nav_new_release)
            mMainView.closeDrawerLayout();
        switch (id) {
            case R.id.nav_home:
                EventBus.getDefault().post(new FragmentEvent(DashFragment.newInstance(), false));
                break;
            case R.id.nav_new_release:
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

    public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {
        boolean userSelect = false;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("selected");
            if (userSelect) {
                AppLoader.getInstance().setAppMode(AppMode.match(i));
                Intent intent = new Intent(mMainView.getActivity(), SplashActivity.class);
                intent.putExtra(AllComicActivity.INTENT_ALL_EPISODE, "");
                mMainView.getActivity().startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
