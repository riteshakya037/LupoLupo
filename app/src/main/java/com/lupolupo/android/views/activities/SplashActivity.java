package com.lupolupo.android.views.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.lupolupo.android.BuildConfig;
import com.lupolupo.android.common.FCMPref;
import com.lupolupo.android.common.FirstRunPrefPref;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.UserInfo;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.model.loaders.EpisodeLoader;
import com.lupolupo.android.views.activities.bases.PortraitActivity;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

import static com.lupolupo.android.views.activities.ComicActivity.INTENT_COMIC;
import static com.lupolupo.android.views.activities.EpisodeActivity.INTENT_EPISODE;

public class SplashActivity extends PortraitActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(FCMPref.REGISTRATION_COMPLETE)) {
                    requestPermission();
                    FirstRunPrefPref.with(getApplicationContext()).save(false);
                }
            }
        };
        checkPlayServices();
        if (getIntent().hasExtra(INTENT_COMIC)) {
            ComicLoader.getInstance().startLoading((Comic) getIntent().getParcelableExtra(INTENT_COMIC)).onSuccess(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, ComicActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else if (getIntent().hasExtra(INTENT_EPISODE)) {
            EpisodeLoader.getInstance().startLoading((Episode) getIntent().getParcelableExtra(INTENT_EPISODE)).onSuccess(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, EpisodeActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else if (!FirstRunPrefPref.with(getApplicationContext()).getBoolean()) {
            requestPermission();
        }
    }

    private void requestPermission() {
        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        } else {
            startMain();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startMain();
                } else {
                    Toast.makeText(SplashActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void startMain() {
        AppLoader.getInstance().startLoading().continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(Task<Void> task) throws Exception {
                return saveInfo();
            }
        }).onSuccess(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return null;
            }
        });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    private Task<Void> saveInfo() {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        UserInfo info = new UserInfo();
        if (BuildConfig.FLAVOR.equals("staging")) {
            tcs.setResult(null);
        } else {
            info.getInfo().onSuccess(new Continuation<UserInfo, Object>() {
                @Override
                public Object then(Task<UserInfo> task) throws Exception {
                    LupolupoHTTPManager.getInstance().saveInfo(task.getResult()).continueWith(new Continuation<String, Void>() {
                        @Override
                        public Void then(Task<String> task) throws Exception {
                            Log.i(TAG, "saveInfo: " + task.getResult());
                            tcs.setResult(null);
                            return null;
                        }
                    });
                    return null;
                }
            });
        }
        return tcs.getTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FCMPref.REGISTRATION_COMPLETE));


    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}