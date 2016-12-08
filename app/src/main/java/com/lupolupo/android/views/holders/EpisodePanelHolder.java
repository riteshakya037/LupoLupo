package com.lupolupo.android.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lupolupo.android.R;
import com.lupolupo.android.model.loaders.GlideLoader;

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
        GlideLoader.load(url, panelImage);
    }
}
