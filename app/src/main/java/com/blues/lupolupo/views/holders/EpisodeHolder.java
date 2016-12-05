package com.blues.lupolupo.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class EpisodeHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.root_layout)
    public RelativeLayout rootLayout;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_episode_title)
    public TextView episodeTitle;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_)// TODO: 12/4/2016
    public TextView txt_;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.button_like)
    public ImageButton button_like;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_episode_id)
    public TextView episodeId;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_like_count)
    public TextView episodeLikeCount;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.episodeImage)
    ImageView episodeImage;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.emptyLoadingAnimator)
    AVLoadingIndicatorView emptyLoadingAnimator;

    public EpisodeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setEpisodeImage(String url) {
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
                        emptyLoadingAnimator.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(episodeImage);
    }
}
