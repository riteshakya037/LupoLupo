package com.lupolupo.android.views;

import android.graphics.drawable.Drawable;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseSpinnerView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface FlipActivityView extends BaseContextView, BaseToolbarView, BaseSpinnerView {
    void toggleSubButton(boolean isEnabled);

    void setLikeDrawable(Drawable drawable);
}
