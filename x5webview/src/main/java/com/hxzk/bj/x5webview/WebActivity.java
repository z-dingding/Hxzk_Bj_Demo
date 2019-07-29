package com.hxzk.bj.x5webview;

import android.os.Bundle;

import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    public static final String KEY_WEB_URL = "key_web_url";

    X5WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
         webView = findViewById(R.id.webview);
        String url = getIntent().getStringExtra(KEY_WEB_URL);
        webView.loadUrl(url);
    }
}
