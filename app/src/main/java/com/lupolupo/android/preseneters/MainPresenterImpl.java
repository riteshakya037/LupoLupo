package com.lupolupo.android.preseneters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.preseneters.events.ModeEvent;
import com.lupolupo.android.views.MainView;
import com.lupolupo.android.views.fragments.DashFragment;
import com.lupolupo.android.views.fragments.OpenSourceFragment;
import com.lupolupo.android.views.fragments.TermOfUseFragment;
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

    public void fragmentTransaction(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) mMainView.getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slideinleft, R.anim.slideoutright)
                .replace(mMainView.getMainLayoutId(), fragment, fragment.getClass().getSimpleName())
                .commit();
        //close navigation menu if fragment change
    }


    @Override
    public void initializeMenuItem() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mMainView.getActivity(),
                R.layout.toolbar_spinner_item_actionbar, Arrays.asList(mMainView.getActivity().getResources().getStringArray(R.array.spinner_list_item_array)));
        dataAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        mMainView.setAdapter(dataAdapter);
        mMainView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(new ModeEvent(AppMode.match(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                fragmentTransaction(DashFragment.newInstance());
                break;
            case R.id.nav_term_of_use:
                fragmentTransaction(TermOfUseFragment.newInstance());
                break;
            case R.id.nav_open_source:
                fragmentTransaction(OpenSourceFragment.newInstance());
                break;
            case R.id.nav_app_version:
                fragmentTransaction(VersionFragment.newInstance());
                break;
            case R.id.nav_new_release:
                break;
            default:
                fragmentTransaction(DashFragment.newInstance());
        }
        return true;
    }

}
