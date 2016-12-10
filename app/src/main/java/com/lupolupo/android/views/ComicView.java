package com.lupolupo.android.views;

import android.widget.ImageView;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseEmptyDialogView;
import com.lupolupo.android.views.bases.BaseEmptyRelativeLayoutView;
import com.lupolupo.android.views.bases.BaseRecyclerView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface ComicView extends BaseContextView, BaseRecyclerView, BaseToolbarView ,BaseEmptyDialogView {

    ImageView getCoverImageHolder();

    void toggleSubButton(boolean isEnabled);
}
