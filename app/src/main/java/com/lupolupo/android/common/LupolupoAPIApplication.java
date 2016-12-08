package com.lupolupo.android.common;

import android.app.Application;

/**
 * @author Ritesh Shakya
 */
public class LupolupoAPIApplication extends Application {
    private static LupolupoAPIApplication instance;

    public static LupolupoAPIApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LupolupoAPIApplication.instance = this;
    }
}
