package com.lupolupo.android.preseneters.events;

import android.support.v4.app.Fragment;

/**
 * @author Ritesh Shakya
 */
public class FragmentEvent {
    private Fragment fragment;
    private boolean goBack;

    public FragmentEvent(Fragment fragment, boolean goBack) {
        this.fragment = fragment;
        this.goBack = goBack;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isGoBack() {
        return goBack;
    }
}
