package com.hxzk_bj_demo.ui.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.activity.HomeSearchActivity;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.zxing.activity.CaptureActivity;
import com.hxzk_bj_demo.widget.XDialog;

import java.lang.reflect.Method;

import androidx.appcompat.widget.Toolbar;


/**
 * Created by Ding on 2017/12/24
 * 作用：
 */

public abstract class BaseBussActivity extends BaseActivity {

    private static final String TAG = "BaseBussActivity";

    public static final int REQUEST_CODE = 0x1111;

    public Toolbar mToolbar;
    /**
     * base.xml中的根布局view
     */
    public LinearLayout mRootLinear;
    public View statebarView;

    Menu toolbarMenu;
    public MenuItem menuItemSearch;
    public MenuItem menuItemNotify;
    public MenuItem menuItemQRCoder;

    /**
     * 显示隐藏toolbar的Menu
     */
    public static boolean isShowMenu = true;
    /**
     * 当前Fragment标识，对应toolbar不同显示，默认为首页
     */
    public static int fragmentFlag = 0;


    @Override
    protected int setLayoutId() {
        return 0;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        if (null == mToolbar) {
            mToolbar = findViewById(R.id.custom_id_app_toolbar);
            if (mRootLinear == null) {
                mRootLinear = findViewById(R.id.custom_id_app);
            }
            if (statebarView == null) {
                statebarView = findViewById(R.id.custom_id_statusbar);
            }
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    /**
     * 初始化Toolbar
     *
     * @param resId
     * @param titleContent
     */
    protected void initToolbar(int resId, String titleContent) {
        //setTitle要设置在setSupportActionBar之前，不然默认appName.
        mToolbar.setTitle(titleContent);
        setSupportActionBar(mToolbar);
        //设置导航图标要在setSupportActionBar方法之后
        mToolbar.setNavigationIcon(resId);
        mToolbar.inflateMenu(R.menu.menu_main);
        //设置左上角的图标响应
        getSupportActionBar().setHomeButtonEnabled(true);
        //Toolbar的控件监听有两种方式，一种是 Toolbar.OnMenuItemClickListener另一种是onOptionsItemSelected
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * 控制toolbar的显示隐藏
     *
     * @param visible
     */
    protected void toolbarVisible(int visible) {
        if (mToolbar != null) {
            mToolbar.setVisibility(visible);
        }
    }


    /**
     * 重写onCreateOptionsMenu()方法，把这个Toolbar菜单加载进去：
     * 创建Activity是回调方法用于填充Menu的布局，只会执行一次
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        toolbarMenu = menu;
        menuItemSearch = toolbarMenu.findItem(R.id.custom_id_toobbaricon_notify);
        menuItemNotify = toolbarMenu.findItem(R.id.custom_id_toobbaricon_search);
        menuItemQRCoder = toolbarMenu.findItem(R.id.custom_id_toobbaricon_qrcoder);
        return true;
    }


    /**
     * 它的功能是在每次点击一个Menu的时候，它就改变一次，所以你想要改变Menu的值，就得在这里运行
     * 方法在创建菜单时(onCreateOptionMenu)会调用一次
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //显示菜单
        if (isShowMenu) {
            switch (fragmentFlag) {
                case 0:
                    menuItemSearch.setVisible(true);
                    menuItemNotify.setVisible(true);
                    menuItemQRCoder.setVisible(true);
                    break;
                case 1:
                    menuItemSearch.setVisible(true);
                    menuItemQRCoder.setVisible(false);
                    menuItemNotify.setVisible(false);
                    break;
                case 2:
                    menuItemSearch.setVisible(false);
                    menuItemQRCoder.setVisible(false);
                    menuItemNotify.setVisible(false);
                    break;
                default:
            }

        } else {
            menuItemSearch.setVisible(false);
            menuItemQRCoder.setVisible(false);
            menuItemNotify.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Google已经不支持在Toolbar的menu中显示图标了，如果要显示，就必须运用反射，强制显示icon
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 处理Menu的item点击事件的
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.custom_id_toobbaricon_notify:
                break;
            case R.id.custom_id_toobbaricon_search:
                addActivityToManager(this, HomeSearchActivity.class);
                break;
            case R.id.custom_id_toobbaricon_qrcoder:
                Intent intent = new Intent(_context, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case android.R.id.home:
                finishActivity();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyByThemeChanged() {

    }

    /**
     * 页面跳转动画,将当前activity添加到栈中
     */
    public void addActivityToManager(Context context, Class clazz) {
        ActivityJump.NormalJump(context, clazz);
        animNext();
    }


    /**
     * 返回上一页面退出动画,关闭当前界面
     */
    public void jumpFinishCurrentActivity(Activity mActivity, Class cls) {
        ActivityJump.NormalJumpAndFinish(mActivity, cls);
        animNext();
    }

    /**
     * finish掉当前的页面动画
     */
    public void finishActivity() {
        ActivityJump.Back(this);
        animBack();
    }

    /**
     * Bundle携参跳转带动画效果
     *
     * @param mContext
     * @param cls
     * @param bundle
     */
    public void jumpBundleActivity(Context mContext, Class<?> cls, Bundle bundle) {
        ActivityJump.BundleJump(mContext, cls, bundle);
        animNext();

    }

    /**
     * @Desc 页面跳转动画
     */

    public void animNext() {
        /**<<<------右入左出*/
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * @Desc 页面返回动画
     */
    public void animBack() {
        /**------>>>左入右出*/
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }


}
