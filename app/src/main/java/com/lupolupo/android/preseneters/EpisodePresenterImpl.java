package com.lupolupo.android.preseneters;

import android.support.v7.widget.LinearLayoutManager;

import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.loaders.EpisodeLoader;
import com.lupolupo.android.preseneters.mappers.EpisodeMapper;
import com.lupolupo.android.views.EpisodeView;
import com.lupolupo.android.views.adaptors.EpisodeAdapter;

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
        episodeData = EpisodeLoader.getInstance().getEpisode();
    }

    @Override
    public void initializeAdaptor() {
        mPanelAdapter = new EpisodeAdapter();
        mMapper.registerAdapter(mPanelAdapter);
        mPanelAdapter.setComicId(episodeData.comic_id);
    }

    @Override
    public void initializeData() {
        mPanelAdapter.setData(EpisodeLoader.getInstance().getPanelList());
    }
}
