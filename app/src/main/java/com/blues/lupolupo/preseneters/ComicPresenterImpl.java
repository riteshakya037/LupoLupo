package com.blues.lupolupo.preseneters;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.preseneters.mappers.ComicMapper;
import com.blues.lupolupo.views.ComicView;
import com.blues.lupolupo.views.adaptors.ComicEpisodeAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

import static com.blues.lupolupo.views.activities.ComicActivity.INTENT_COMIC;

/**
 * @author Ritesh Shakya
 */
public class ComicPresenterImpl implements ComicPresenter {
    private ComicView mView;
    private ComicMapper mMapper;
    private Comic comicData;
    private ComicEpisodeAdapter comicEpisodeAdaptor;

    public ComicPresenterImpl(ComicView comicView, ComicMapper comicMapper) {
        mView = comicView;
        mMapper = comicMapper;
    }

    @Override
    public void initializeAdaptor() {
        comicEpisodeAdaptor = new ComicEpisodeAdapter(mView.getActivity());
        mMapper.registerAdapter(comicEpisodeAdaptor);
    }

    @Override
    public void initializeData() {
        if (mView.getActivity().getIntent() != null) {
            comicData = mView.getActivity().getIntent().getParcelableExtra(INTENT_COMIC);
        }
        mMapper.setHeader(comicData);
        LupolupoHTTPManager.getInstance().getEpisodes(comicData.id).onSuccess(new Continuation<List<Episode>, Void>() {
            @Override
            public Void then(Task<List<Episode>> task) throws Exception {
                if (task.getResult() != null && task.getResult().size() != 0) {
                    mView.hideEmptyRelativeLayout();
                    comicEpisodeAdaptor.setData(task.getResult());
                }
                return null;
            }
        });

    }

    @Override
    public void loadImage(String url) {
        Glide.with(LupolupoAPIApplication.get())
                .load(url)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mView.setCoverImageLoading(View.GONE);
                        return false;
                    }
                })
                .into(mView.getCoverImageHolder());
    }

    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mView.initializeRecyclerLayoutManager(new LinearLayoutManager(mView.getActivity()));
    }
}
