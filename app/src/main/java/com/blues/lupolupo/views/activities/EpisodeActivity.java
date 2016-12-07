package com.blues.lupolupo.views.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    @BindView(R.id.fullscreen_content)
    ViewGroup mContentView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.fullscreen_content_controls)
    View mControlsView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panelPager)
    ViewPager mViewPager;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingView)
    RelativeLayout emptyLoadingView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.root_layout)
    ViewGroup root_layout;

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
        root_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPresenter.delayedHide(100);
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
    public void registerClickListener(View.OnClickListener onClickListener) {
        mContentView.setOnClickListener(onClickListener);
    }

    @Override
    public void registerTouchListener(View.OnTouchListener onTouchListener) {
        mContentView.setOnTouchListener(onTouchListener);
    }


    @Override
    public void showControls() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
        mControlsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideControls() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    @Override
    public void hideContent() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void registerAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
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
