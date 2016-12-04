package com.blues.lupolupo.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LupolupoAPIApplication;
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

public class DashViewSmallHolder extends RecyclerView.ViewHolder {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverImage)
    ImageView coverImage;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingAnimator)
    AVLoadingIndicatorView emptyLoadingAnimator;

    public DashViewSmallHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void loadImage(String url) {
        Glide.with(LupolupoAPIApplication.get())
                .load(url)
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
                .into(coverImage);
    }
}