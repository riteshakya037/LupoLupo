package com.lupolupo.android.model.enums;

import java.util.Arrays;

/**
 * @author Ritesh Shakya
 */
public enum AppMode {
    RECENT(0, "Recent"), POPULAR(1, "Popular");

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
