package com.blues.lupolupo.preseneters;

import com.blues.lupolupo.preseneters.mappers.EpisodeMapper;
import com.blues.lupolupo.views.EpisodeView;

/**
 * @author Ritesh Shakya
 */
public class EpisodePresenterImpl implements ComicEpisodePresenter {
    private EpisodeView mView;
    private EpisodeMapper mMapper;

    public EpisodePresenterImpl(EpisodeView episodeView, EpisodeMapper episodeMapper) {
        mView = episodeView;
        mMapper = episodeMapper;
    }
}
