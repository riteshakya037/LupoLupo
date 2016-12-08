package com.lupolupo.android.views;

import android.widget.ImageView;

import com.lupolupo.android.views.bases.BaseContextView;
import com.lupolupo.android.views.bases.BaseEmptyRelativeLayoutView;
import com.lupolupo.android.views.bases.BaseRecyclerView;
import com.lupolupo.android.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface ComicView extends BaseContextView, BaseEmptyRelativeLayoutView, BaseRecyclerView, BaseToolbarView {

    ImageView getCoverImageHolder();
}
