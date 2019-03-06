package com.hxzk.bj.x5webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class X5MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    X5WebView mX5WebView;
    //内容显示区域
    private FrameLayout center_layout;
    ImageView ivShare,ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5main);
        //状态栏着色
        addStatusBarView();
        center_layout = findViewById(R.id.center_layout);
        ivShare=findViewById(R.id.iv_x5share);
        ivBack=findViewById(R.id.iv_x5back);
        String webUrl =getIntent().getStringExtra("data");
        if(!TextUtils.isEmpty(webUrl)){
            loadUrl(webUrl);
        }
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(X5MainActivity.this,"正在开发敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        mX5WebView.setCanReturn(false, X5MainActivity.this);
        center_layout.addView(mX5WebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mX5WebView.loadWebUrl(url);

    }



    //创建view添加到状态栏
    private void addStatusBarView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorstate));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(this));
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view, params);
    }
    /**
     * 获取状态栏的高度
     * 19API以上 读取到状态栏高度才有意义
     */
    private int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
        } else {
            return 0;
        }
    }
}
