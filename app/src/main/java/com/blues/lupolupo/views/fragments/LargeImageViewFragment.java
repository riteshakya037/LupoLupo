package com.blues.lupolupo.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.views.activities.ComicActivity;
import com.blues.lupolupo.views.activities.SplashActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Ritesh Shakya
 */
public class LargeImageViewFragment extends Fragment {
    private static final String KEY_COMIC_BUNDLE = "key_comic";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.imageView)
    ImageView imageView;

    private Comic comicData;

    public static Fragment newInstance(Comic comic) {
        Fragment fragment = new LargeImageViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_COMIC_BUNDLE, comic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicData = getArguments().getParcelable(KEY_COMIC_BUNDLE);
    }

    @OnClick(R.id.imageView)
    void comicDetails() {
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.putExtra(ComicActivity.INTENT_COMIC, comicData);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_large_image, container, false);
        ButterKnife.bind(this, rootView);
        Glide.with(LupolupoAPIApplication.get())
                .load("http://lupolupo.com/images/" + comicData.id + "/" + comicData.comic_big_image)
                .crossFade()
                .into(imageView);
        return rootView;
    }
}
