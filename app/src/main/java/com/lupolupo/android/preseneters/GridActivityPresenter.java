package com.lupolupo.android.preseneters;


import com.lupolupo.android.preseneters.bases.BaseAdapterPresenter;
import com.lupolupo.android.preseneters.bases.BaseDataPresenter;
import com.lupolupo.android.preseneters.bases.BaseMenuPresenter;
import com.lupolupo.android.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface GridActivityPresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter, BaseMenuPresenter {
    void setPage(int position);
}
