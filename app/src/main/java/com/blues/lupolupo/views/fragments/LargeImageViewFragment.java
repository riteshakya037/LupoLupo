package com.blues.lupolupo.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.model.Comic;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */
public class LargeImageViewFragment extends Fragment {
    private static final String KEY_COMIC_BUNDLE = "key_comic";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.imageView)
    ImageView imageView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingAnimator)
    AVLoadingIndicatorView emptyLoadingAnimator;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_large_image, container, false);
        ButterKnife.bind(this, rootView);
        Glide.with(LupolupoAPIApplication.get())
                .load("http://lupolupo.com/images/" + comicData.id + "/" + comicData.comic_big_image)
                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        emptyLoadingAnimator.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
        return rootView;
    }
}
