package com.hxzk.bj.x5webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

        SharedPreferences sp = getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        boolean theme =sp.getBoolean("apptheme", false);
        if(theme){//夜间模式
            setTheme(R.style.AppTheme_Night);
        }else{//白天模式
            setTheme(R.style.AppTheme_Day);
        }
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            //a|=b的意思就是把a和b按位或然后赋值给a 按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
            //透明状态栏
            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(attributes);
        }
        View status = findViewById(R.id.custom_id_statusbar);
        if (status != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            status.getLayoutParams().height = getStatusBarHeight();
        }

        setContentView(R.layout.activity_x5main);
        viewState=findViewById(R.id.custom_id_statusbar);
        relativeLayout=findViewById(R.id.linear_x5share);
        MarioResourceHelper helper = MarioResourceHelper.getInstance(X5MainActivity.this);
        helper.setBackgroundResourceByAttr(viewState, R.attr.custom_attr_app_statusbar_bg);
        helper.setBackgroundResourceByAttr(relativeLayout, R.attr.custom_attr_app_statusbar_bg);


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


    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result =getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



}
