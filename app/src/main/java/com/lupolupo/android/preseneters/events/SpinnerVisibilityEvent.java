package com.lupolupo.android.preseneters.events;

/**
 * @author Ritesh Shakya
 */
public class SpinnerVisibilityEvent {
    private boolean visible;

    public SpinnerVisibilityEvent(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
