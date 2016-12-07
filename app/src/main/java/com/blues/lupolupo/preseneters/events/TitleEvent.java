package com.blues.lupolupo.preseneters.events;

/**
 * @author Ritesh Shakya
 */
public class TitleEvent {
    private String titleText;

    public TitleEvent(String titleText) {
        this.titleText = titleText;
    }

    public String getTitleText() {
        return titleText;
    }
}
