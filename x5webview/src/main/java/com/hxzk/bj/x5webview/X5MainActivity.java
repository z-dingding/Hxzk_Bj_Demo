package com.hxzk.bj.x5webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil;

import static com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil.setLightStatusBar;
import static com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil.transparencyBar;

public class X5MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String FILE_NAME = "share_data";

    X5WebView mX5WebView;
    RelativeLayout relativeLayout;
    //内容显示区域
    private FrameLayout center_layout;
    ImageView ivShare,ivBack;

    View viewState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        boolean theme =sp.getBoolean("apptheme", true);
        if(theme){//夜间模式
            setTheme(R.style.AppTheme_Night);
            StatebusTextColorUtil.setStatusBarColor(this, R.color.custom_color_app_status_bg_night);
            setLightStatusBar(this, false);
        }else{//白天模式
            setTheme(R.style.AppTheme_Day);
            StatebusTextColorUtil.setStatusBarColor(this,  R.color.custom_color_app_status_bg_day);
            setLightStatusBar(this, true);
        }


        setContentView(R.layout.activity_x5main);
        relativeLayout=findViewById(R.id.linear_x5share);
        MarioResourceHelper helper = MarioResourceHelper.getInstance(X5MainActivity.this);
       // helper.setBackgroundResourceByAttr(viewState, R.attr.custom_attr_app_statusbar_bg);
        helper.setBackgroundResourceByAttr(relativeLayout, R.attr.custom_attr_app_statusbar_bg);
        helper.setImageResourceByAttr(ivShare, R.attr.custom_attr_app_more);
        helper.setImageResourceByAttr(ivBack, R.attr.custom_attr_app_back);


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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mX5WebView != null) {
            mX5WebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mX5WebView.removeAllViews();
            mX5WebView.clearHistory();
            ViewParent parent = mX5WebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mX5WebView);
            }
            mX5WebView.destroy();
        }
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







}
