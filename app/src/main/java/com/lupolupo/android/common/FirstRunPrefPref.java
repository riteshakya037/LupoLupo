package com.lupolupo.android.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("unused")
public class FirstRunPrefPref {

    private static final String TAG = FirstRunPrefPref.class.getSimpleName();

    private static FirstRunPrefPref singleton = null;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor editor;
    private String key = "first_run";


    @SuppressLint("CommitPrefEdits")
    private FirstRunPrefPref(Context context) {
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static FirstRunPrefPref with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context).build();
        }
        return singleton;
    }

    public void save(boolean value) {
        Log.i(TAG, "save: " + value);
        editor.putBoolean(key, value).apply();
    }


    @SuppressWarnings("SameParameterValue")
    public boolean getBoolean() {
        return sharedPreferences.getBoolean(key, true);
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

        FirstRunPrefPref build() {
            return new FirstRunPrefPref(context);
        }
    }
}