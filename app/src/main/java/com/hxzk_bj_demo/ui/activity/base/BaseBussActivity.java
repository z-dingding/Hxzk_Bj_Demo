package com.hxzk_bj_demo.ui.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.zxing.activity.CaptureActivity;
import com.hxzk_bj_demo.widget.XDialog;
import java.lang.reflect.Method;

import androidx.appcompat.widget.Toolbar;


/**
 * Created by Ding on 2017/12/24
 * 作用：
 */

public class BaseBussActivity extends BaseActivity {

    private static final String TAG = "BaseBussActivity";

    public static final int REQUEST_CODE=0x1111;

    public Toolbar mToolbar;

    private XDialog loadingDialog;


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
            mToolbar = findViewById(R.id.toolbar_main);
            if (mAppBarLayout == null) {
                mAppBarLayout =findViewById(R.id.appbarlayout_base);
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
     * 显示dialog
     *
     * @param mContext 上下文
     */
    public void mshowDialog(Context mContext) {
        if (null == loadingDialog) {
            loadingDialog = new XDialog(mContext, R.style.Dialog_image, "");
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        } else {
            if (!loadingDialog.isShowing()){
                loadingDialog.show();
            }



        }
    }


    /**
     * 让dialog消失
     */
    public void mdismissDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
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
     //   mToolbar.setTitleTextColor(Color.WHITE);
        //设置左上角的图标响应
        getSupportActionBar().setHomeButtonEnabled(true);
        //Toolbar的空间监听有两种方式，一种是 Toolbar.OnMenuItemClickListener，另一种是onOptionsItemSelected
       // mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    /**
     * 控制toolbar的显示隐藏
     * @param visible
     */
    protected void  toolbarVisible(int visible){
        if(mToolbar != null){
            mToolbar.setVisibility(visible);
        }
    }


    /**
     * 重写onCreateOptionsMenu()方法，把这个Toolbar菜单加载进去：
     * 创建Activity是回调方法用于填充Menu的布局，只会执行一次
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * 它的功能是在每次点击一个Menu的时候，它就改变一次，所以你想要改变Menu的值，就得在这里运行
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isShowMenu) {//显示菜单
            switch (fragmentFlag) {
                case 0:
                    menu.findItem(R.id.toolbar_search).setVisible(true);
                    menu.findItem(R.id.action_notifications).setVisible(true);
                    menu.findItem(R.id.toolbar_qrcode).setVisible(true);
                    break;
                case 1:
                    menu.findItem(R.id.toolbar_search).setVisible(true);
                    menu.findItem(R.id.toolbar_qrcode).setVisible(false);
                    menu.findItem(R.id.action_notifications).setVisible(false);
                    break;
                case 2:
                    menu.findItem(R.id.toolbar_search).setVisible(false);
                    menu.findItem(R.id.toolbar_qrcode).setVisible(false);
                    menu.findItem(R.id.action_notifications).setVisible(false);
                    break;
            }

        } else {
            menu.findItem(R.id.toolbar_search).setVisible(false);
            menu.findItem(R.id.toolbar_qrcode).setVisible(false);
            menu.findItem(R.id.action_notifications).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //Google已经不支持在Toolbar的menu中显示图标了，如果要显示，就必须运用反射，强制显示icon
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
            case R.id.action_notifications:
                break;
            case R.id.toolbar_search:

                break;
            case R.id.toolbar_qrcode:
                Intent intent = new Intent(_context, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case android.R.id.home:

                finishActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 页面跳转,将当前activity添加到栈中
     */
    public void addActivityToManager(Context context, Class clazz) {
        ActivityJump.NormalJump(context, clazz);
    }

    /**
     * finish掉当前的activity
     */
    public void finishActivity() {
        ActivityJump.Back(this);
        animBack();
    }

    /**
     * 页面跳转,关闭当前界面
     */
    public void jumpFinishCurrentActivity(Activity mActivity, Class cls) {
        ActivityJump.NormalJumpAndFinish(mActivity, cls);
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
