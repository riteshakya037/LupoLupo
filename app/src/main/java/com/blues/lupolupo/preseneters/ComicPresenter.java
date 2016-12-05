package com.blues.lupolupo.preseneters;

import com.blues.lupolupo.preseneters.bases.BaseAdapterPresenter;
import com.blues.lupolupo.preseneters.bases.BaseDataPresenter;
import com.blues.lupolupo.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface ComicPresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter {
    void loadImage(String url);
}
