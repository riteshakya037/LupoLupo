package com.blues.lupolupo.views.holders;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LikePref;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.loaders.GlideLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.txt_date)
    public TextView episodeDate;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.button_like)
    public ImageButton button_like;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_episode_id)
    public TextView txtEpisodeId;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_like_count)
    public TextView txtEpisodeLikeCount;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.episodeImage)
    ImageView episodeImage;
    private boolean liked = false;
    private String episodeId;

    public EpisodeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.button_like)
    void onLikeDislike() {
        liked = !liked;
        setLikeDrawable();
        increaseCount();
        LupolupoHTTPManager.getInstance().postLikeUnlike(episodeId, liked);
    }

    public void setEpisodeImage(String url) {
        GlideLoader.load(url, episodeImage);
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
        liked = LikePref.with(LupolupoAPIApplication.get()).getBoolean(episodeId, false);
        setLikeDrawable();
    }

    private void increaseCount() {
        int count = Integer.valueOf(txtEpisodeLikeCount.getText().toString());
        if (liked) {
            txtEpisodeLikeCount.setText(String.valueOf(++count));
        } else {
            txtEpisodeLikeCount.setText(String.valueOf(--count));
        }
    }


    private void setLikeDrawable() {
        if (liked) {
            button_like.setImageDrawable(ContextCompat.getDrawable(LupolupoAPIApplication.get(), R.drawable.ic_heart_fill));
        } else {
            button_like.setImageDrawable(ContextCompat.getDrawable(LupolupoAPIApplication.get(), R.drawable.ic_heart_empty));
        }
    }
}
