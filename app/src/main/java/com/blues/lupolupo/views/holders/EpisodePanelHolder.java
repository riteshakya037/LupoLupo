package com.blues.lupolupo.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */
public class EpisodePanelHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panel_image)
    ImageView panelImage;

    public EpisodePanelHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void loadPanelImage(String url) {
        Glide.with(LupolupoAPIApplication.get())
                .load(url)
                .crossFade()
                .into(panelImage);
    }
}
