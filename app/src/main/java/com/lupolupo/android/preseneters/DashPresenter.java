package com.lupolupo.android.preseneters;

import com.lupolupo.android.preseneters.bases.BaseAdapterPresenter;
import com.lupolupo.android.preseneters.bases.BaseDataPresenter;
import com.lupolupo.android.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface DashPresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter {
    void setPage(int position);
}