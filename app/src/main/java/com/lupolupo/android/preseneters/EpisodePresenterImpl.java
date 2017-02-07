package com.lupolupo.android.preseneters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.Panel;
import com.lupolupo.android.model.loaders.EpisodeLoader;
import com.lupolupo.android.preseneters.mappers.EpisodeMapper;
import com.lupolupo.android.views.EpisodeView;
import com.lupolupo.android.views.adaptors.EpisodeAdapter;

import java.io.File;
import java.util.Arrays;

import bolts.Continuation;
import bolts.Task;

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
        if (EpisodeLoader.getInstance().getPanelList().size() == 0) {
            mView.showEmptyDialog();
        } else {
            mPanelAdapter.setData(EpisodeLoader.getInstance().getPanelList());
            EpisodeLoader.getInstance().getRemaining();
        }
    }

    @Override
    public void share(Panel panel) {
        File imgFile = new File(LupolupoAPIApplication.get().getCacheDir(), "images/" + episodeData.comic_id + "/" + panel.episode_id + "/" + panel.panel_image);
        Uri contentUri = FileProvider.getUriForFile(mView.getActivity(), "com.lupolupo.android.file_provider", imgFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This app is too Funny – LupoLupo. You should check it out!");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType(mView.getActivity().getContentResolver().getType(contentUri));
            mView.getActivity().startActivity(Intent.createChooser(shareIntent, mView.getActivity().getResources().getText(R.string.send_to)));
        }
    }

    @Override
    public void subscribe() {
        LupolupoHTTPManager.getInstance().subscribe(episodeData.comic_id).onSuccess(new Continuation<String, Task<Void>>() {
            @Override
            public Task<Void> then(Task<String> results) throws Exception {
                mView.toggleSubButton(true);
                Toast.makeText(mView.getActivity(), results.getResult(), Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }
}
