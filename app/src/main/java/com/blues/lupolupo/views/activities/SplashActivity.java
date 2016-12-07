package com.blues.lupolupo.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.model.loaders.AppLoader;
import com.blues.lupolupo.model.loaders.ComicLoader;
import com.blues.lupolupo.model.loaders.EpisodeLoader;

import bolts.Continuation;
import bolts.Task;

import static com.blues.lupolupo.views.activities.ComicActivity.INTENT_COMIC;
import static com.blues.lupolupo.views.activities.EpisodeActivity.INTENT_EPISODE;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

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
}
