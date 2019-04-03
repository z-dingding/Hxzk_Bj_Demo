package com.hxzk_bj_demo.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver;
import com.hxzk_bj_demo.utils.LogUtil;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.umeng.analytics.MobclickAgent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil.setLightStatusBar;

/**
 * Created by Ding on 2017/12/24
 * 作用：
 */

public abstract class BaseActivity extends AppCompatActivity implements ThemeChangeObserver {
    private static final String TAG = "BaseActivity";

    protected  static Activity _context;
    //加载子视图内容区域
    LinearLayout layout_ContentView_Base;

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //注册当前Activity主题的监听
        setupActivityBeforeCreate();
        super.onCreate(savedInstanceState);
        //将当前界面子类的Activity添加到栈中
        ActivityJump.AddToTack(this);
        //打印当前活动的activity
        LogUtil.e(TAG, "{当前活动的activity}"+getClass().getSimpleName());
        //打印栈中的activity
        ActivityJump.LogAllActivityNames();
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_base);
        layout_ContentView_Base= findViewById(R.id.layout_contentview_base);
        if(setLayoutId() != 0){
            View contentView = LayoutInflater.from(this).inflate(setLayoutId(),null);
            layout_ContentView_Base.addView(contentView);
            //绑定Butterknife
            unbinder=ButterKnife.bind(this);

            initView();
            initEvent();
            initData();

        }
    }
    //Session启动、App使用时长等基础数据统计接口API：
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        ((MyApplication) getApplication()).unregisterObserver(this);
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * 先将当前Activity注册
     */
    private void setupActivityBeforeCreate() {
        ((MyApplication) getApplication()).registerObserver(this);
        loadingCurrentTheme();
    }


    @Override
    public void loadingCurrentTheme() {
            if(MyApplication.getAppTheme()){//夜间模式
                setTheme(R.style.Base_CustomTheme_Night);
                StatebusTextColorUtil.setStatusBarColor(this,R.color.custom_color_app_status_bg_night);
                setLightStatusBar(this, false);
            }else{//白天模式
                setTheme(R.style.Base_CustomTheme_Day);
                StatebusTextColorUtil.setStatusBarColor(this, R.color.custom_color_app_status_bg_day);
                setLightStatusBar(this, true);
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
