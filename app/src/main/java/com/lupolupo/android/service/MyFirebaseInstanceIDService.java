package com.lupolupo.android.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lupolupo.android.common.FCMPref;

/**
 * @author Ritesh Shakya
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        FCMPref.newInsance().storeToken(refreshedToken);
        Log.i(TAG, "onTokenRefresh: " + refreshedToken);

        Intent registrationComplete = new Intent(FCMPref.REGISTRATION_COMPLETE);
        registrationComplete.putExtra(FCMPref.INTENT_TOKEN, refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


}