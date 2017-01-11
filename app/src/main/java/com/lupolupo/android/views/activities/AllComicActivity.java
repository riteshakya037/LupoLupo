package com.lupolupo.android.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.common.DialogUtils;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.preseneters.AllComicPresenter;
import com.lupolupo.android.preseneters.AllComicPresenterImpl;
import com.lupolupo.android.preseneters.mappers.AllComicMapper;
import com.lupolupo.android.views.AllComicView;
import com.lupolupo.android.views.activities.bases.PortraitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AllComicActivity extends PortraitActivity implements AllComicView, AllComicMapper {

    public static final String INTENT_ALL_EPISODE = "all_comic_intent";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.episodeRecycler)
    RecyclerView mRecycler;

    private AllComicPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comic);
        ButterKnife.bind(this);

        mPresenter = new AllComicPresenterImpl(this, this);
        mPresenter.initializeViews();
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            onBackPressed();
            return true;
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
            toolbarTitle.setText(AppLoader.getInstance().getMode().getDescription());
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
    public void showEmptyDialog() {
        DialogUtils.showDialog(this, "Information", "There are no episodes.");
    }
}
