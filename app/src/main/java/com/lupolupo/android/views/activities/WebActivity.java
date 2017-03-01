package com.lupolupo.android.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lupolupo.android.R;
import com.lupolupo.android.common.FCMPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.activity_web_web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://scottlupo.typeform.com/to/l2BXer?app=LupoLupo&deviceid=" + FCMPref.newInsance().getToken());


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
}
