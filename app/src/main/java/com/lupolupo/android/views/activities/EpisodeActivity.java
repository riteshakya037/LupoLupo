package com.lupolupo.android.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.common.DialogUtils;
import com.lupolupo.android.model.loaders.EpisodeLoader;
import com.lupolupo.android.preseneters.EpisodePresenter;
import com.lupolupo.android.preseneters.EpisodePresenterImpl;
import com.lupolupo.android.preseneters.SpinnerInteractionListener;
import com.lupolupo.android.preseneters.mappers.EpisodeMapper;
import com.lupolupo.android.views.EpisodeView;
import com.lupolupo.android.views.custom.NDSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class EpisodeActivity extends AppCompatActivity implements EpisodeView, EpisodeMapper {
    public static final String INTENT_EPISODE = "episode_intent";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panelPager)
    RecyclerView mRecyclerView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.button_subscribe)
    FancyButton buttonSubscribe;

    @SuppressWarnings("FieldCanBeLocal")
    private EpisodePresenter mPresenter;


    @OnClick(R.id.button_subscribe)
    void onSubscribe() {
        toggleSubButton(false);
        mPresenter.subscribe();
    }

    @OnClick(R.id.button_share)
    void onShare() {
        int visibleItemNo = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        mPresenter.share(EpisodeLoader.getInstance().getPanelList().get(visibleItemNo));
    }

    @BindView(R.id.mode_spinner)
    NDSpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        ButterKnife.bind(this);
        mPresenter = new EpisodePresenterImpl(this, this);
        mPresenter.initializeEpisode();
        mPresenter.initializeViews();
        mPresenter.initializeMenuItem();
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toggleSubButton(boolean isEnabled) {
        buttonSubscribe.setBackgroundColor(ContextCompat.getColor(this, isEnabled ? android.R.color.transparent : R.color.colorAccentLight));
        buttonSubscribe.setTextColor(ContextCompat.getColor(this, isEnabled ? R.color.colorAccentLight : R.color.cardview_light_background));
        buttonSubscribe.setEnabled(isEnabled);
    }

    @Override
    public void registerAdapter(RecyclerView.Adapter adapter) {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void initializeRecyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initializeToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showEmptyDialog() {
        DialogUtils.showDialog(this, "Information", "There are no panels");
    }

    @Override
    public void setAdapter(ArrayAdapter<String> dataAdapter) {
        if (mSpinner != null) {
            mSpinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void setListeners(SpinnerInteractionListener onItemSelectedListener) {
        if (mSpinner != null) {
            mSpinner.setOnTouchListener(onItemSelectedListener);
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }
}
