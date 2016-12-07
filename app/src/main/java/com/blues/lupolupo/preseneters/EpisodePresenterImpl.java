package com.blues.lupolupo.preseneters;

import android.support.v7.widget.LinearLayoutManager;

import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.model.Panel;
import com.blues.lupolupo.preseneters.mappers.EpisodeMapper;
import com.blues.lupolupo.views.EpisodeView;
import com.blues.lupolupo.views.adaptors.EpisodeAdapter;

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

    private EpisodeAdapter mPanelAdapter;
    private Episode episodeData;

    public EpisodePresenterImpl(EpisodeView episodeView, EpisodeMapper episodeMapper) {
        mView = episodeView;
        mMapper = episodeMapper;
    }

    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mView.initializeRecyclerLayoutManager(new LinearLayoutManager(mView.getActivity()));
        mMapper.setTitle(episodeData.episode_name);
    }

    @Override
    public void initializeEpisode() {
        if (mView.getActivity().getIntent() != null) {
            episodeData = mView.getActivity().getIntent().getParcelableExtra(INTENT_EPISODE);
        }
    }

    @Override
    public void initializeAdaptor() {
        mPanelAdapter = new EpisodeAdapter();
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
