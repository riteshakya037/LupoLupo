package com.lupolupo.android.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lupolupo.android.R;
import com.lupolupo.android.common.FCMPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    public static final String URL = "web_url";
    private static final String TAG = WebActivity.class.getSimpleName();
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
        if (getIntent().hasExtra(URL)) {
            String url = getIntent().getStringExtra(URL) + "?app=LupoLupo&deviceid=" + FCMPref.newInsance().getToken();
            webView.loadUrl(url);
            urlText.setText(url);
        }
        WebViewClient yourWebClient = new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://scottlupo.typeform.com/to/l2BXer?app=")) {
                    finish();
                    return true;
                }
                return false;
            }
        };
        webView.setWebViewClient(yourWebClient);
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
}
