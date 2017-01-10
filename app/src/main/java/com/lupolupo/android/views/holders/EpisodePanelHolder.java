package com.lupolupo.android.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lupolupo.android.R;
import com.lupolupo.android.model.loaders.GlideLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * @author Ritesh Shakya
 */
public class EpisodePanelHolder extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panel_image)
    PhotoView panelImage;

    public EpisodePanelHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void loadPanelImage(String url) {
        GlideLoader.load(url, panelImage);
    }
}
