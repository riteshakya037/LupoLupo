package com.lupolupo.android.model.enums;

import java.util.Arrays;

/**
 * @author Ritesh Shakya
 */
public enum AppMode {
    FLIP(0, "Flip"), GRID(1, "GRID"), RECENT(2, "Recent"), POPULAR(3, "Popular");

    private int position;
    private String description;

    AppMode(int position, String description) {
        this.position = position;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AppMode match(int position) {
        for (AppMode keywordType : Arrays.asList(AppMode.values())) {
            if (keywordType.getPosition() == position) {
                return keywordType;
            }
        }
        return AppMode.RECENT;
    }

    public int getPosition() {
        return position;
    }
}
