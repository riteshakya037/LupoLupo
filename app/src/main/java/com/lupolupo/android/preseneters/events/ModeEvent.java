package com.lupolupo.android.preseneters.events;

import com.lupolupo.android.model.enums.AppMode;

/**
 * @author Ritesh Shakya
 */
public class ModeEvent {
    private AppMode appMode;

    public ModeEvent(AppMode appMode) {
        this.appMode = appMode;
    }

    public AppMode getAppMode() {
        return appMode;
    }
}
