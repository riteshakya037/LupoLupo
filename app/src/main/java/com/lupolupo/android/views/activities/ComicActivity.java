package com.lupolupo.android.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.common.DialogUtils;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.preseneters.ComicPresenter;
import com.lupolupo.android.preseneters.ComicPresenterImpl;
import com.lupolupo.android.preseneters.SpinnerInteractionListener;
import com.lupolupo.android.preseneters.mappers.ComicMapper;
import com.lupolupo.android.views.ComicView;
import com.lupolupo.android.views.activities.bases.PortraitActivity;
import com.lupolupo.android.views.custom.NDSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;


public class ComicActivity extends PortraitActivity implements ComicView, ComicMapper {

    public static final String INTENT_COMIC = "comic_intent";
    private static final String TAG = ComicActivity.class.getSimpleName();
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
    @BindView(R.id.episodeRecycler)
    RecyclerView mRecycler;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.button_subscribe)
    FancyButton buttonSubscribe;

    @OnClick(R.id.button_subscribe)
    void onSubscribe() {
        toggleSubButton(false);
        mPresenter.subscribe();
    }

    @OnClick(R.id.button_share)
    void onShare() {
        mPresenter.share();
    }

    @BindView(R.id.mode_spinner)
    NDSpinner mSpinner;

    private ComicPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        ButterKnife.bind(this);

        mPresenter = new ComicPresenterImpl(this, this);
        mPresenter.initializeViews();
        mPresenter.initializeMenuItem();
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
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
    public ImageView getCoverImageHolder() {
        return coverImage;
    }

    @Override
    public void toggleSubButton(boolean isEnabled) {
        buttonSubscribe.setBackgroundColor(ContextCompat.getColor(this, isEnabled ? android.R.color.transparent : R.color.colorAccentLight));
        buttonSubscribe.setTextColor(ContextCompat.getColor(this, isEnabled ? R.color.colorAccentLight : R.color.cardview_light_background));
        buttonSubscribe.setEnabled(isEnabled);
    }

    public int getThemeAccentColor() {
        final TypedValue value = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }

    @Override
    public void setHeader(Comic comicData) {
        mPresenter.loadImage("images/" + comicData.id + "/" + comicData.comic_big_image);
        comicTitle.setText(comicData.comic_name);
    }

    @Override
    public void showEmptyDialog() {
        DialogUtils.showDialog(this, "Information", "There are no episodes.");
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
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }
}
