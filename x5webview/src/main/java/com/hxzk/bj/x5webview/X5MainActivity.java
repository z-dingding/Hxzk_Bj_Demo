package com.hxzk.bj.x5webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil;
import com.hxzk.bj.x5webview.util.Mobile;
import com.tencent.smtt.sdk.WebView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil.setLightStatusBar;
import static com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil.transparencyBar;

public class X5MainActivity extends AppCompatActivity implements Mobile.ScanListener{

    private static final String TAG = "MainActivity";
    public static final String FILE_NAME = "share_data";

    X5WebView mX5WebView;
    RelativeLayout relativeLayout;
    /**
     * 显示加载进度ProgressBar
     */
    ProgressBar mProgressBar;
    ProgressDialog scanDialog;
    /**
     * 内容显示区域
     */
    private FrameLayout center_layout;
    ImageView ivShare,ivBack;
    /**
     * 本地js转化为输出流字符串
     */
    String initJs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        boolean theme =sp.getBoolean("apptheme", true);
        //夜间模式
        if(theme){
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
        MarioResourceHelper helper = MarioResourceHelper.getInstance(getApplicationContext());
       // helper.setBackgroundResourceByAttr(viewState, R.attr.custom_attr_app_statusbar_bg);
        helper.setBackgroundResourceByAttr(relativeLayout, R.attr.custom_attr_app_statusbar_bg);
        helper.setImageResourceByAttr(ivShare, R.attr.custom_attr_app_more);
        helper.setImageResourceByAttr(ivBack, R.attr.custom_attr_app_back);


        center_layout = findViewById(R.id.center_layout);
        mProgressBar =findViewById(R.id.progressBar);
        ivShare=findViewById(R.id.iv_x5share);
        ivBack=findViewById(R.id.iv_x5back);
        //进行js交互
        initJS();
        String webUrl =getIntent().getStringExtra("data");
        if(!TextUtils.isEmpty(webUrl)){
            loadUrl(webUrl);
        }
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"正在开发敬请期待",Toast.LENGTH_SHORT).show();
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

    /**
     * 加载本地的js方法,要实现长按识别二维码和点击查看图片，
     * 就需要页面中的touchstart，touchmove，touchend方法监听
     */
    private void initJS(){
        InputStream inputStream = this.getResources().openRawResource(R.raw.init);
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            while ((len = inputStream.read(buffer)) >= 0) {
                bos.write(buffer, 0, len);
            }
            inputStream.close();
            initJs = bos.toString();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    private void loadUrl(String url) {
        if(mX5WebView == null){
            mX5WebView = new X5WebView(getApplicationContext(), null);
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


        //注入mobile对象
        Mobile mobile = new Mobile(X5MainActivity.this);
        mobile.setScanListener(this);
        //指定js交互的类对象
        mX5WebView.addJavascriptInterface(mobile, "mobile");


        center_layout.addView(mX5WebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mX5WebView.loadWebUrl(url);

        mX5WebView.setWebChromeClient(new X5WebChromeClient(getApplicationContext(),mX5WebView){
            @Override
            public void onProgressChanged(WebView view, int newProgress) throws NullPointerException {
                if (newProgress == 100) {
                    // 加载完成，将进度条隐藏
                    mProgressBar.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE) {
                        mProgressBar.setVisibility(VISIBLE);
                    }
                    // 设置加载进度
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                return false;
            }
        });



        mX5WebView.setWebViewClient(new X5WebViewClient(getApplicationContext(),mX5WebView) {
            @Override
            public void onPageFinished(WebView webView, String url) {
                webView.evaluateJavascript("javascript:" + initJs, null);
            }
        });
    }


    @Override
    public void onScanStart() {
        if (scanDialog == null) {
            scanDialog = new ProgressDialog(this);
            scanDialog.setMessage("正在识别");
            scanDialog.setCancelable(false);

        }
        scanDialog.show();
    }

    @Override
    public void onScanFailed(String info) {
        if (scanDialog != null) {
            scanDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(
               this);
        builder.setMessage("识别失败")
                .setTitle("二维码扫描")
                .setCancelable(false)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        //点击返回键或alertdialog以外区域是否取消
        //builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onScanSuccess(final String result) {
        if (scanDialog != null) {
            scanDialog.dismiss();
        }
        boolean isHttpLink = false;
        String info = result;
        if (!TextUtils.isEmpty(result)) {
            String lowerCase = result.toLowerCase();
            if (lowerCase.startsWith("http")) {
                isHttpLink = true;
                info = "是否跳转到链接:" + info;
            }
        }
        final boolean finalIsHttpLink = isHttpLink;
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setMessage(info)
                .setTitle("二维码扫描")
                .setCancelable(false)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                if (finalIsHttpLink) {
                                    Intent intent = new Intent(X5MainActivity.this, WebActivity.class);
                                    intent.putExtra(WebActivity.KEY_WEB_URL, result);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog, int id) {
                        dialog.cancel();


                    }
                });
        //点击返回键或alertdialog以外区域是否取消
        //builder.setCancelable(false);
        builder.show();
    }
}
