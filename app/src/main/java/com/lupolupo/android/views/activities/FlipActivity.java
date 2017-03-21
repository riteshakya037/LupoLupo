package com.lupolupo.android.views.activities;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lupolupo.android.R;
import com.lupolupo.android.preseneters.FlipActivityPresenter;
import com.lupolupo.android.preseneters.FlipActivityPresenterImpl;
import com.lupolupo.android.preseneters.SpinnerInteractionListener;
import com.lupolupo.android.preseneters.mappers.FlipActivityMapper;
import com.lupolupo.android.views.FlipActivityView;
import com.lupolupo.android.views.custom.NDSpinner;
import com.lupolupo.android.views.fragments.DashFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class FlipActivity extends AppCompatActivity implements FlipActivityView, FlipActivityMapper, ViewPager.OnPageChangeListener {

    public static final String INTENT_GRID = "flip_intent";
    private static final String TAG = DashFragment.class.getSimpleName();
    public static final String LOAD_GUIDE = "load_guide";
    private Handler handler;
    private int delay = 2000;

    @BindView(R.id.mode_spinner)
    NDSpinner mSpinner;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.button_heart)
    ImageButton buttonLike;

    @BindView(R.id.swipe_helper)
    ImageView swipeHelper;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.button_subscribe)
    FancyButton buttonSubscribe;

    @OnClick(R.id.button_subscribe)
    void onSubscribe() {
        toggleSubButton(false);
        mPresenter.subscribe();
    }

    @OnClick(R.id.button_heart)
    void onLike() {
        mPresenter.onLike();
    }

    @OnClick(R.id.button_plus)
    void showComic() {
        mPresenter.showComic();
    }

    @OnClick(R.id.button_web)
    void onShowWeb() {
        mPresenter.showWeb();
    }

    @OnClick(R.id.button_share)
    void onShare() {
        mPresenter.share();
    }


    Runnable runnable = new Runnable() {
        public void run() {
            if (swipeHelper != null) {
                swipeHelper.setVisibility(View.GONE);
            }
        }
    };

    public FlipActivity() {
        // Required empty public constructor
    }

    private FlipActivityPresenter mPresenter;

    public static Fragment newInstance() {
        return new DashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        ButterKnife.bind(this);
        mPresenter = new FlipActivityPresenterImpl(this, this);
        mPresenter.initializeViews();
        mPresenter.initializeAdaptor();
        mPresenter.initializeData();
        mPresenter.initializeMenuItem();
        handler = new Handler();
        if (getIntent().hasExtra(LOAD_GUIDE) && savedInstanceState == null) {
            handler.postDelayed(runnable, delay);
            swipeHelper.setVisibility(View.VISIBLE);
        } else {
            swipeHelper.setVisibility(View.GONE);
        }
    }

    @Override
    public void registerAdapter(FragmentStatePagerAdapter adapter) {
        if (mViewPager != null) {
            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void setCurrentPage(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initializeToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPager.getCurrentItem() != 0)
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            });
        }
    }

    @Override
    public void setAdapter(ArrayAdapter<String> dataAdapter) {
        if (mSpinner != null) {
            mSpinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void toggleSubButton(boolean isEnabled) {
        buttonSubscribe.setBackgroundColor(ContextCompat.getColor(this, isEnabled ? android.R.color.transparent : R.color.colorAccentLight));
        buttonSubscribe.setTextColor(ContextCompat.getColor(this, isEnabled ? R.color.colorAccentLight : R.color.cardview_light_background));
        buttonSubscribe.setEnabled(isEnabled);
    }

    @Override
    public void setLikeDrawable(Drawable drawable) {
        buttonLike.setImageDrawable(drawable);
    }

    @Override
    public void setListeners(SpinnerInteractionListener onItemSelectedListener) {
        if (mSpinner != null) {
            mSpinner.setOnTouchListener(onItemSelectedListener);
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }
}
