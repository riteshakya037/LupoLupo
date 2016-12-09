package com.lupolupo.android.common;

import android.content.SharedPreferences;

/**
 * @author Ritesh Shakya
 */

public class FCMPref {


    public static final String SHARED_PREF = "ah_firebase";
    public static final String INTENT_TOKEN = "token";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private static FCMPref _instance;
    private final String TOKEN = "regId";

    public static FCMPref newInsance() {
        if (_instance == null) {
            _instance = new FCMPref();
        }
        return _instance;
    }

    public void storeToken(String refreshedToken) {
        SharedPreferences pref = LupolupoAPIApplication.get().getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN, refreshedToken);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences pref = LupolupoAPIApplication.get().getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(TOKEN, null);
    }
}
