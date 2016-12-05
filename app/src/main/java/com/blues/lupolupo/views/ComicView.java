package com.blues.lupolupo.views;

import android.widget.ImageView;

import com.blues.lupolupo.views.bases.BaseContextView;
import com.blues.lupolupo.views.bases.BaseEmptyRelativeLayoutView;
import com.blues.lupolupo.views.bases.BaseRecyclerView;
import com.blues.lupolupo.views.bases.BaseToolbarView;

/**
 * @author Ritesh Shakya
 */
public interface ComicView extends BaseContextView, BaseEmptyRelativeLayoutView, BaseRecyclerView, BaseToolbarView {

    void setCoverImageLoading(int visibility);

    ImageView getCoverImageHolder();
}
