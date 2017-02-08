package com.lupolupo.android.views;

import android.graphics.drawable.Drawable;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseEmptyDialogView;
import com.lupolupo.android.views.bases.BaseRecyclerView;
import com.lupolupo.android.views.bases.BaseSpinnerView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface EpisodeView extends BaseContextView, BaseToolbarView, BaseRecyclerView, BaseEmptyDialogView, BaseSpinnerView {
    void toggleSubButton(boolean isEnabled);

    void setLikeDrawable(Drawable drawable);
}
