package com.blues.lupolupo.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.preseneters.EpisodePresenter;
import com.blues.lupolupo.preseneters.EpisodePresenterImpl;
import com.blues.lupolupo.preseneters.mappers.EpisodeMapper;
import com.blues.lupolupo.views.EpisodeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeActivity extends AppCompatActivity implements EpisodeView, EpisodeMapper {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panelPager)
    RecyclerView mRecyclerView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingView)
    RelativeLayout emptyLoadingView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    public static final String INTENT_EPISODE = "episode_intent";

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
    public void hideEmptyRelativeLayout() {
        if (emptyLoadingView != null) {
            emptyLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyRelativeLayout() {
        if (emptyLoadingView != null) {
            emptyLoadingView.setVisibility(View.VISIBLE);
        }
    }
}
