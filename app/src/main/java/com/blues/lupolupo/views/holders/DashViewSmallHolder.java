package com.blues.lupolupo.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.model.loaders.GlideLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */

public class DashViewSmallHolder extends RecyclerView.ViewHolder {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.coverImage)
    public ImageView coverImage;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_comic_title)
    public TextView comicTitle;

    public DashViewSmallHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void loadImage(String url) {
        GlideLoader.load(url, coverImage);
    }
}