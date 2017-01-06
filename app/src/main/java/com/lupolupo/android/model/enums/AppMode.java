package com.lupolupo.android.model.enums;

import java.util.Arrays;

/**
 * @author Ritesh Shakya
 */
public enum AppMode {
    RECENT(0), FEATURED(1), POPULAR(2);

    private int position;

    AppMode(int position) {
        this.position = position;
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
