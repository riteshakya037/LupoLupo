package com.lupolupo.android.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.preseneters.events.WebEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Callable;

import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    public static final String URL = "web_url";
    private static final String TAG = WebActivity.class.getSimpleName();
    public static final String EPISODE_NAME = "episode_name";
    @BindView(R.id.activity_web_web_view)
    WebView webView;

    @BindView(R.id.activity_web_url)
    TextView urlText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        getAdId();
        WebViewClient yourWebClient = new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(getIntent().getStringExtra(URL))) {
                    finish();
                    return true;
                }
                return false;
            }
        };
        webView.setWebViewClient(yourWebClient);
    }

    private void getAdId() {
        Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AdvertisingIdClient.Info idInfo;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(LupolupoAPIApplication.get());
                    EventBus.getDefault().post(new WebEvent(idInfo.getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateView(WebEvent webEvent) {
        String url = getIntent().getStringExtra(URL) + "?app=LupoLupo" + "&deviceid=" + webEvent.getId() + "&episode=" + getIntent().getStringExtra(EPISODE_NAME);
        webView.loadUrl(url);
        urlText.setText(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
