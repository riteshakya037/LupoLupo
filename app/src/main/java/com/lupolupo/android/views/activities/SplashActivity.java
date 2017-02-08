package com.lupolupo.android.views.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Tasks;
import com.lupolupo.android.BuildConfig;
import com.lupolupo.android.R;
import com.lupolupo.android.common.FCMPref;
import com.lupolupo.android.common.FirstRunPrefPref;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.UserInfo;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.model.loaders.EpisodeLoader;
import com.lupolupo.android.model.loaders.FlipLoader;
import com.lupolupo.android.preseneters.events.DownloadProgressEvent;
import com.lupolupo.android.views.activities.bases.PortraitActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lupolupo.android.views.activities.AllComicActivity.INTENT_ALL_EPISODE;
import static com.lupolupo.android.views.activities.ComicActivity.INTENT_COMIC;
import static com.lupolupo.android.views.activities.EpisodeActivity.INTENT_EPISODE;
import static com.lupolupo.android.views.activities.GridActivity.INTENT_GRID;
import static com.lupolupo.android.views.activities.MainActivity.INTENT_MAIN;

public class SplashActivity extends PortraitActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    double bytesPerSecond = 0;

    @BindView(R.id.activity_splash_progress)
    RoundCornerProgressBar mProgressBar;
    private int fileBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

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
        } else if (getIntent().hasExtra(INTENT_ALL_EPISODE)) {
            ComicLoader.getInstance().startLoadingAll().onSuccess(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, AllComicActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else if (getIntent().hasExtra(INTENT_MAIN)) {
            FlipLoader.getInstance().startLoading().onSuccess(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else if (getIntent().hasExtra(INTENT_GRID)) {
            AppLoader.getInstance().startLoading().onSuccess(new Continuation<Void, Void>() {
                @Override
                public Void then(Task<Void> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, GridActivity.class);
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
        mProgressBar.setVisibility(View.VISIBLE);
        final long startTime = System.currentTimeMillis();
        fileBytes = 0;
        FlipLoader.getInstance().startLoading().continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(Task<Void> task) throws Exception {
                long endTime = System.currentTimeMillis();
                double downloadTimeSeconds = ((double) (endTime - startTime)) / 1000d;
                bytesPerSecond = ((double) fileBytes) / downloadTimeSeconds / 125;
                return checkGPS();
            }
        }).continueWithTask(new Continuation<Void, Task<Void>>() {
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

    TaskCompletionSource<Void> checkGpsTask;

    private Task<Void> checkGPS() {
        checkGpsTask = new TaskCompletionSource<>();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
        }

        if (!gps_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
            dialog.setMessage(getResources().getString(R.string.gps_not_enabled));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 100);
                }
            });
//            dialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                    checkGpsTask.setResult(null);
//                }
//            });
            dialog.show();
        } else {
            checkGpsTask.setResult(null);
        }
        return checkGpsTask.getTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (checkGpsTask != null) {
                checkGpsTask.setResult(null);
            }
        }
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
        Tasks.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                UserInfo info = new UserInfo();
                info.setDownloadSpeed(bytesPerSecond);
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
                return null;
            }
        });

        return tcs.getTask();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgress(DownloadProgressEvent event) {
        fileBytes = event.getTotalFileSize();
        try {
            mProgressBar.setProgress((event.getTotalBytesWritten() * 100 / event.getTotalFileSize() / event.getSmoothingVariable()));
        } catch (ArithmeticException ignored) {
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FCMPref.REGISTRATION_COMPLETE));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}