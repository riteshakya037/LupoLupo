package com.blues.lupolupo.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.preseneters.ComicPresenter;
import com.blues.lupolupo.preseneters.ComicPresenterImpl;
import com.blues.lupolupo.preseneters.mappers.ComicMapper;
import com.blues.lupolupo.views.ComicView;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ComicActivity extends AppCompatActivity implements ComicView, ComicMapper {

    public static final String INTENT_COMIC = "comic_intent";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverImage)
    ImageView coverImage;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_comic_title)
    TextView comicTitle;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingAnimator)
    AVLoadingIndicatorView emptyLoadingAnimator;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.episodeRecycler)
    RecyclerView mRecycler;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingView)
    View emptyLoadingView;

    private ComicPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        ButterKnife.bind(this);

        mPresenter = new ComicPresenterImpl(this, this);
        mPresenter.initializeViews();
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.activity_comic_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {
            mPresenter.share();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void initializeToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void registerAdapter(RecyclerView.Adapter<?> adapter) {
        if (mRecycler != null) {
            mRecycler.setAdapter(adapter);
        }
    }

    @Override
    public void initializeRecyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecycler != null) {
            mRecycler.setLayoutManager(layoutManager);
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

    @Override
    public void setCoverImageLoading(int visibility) {
        emptyLoadingAnimator.setVisibility(visibility);
    }

    @Override
    public ImageView getCoverImageHolder() {
        return coverImage;
    }

    @Override
    public void setHeader(Comic comicData) {
        mPresenter.loadImage("http://lupolupo.com/images/" + comicData.id + "/" + comicData.comic_big_image);
        comicTitle.setText(comicData.comic_name);
    }
}
