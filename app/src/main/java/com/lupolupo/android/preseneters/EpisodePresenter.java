package com.lupolupo.android.preseneters;

import com.lupolupo.android.model.Panel;
import com.lupolupo.android.preseneters.bases.BaseAdapterPresenter;
import com.lupolupo.android.preseneters.bases.BaseDataPresenter;
import com.lupolupo.android.preseneters.bases.BaseMenuPresenter;
import com.lupolupo.android.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface EpisodePresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter, BaseMenuPresenter {

    void share(Panel panel);

    void subscribe();

    void initializeEpisode();
}
