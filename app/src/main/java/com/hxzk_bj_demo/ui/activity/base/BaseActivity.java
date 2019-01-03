package com.hxzk_bj_demo.ui.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.utils.ActivityJump;
import com.hxzk_bj_demo.utils.LanguageUtil;
import com.hxzk_bj_demo.utils.LogUtil;

import butterknife.ButterKnife;

/**
 * Created by Ding on 2017/12/24
 * 作用：
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected  Activity _context;
    AppBarLayout mAppBarLayout ;


    //加载子视图内容区域
    LinearLayout layout_ContentView_Base;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(MyApplication.getAppTheme()){
//            setTheme(R.style.AppTheme_Night);
//        }else{
//            setTheme(R.style.AppTheme_Light);
//        }

        //将当前界面子类的Activity添加到栈中
        ActivityJump.AddToTack(this);
        //打印当前活动的activity
       // LogUtil.e(TAG, "{当前活动的activity}"+getClass().getSimpleName());
        //栈中的activity
        ActivityJump.LogAllActivityNames();
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_base);
        layout_ContentView_Base= (LinearLayout) findViewById(R.id.layout_contentview_base);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout_base);
        if(setLayoutId() != 0){
            View contentView = LayoutInflater.from(this).inflate(setLayoutId(),null);
            layout_ContentView_Base.addView(contentView);
            //状态栏着色
            //addStatusBarView();
            //绑定Butterknife
            ButterKnife.bind(this);
            initView();
            initEvent();
            initData();
        }
    }


    //创建view添加到状态栏
    private void addStatusBarView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
    /**
     * 设置layout布局文件
     * */
    protected abstract int  setLayoutId();

    /**
     * 初始化控件
     * */
    protected abstract void initView();

    /**
     *初始化监听事件
     * */
    protected abstract void initEvent();

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            doActivityResult(requestCode,resultCode, data);
        }
    }

    protected void doActivityResult(int requestCode, int resultCode,Intent intent){
    }
}
