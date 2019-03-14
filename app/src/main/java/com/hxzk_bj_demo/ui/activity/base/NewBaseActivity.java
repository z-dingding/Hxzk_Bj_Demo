package com.hxzk_bj_demo.ui.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver;
import com.hxzk_bj_demo.utils.LogUtil;
import com.hxzk_bj_demo.utils.activity.ActivityJump;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * 作者：created by ${zjt} on 2019/3/14
 * 描述:
 */
public abstract class NewBaseActivity extends AppCompatActivity implements ThemeChangeObserver {

    private static final String TAG = "BaseActivity";

    protected  static Activity _context;

    //AppBarLayout mAppBarLayout ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //注册当前Activity主题的监听
        setupActivityBeforeCreate();
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            //a|=b的意思就是把a和b按位或然后赋值给a 按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
            //透明状态栏
            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(attributes);
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        addStatusBarView();

    }


    @Override
    protected void onDestroy() {
        ((MyApplication) getApplication()).unregisterObserver(this);
        super.onDestroy();
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
        }else{//白天模式
            setTheme(R.style.Base_CustomTheme_Day);
        }
    }

    //创建view添加到状态栏
    private void addStatusBarView() {
        View status = findViewById(R.id.custom_id_statusbar);
        if (status != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            status.getLayoutParams().height = getStatusBarHeight();
        }
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


    /**
     * 设置layout布局文件
     * */
    //protected abstract int  setLayoutId();

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


    /**
     * */
    public Context getContext() {
        return NewBaseActivity.this;
    }
}
