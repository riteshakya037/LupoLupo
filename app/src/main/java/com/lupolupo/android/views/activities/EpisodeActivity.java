package com.lupolupo.android.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.common.DialogUtils;
import com.lupolupo.android.preseneters.EpisodePresenter;
import com.lupolupo.android.preseneters.EpisodePresenterImpl;
import com.lupolupo.android.preseneters.mappers.EpisodeMapper;
import com.lupolupo.android.views.EpisodeView;
import com.lupolupo.android.views.activities.bases.PortraitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeActivity extends PortraitActivity implements EpisodeView, EpisodeMapper {
    public static final String INTENT_EPISODE = "episode_intent";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panelPager)
    RecyclerView mRecyclerView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @SuppressWarnings("FieldCanBeLocal")
    private EpisodePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        ButterKnife.bind(this);
        mPresenter = new EpisodePresenterImpl(this, this);
        mPresenter.initializeEpisode();
        mPresenter.initializeViews();
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
    public void setTitle(String episode_name) {
        toolbarTitle.setText(episode_name);
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
    }

    @Override
    public void showEmptyDialog() {
        DialogUtils.showDialog(this, "Information", "There are no panels");
    }
}
