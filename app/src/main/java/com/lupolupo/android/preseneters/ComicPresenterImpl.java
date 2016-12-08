package com.lupolupo.android.preseneters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.preseneters.mappers.ComicMapper;
import com.lupolupo.android.views.ComicView;
import com.lupolupo.android.views.adaptors.ComicEpisodeAdapter;
import com.lupolupo.android.views.bases.BaseEmptyRelativeLayoutView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Ritesh Shakya
 */
public class ComicPresenterImpl implements ComicPresenter, BaseEmptyRelativeLayoutView {
    private static final String CHILD = "images";
    private static final String IMAGE_NAME = "image.png";
    private final ComicView mView;
    private final ComicMapper mMapper;
    private Comic comicData;
    private ComicEpisodeAdapter comicEpisodeAdaptor;

    public ComicPresenterImpl(ComicView comicView, ComicMapper comicMapper) {
        mView = comicView;
        mMapper = comicMapper;
    }

    @Override
    public void initializeAdaptor() {
        comicEpisodeAdaptor = new ComicEpisodeAdapter(mView.getActivity(), this);
        mMapper.registerAdapter(comicEpisodeAdaptor);
    }

    @Override
    public void initializeData() {
        comicData = ComicLoader.getInstance().getComicData();
        mMapper.setHeader(comicData);
        comicEpisodeAdaptor.setData(ComicLoader.getInstance().getEpisodeList());
    }

    @Override
    public void loadImage(String url) {
        Glide.with(LupolupoAPIApplication.get())
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mView.getCoverImageHolder().setImageBitmap(resource);
                        saveImage(resource);
                    }
                });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void saveImage(Bitmap bitmap) {
        try {
            File cachePath = new File(mView.getActivity().getCacheDir(), CHILD);
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/" + IMAGE_NAME);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void share() {
        File imagePath = new File(mView.getActivity().getCacheDir(), CHILD);
        File newFile = new File(imagePath, IMAGE_NAME);
        Uri contentUri = FileProvider.getUriForFile(mView.getActivity(), "com.blues.lupolupo.file_provider", newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, comicData.comic_name);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType(mView.getActivity().getContentResolver().getType(contentUri));
            mView.getActivity().startActivity(Intent.createChooser(shareIntent, mView.getActivity().getResources().getText(R.string.send_to)));
        }
    }


    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mView.initializeRecyclerLayoutManager(new LinearLayoutManager(mView.getActivity()));
    }

    @Override
    public void hideEmptyRelativeLayout() {
        mView.hideEmptyRelativeLayout();
    }

    @Override
    public void showEmptyRelativeLayout() {
        mView.showEmptyRelativeLayout();
    }
}
