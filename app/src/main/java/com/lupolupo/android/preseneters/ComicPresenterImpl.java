package com.lupolupo.android.preseneters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.model.loaders.ImageLoader;
import com.lupolupo.android.preseneters.mappers.ComicMapper;
import com.lupolupo.android.views.ComicView;
import com.lupolupo.android.views.adaptors.ComicEpisodeAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class ComicPresenterImpl implements ComicPresenter {
    private static final String CHILD = "images";
    private static final String IMAGE_NAME = "image.png";
    private static final String TAG = ComicPresenterImpl.class.getSimpleName();
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
        comicEpisodeAdaptor = new ComicEpisodeAdapter(mView.getActivity());
        mMapper.registerAdapter(comicEpisodeAdaptor);
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
    public void initializeData() {
        comicData = ComicLoader.getInstance().getComicData();
        mMapper.setHeader(comicData);
        if (ComicLoader.getInstance().getEpisodeListSorted().size() == 0) {
            mView.showEmptyDialog();
        } else {
            comicEpisodeAdaptor.setData(ComicLoader.getInstance().getEpisodeListSorted());
        }

    }

    @Override
    public void loadImage(String url) {
        ImageLoader.load(url, mView.getCoverImageHolder());
        Glide.with(LupolupoAPIApplication.get())
                .load("http://lupolupo.com/" + url)
                .asBitmap()
                .placeholder(R.drawable.background_empty)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
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
        Uri contentUri = FileProvider.getUriForFile(mView.getActivity(), "com.lupolupo.android.file_provider", newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is too Funny. Check out this app - LupoLupo!");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType(mView.getActivity().getContentResolver().getType(contentUri));
            mView.getActivity().startActivity(Intent.createChooser(shareIntent, mView.getActivity().getResources().getText(R.string.send_to)));
        }
    }

    @Override
    public void subscribe() {
        LupolupoHTTPManager.getInstance().subscribe(comicData.id).onSuccess(new Continuation<String, Task<Void>>() {
            @Override
            public Task<Void> then(Task<String> results) throws Exception {
                mView.toggleSubButton(true);
                Toast.makeText(mView.getActivity(), results.getResult(), Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }


    @Override
    public void initializeViews() {
        mView.initializeToolbar();
        mView.initializeRecyclerLayoutManager(new LinearLayoutManager(mView.getActivity()));
    }

}
