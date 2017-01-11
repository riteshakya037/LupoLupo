package com.lupolupo.android.views.fragments.bases;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.lupolupo.android.preseneters.events.SpinnerVisibilityEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Ritesh Shakya
 */
public class BaseFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().post(new SpinnerVisibilityEvent(false));
    }
}
