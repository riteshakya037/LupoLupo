package com.blues.lupolupo.preseneters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.model.Panel;
import com.blues.lupolupo.preseneters.mappers.EpisodeMapper;
import com.blues.lupolupo.views.EpisodeView;
import com.blues.lupolupo.views.adaptors.PanelPagerAdapter;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

import static com.blues.lupolupo.views.activities.EpisodeActivity.INTENT_EPISODE;

/**
 * @author Ritesh Shakya
 */
public class EpisodePresenterImpl implements EpisodePresenter {
    private final EpisodeView mView;
    private final EpisodeMapper mMapper;
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private boolean mVisible;
    private PanelPagerAdapter mPanelAdapter;
    private Episode episodeData;

    public EpisodePresenterImpl(EpisodeView episodeView, EpisodeMapper episodeMapper) {
        mView = episodeView;
        mMapper = episodeMapper;
        mVisible = true;
    }

    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mMapper.setTitle(episodeData.episode_name);
        mView.registerClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        mView.registerTouchListener(mDelayHideTouchListener);
    }

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mView.hideContent();
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            mView.showControls();
        }
    };

    @Override
    public void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void initializeEpisode() {
        if (mView.getActivity().getIntent() != null) {
            episodeData = mView.getActivity().getIntent().getParcelableExtra(INTENT_EPISODE);
        }
    }

    @Override
    public void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void hide() {
        // Hide UI first
        mView.hideControls();
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mView.showContent();
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }


    @Override
    public void initializeAdaptor() {
        mPanelAdapter = new PanelPagerAdapter(((AppCompatActivity) mView.getActivity()).getSupportFragmentManager());
        mMapper.registerAdapter(mPanelAdapter);
        mPanelAdapter.setComicId(episodeData.comic_id);
    }

    @Override
    public void initializeData() {
        LupolupoHTTPManager.getInstance().getPanel(episodeData.id).onSuccess(new Continuation<List<Panel>, Void>() {
            @Override
            public Void then(Task<List<Panel>> task) throws Exception {
                if (task.getResult() != null && task.getResult().size() != 0) {
                    mView.hideEmptyRelativeLayout();
                    mPanelAdapter.setData(task.getResult());
                }
                return null;
            }
        });
    }
}
