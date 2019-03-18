package com.hxzk_bj_demo.ui.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver;
import com.hxzk_bj_demo.utils.MarioResourceHelper;
import com.hxzk_bj_demo.utils.RomUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by Ding on 2017/12/24
 * 作用：
 */

public abstract class BaseActivity extends AppCompatActivity implements ThemeChangeObserver {
    private static final String TAG = "BaseActivity";

    protected  static Activity _context;

    //AppBarLayout mAppBarLayout ;

    //加载子视图内容区域
    LinearLayout layout_ContentView_Base;
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


        //将当前界面子类的Activity添加到栈中
        ActivityJump.AddToTack(this);
        //打印当前活动的activity
        LogUtil.e(TAG, "{当前活动的activity}"+getClass().getSimpleName());
        //打印栈中的activity
        ActivityJump.LogAllActivityNames();
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addStatusBarView();
        setContentView(R.layout.activity_base);
        layout_ContentView_Base= findViewById(R.id.layout_contentview_base);
       // mAppBarLayout =  findViewById(R.id.appbarlayout_base);
        if(setLayoutId() != 0){
            View contentView = LayoutInflater.from(this).inflate(setLayoutId(),null);
            layout_ContentView_Base.addView(contentView);
            //白天模式存在bug(暂时先通过设置字体颜色解决)
            MIUISetStatusBarLightMode(_context,true);
            //绑定Butterknife
            ButterKnife.bind(this);

            initView();
            initEvent();
            initData();

        }
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

    /**
     * 针对小米手机的设置状态栏字体颜色
     * @param activity
     * @param dark
     * @return
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
}
