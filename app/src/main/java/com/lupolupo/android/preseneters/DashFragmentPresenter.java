package com.lupolupo.android.preseneters;

import com.lupolupo.android.preseneters.bases.BaseAdapterPresenter;
import com.lupolupo.android.preseneters.bases.BaseDataPresenter;

/**
 * @author Ritesh Shakya
 */
public interface DashFragmentPresenter extends BaseDataPresenter, BaseAdapterPresenter {
    void setPage(int position);
}
