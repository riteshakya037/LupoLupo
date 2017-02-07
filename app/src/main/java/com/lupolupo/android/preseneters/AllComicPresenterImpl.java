package com.lupolupo.android.preseneters;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;

import com.lupolupo.android.R;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.preseneters.mappers.AllComicMapper;
import com.lupolupo.android.views.AllComicView;
import com.lupolupo.android.views.adaptors.ComicEpisodeAdapter;

import java.util.Arrays;

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
    public void initializeMenuItem() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mView.getActivity(),
                R.layout.toolbar_spinner_item_actionbar, Arrays.asList(mView.getActivity().getResources().getStringArray(R.array.spinner_list_item_array)));
        dataAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        mView.setAdapter(dataAdapter);
        SpinnerInteractionListener listener = new SpinnerInteractionListener(mView.getActivity());
        mView.setListeners(listener);
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
