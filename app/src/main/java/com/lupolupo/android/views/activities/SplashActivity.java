package com.lupolupo.android.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.model.loaders.ComicLoader;
import com.lupolupo.android.model.loaders.EpisodeLoader;

import bolts.Continuation;
import bolts.Task;

import static com.lupolupo.android.views.activities.ComicActivity.INTENT_COMIC;
import static com.lupolupo.android.views.activities.EpisodeActivity.INTENT_EPISODE;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(INTENT_COMIC)) {
            ComicLoader.getInstance().startLoading((Comic) getIntent().getParcelableExtra(INTENT_COMIC)).onSuccess(new Continuation<Task<Void>, Void>() {
                @Override
                public Void then(Task<Task<Void>> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, ComicActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else if (getIntent().hasExtra(INTENT_EPISODE)) {
            EpisodeLoader.getInstance().startLoading((Episode) getIntent().getParcelableExtra(INTENT_EPISODE)).onSuccess(new Continuation<Task<Void>, Void>() {
                @Override
                public Void then(Task<Task<Void>> task) throws Exception {
                    Intent intent = new Intent(SplashActivity.this, EpisodeActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            });
        } else {
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
        AppLoader.getInstance().startLoading().onSuccess(new Continuation<Task<Void>, Void>() {
            @Override
            public Void then(Task<Task<Void>> task) throws Exception {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return null;
            }
        });
    }
}
