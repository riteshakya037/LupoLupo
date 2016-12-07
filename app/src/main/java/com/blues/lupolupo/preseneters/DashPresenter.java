package com.blues.lupolupo.preseneters;

import com.blues.lupolupo.preseneters.bases.BaseAdapterPresenter;
import com.blues.lupolupo.preseneters.bases.BaseDataPresenter;
import com.blues.lupolupo.preseneters.bases.BasePresenter;

/**
 * @author Ritesh Shakya
 */
public interface DashPresenter extends BasePresenter, BaseDataPresenter, BaseAdapterPresenter {
    void setPage(int position);
}
