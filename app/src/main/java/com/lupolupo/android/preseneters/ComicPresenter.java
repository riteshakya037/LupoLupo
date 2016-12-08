package com.lupolupo.android.preseneters;

import com.lupolupo.android.preseneters.bases.BaseAdapterPresenter;
import com.lupolupo.android.preseneters.bases.BaseDataPresenter;
import com.lupolupo.android.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface ComicPresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter {
    void loadImage(String url);

    void share();
}
