package com.lupolupo.android.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("unused")
public class LikePref {

    private static final String TAG = "LoginPrefs";

    private static LikePref singleton = null;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    private LikePref(Context context) {
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static LikePref with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context).build();
        }
        return singleton;
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }


    @SuppressWarnings("SameParameterValue")
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public void removeAll() {
        editor.clear();
        editor.apply();
    }

    private static class Builder {

        private final Context context;

        Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        LikePref build() {
            return new LikePref(context);
        }
    }
}