package com.blues.lupolupo.views.bases;

import android.view.View;

/**
 * @author Ritesh Shakya
 */
public interface BaseFullScreenView {
    void registerClickListener(View.OnClickListener onClickListener);

    void registerTouchListener(View.OnTouchListener onTouchListener);

    void showControls();

    void hideControls();

    void showContent();

    void hideContent();
}
