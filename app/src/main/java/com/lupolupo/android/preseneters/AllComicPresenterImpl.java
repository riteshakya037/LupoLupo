package com.lupolupo.android.preseneters;

import android.support.v7.widget.LinearLayoutManager;

import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.preseneters.mappers.AllComicMapper;
import com.lupolupo.android.views.AllComicView;
import com.lupolupo.android.views.adaptors.ComicEpisodeAdapter;

/**
 * @author Ritesh Shakya
 */
public class AllComicPresenterImpl implements AllComicPresenter {
    private final AllComicView mView;
    private final AllComicMapper mMapper;
    private ComicEpisodeAdapter comicEpisodeAdaptor;

    public AllComicPresenterImpl(AllComicView mView, AllComicMapper mMapper) {
        this.mView = mView;
        this.mMapper = mMapper;
    }

    @Override
    public void initializeAdaptor() {
        comicEpisodeAdaptor = new ComicEpisodeAdapter(mView.getActivity());
        mMapper.registerAdapter(comicEpisodeAdaptor);

    }

    @Override
    public void initializeData() {
        if (ComicLoader.getInstance().getEpisodeList().size() == 0) {
            mView.showEmptyDialog();
        } else {
            comicEpisodeAdaptor.setData(ComicLoader.getInstance().getEpisodeList());
        }
    }

    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mView.initializeRecyclerLayoutManager(new LinearLayoutManager(mView.getActivity()));
    }
}
