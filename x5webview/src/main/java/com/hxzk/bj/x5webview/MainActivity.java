package com.hxzk.bj.x5webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    X5WebView mX5WebView;
    //内容显示区域
    private FrameLayout center_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        center_layout = findViewById(R.id.center_layout);
        loadUrl("aaaaaa");
    }

    private void loadUrl(String url) {
        if(mX5WebView == null){
            mX5WebView = new X5WebView(this, null);
        }
        //webview的getX5WebViewExtension()返回非null表示已加载了x5内核webview
        //TBS主要通过共享使用微信手Q的内核而加载x5内核
        if(mX5WebView.getX5WebViewExtension() == null){
            LogUtil.e(TAG, "没有加载了x5内核webview");
        }

        //加载html中input有时失效
        mX5WebView.setFocusable(true);
        mX5WebView.setFocusableInTouchMode(true);
        //设置直接退出
        mX5WebView.setCanReturn(false, MainActivity.this);
        center_layout.addView(mX5WebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mX5WebView.loadWebUrl(url);

    }
}
