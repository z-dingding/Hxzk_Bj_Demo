package com.hxzk_bj_demo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.mvp.view.NoteBookActivity;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.ExceptionHandle;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.base.FragmentAdapter;
import com.hxzk_bj_demo.ui.fragment.HomeFragment;
import com.hxzk_bj_demo.ui.fragment.InvestFragment;
import com.hxzk_bj_demo.ui.fragment.UserFragment;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.LanguageUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.activity.ActivityManager;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.Subscriber;

import static com.hxzk_bj_demo.R.id.vp_main;
import static com.hxzk_bj_demo.utils.LanguageUtil.setLocale;


//注意因为BaseFragmeng中定义了FragmentCallBack接口MainActiviyz中用到了Fragment所以要实现，否则报未知错误
public class MainActivity extends BaseBussActivity implements BaseFragment.FragmentCallBack {


    private static final String TAG = "MainActivity";
    private static final int HOME = 0;
    private static final int INVEST = 1;
    private static final int USER = 2;


    @BindView(vp_main)
    ViewPager vp_Main;


    @BindView(R.id.bav_main)
    BottomNavigationView bav_Main;

    @BindView(R.id.navigationview_main)
    NavigationView navigationview_Main;


    private FragmentAdapter adapter;
    private Fragment  investFrag, homeFrag , userFrag;
    private MenuItem menuItem;


    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;

    TextView tv_userInfo_hvfromvn;


    Observable<BaseResponse<LoginOutBean>> observable;
    Subscriber<BaseResponse<LoginOutBean>> subscriber;

    @Override
    protected int setLayoutId() {
        _context = MainActivity.this;
        //解决从登陆页跳转过来isShowMenu值为false
        isShowMenu = true;
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        super.initView();
        ActivityJump.popSpecifiedActivity(LoginActivity.class);
        //初始化DrawerLayout
        mDrawer = (DrawerLayout) findViewById(R.id.drawerlayout_main);
        initToolbar(R.drawable.back, getResources().getString(R.string.home));
        //让图片显示本来颜色
        navigationview_Main.setItemIconTintList(null);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.syncState();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//防止5.0一下NavigationView没延伸到状态栏
            mDrawer.setFitsSystemWindows(true);
            mDrawer.setClipToPadding(false);
        }
        mDrawer.setDrawerListener(mDrawerToggle); //设置侧滑监听
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        vp_Main.addOnPageChangeListener(mOnPageChangeListener);
        bav_Main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //获取头布局文件
        View headerView = navigationview_Main.getHeaderView(0);
        //过调用headerView中的findViewById方法来查找到头部的控件
        headerView.findViewById(R.id.img_uphoto_hvfromnv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastCustomUtil.showShortToast("点击了用户头像");
            }
        });
        tv_userInfo_hvfromvn = (TextView) headerView.findViewById(R.id.tv_userInfo_hvfromvn);

        //设置用户详细信息点击
        UserInforLink();

        navigationview_Main.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    case R.id.theme:
                        break;
                    case R.id.favorite:
                        addActivityToManager(MainActivity.this, CollectionActivity.class);
                        break;

                    case R.id.notebook:
                        addActivityToManager(MainActivity.this, NoteBookActivity.class);
                        break;

                    case R.id.photo:
                        break;

                    case R.id.loginout:

                        subscriber = new BaseSubscriber<BaseResponse<LoginOutBean>>(MainActivity.this) {

                            @Override
                            public void onResult(BaseResponse<LoginOutBean> baseResponse) {
                                if (!baseResponse.isOk()) {
                                    ToastCustomUtil.showLongToast(baseResponse.getMsg());
                                } else {
                                    //清空保存在本地的cookie
                                    SPUtils.remove(MainActivity.this, "ygcy.drugwebcn.com");
                                    ActivityJump.finnishAllActivitys();
                                    ActivityJump.NormalJumpAndFinish(MainActivity.this, LoginActivity.class);
                                }
                            }

//                            @Override
//                            public void onFail(ExceptionHandle.ResponeThrowable e) {
//                                ToastCustomUtil.showLongToast(e.message);
//                            }


                            @Override
                            public void onFail(Throwable e) {
                                ToastCustomUtil.showLongToast(e.getMessage());
                            }


                        };
                        observable = HttpRequest.getInstance().getServiceInterface().loginout();
                        //用observable提供的onErrorResumeNext 则可以将你自定义的Func1 关联到错误处理类中
                         observable.onErrorResumeNext(new BaseSubscriber.HttpResponseFunc<>());
                         HttpRequest.getInstance().toSubscribe(observable, subscriber);
                        break;

                    case R.id.settting:
                        String lan = LanguageUtil.getAppLanguage(MainActivity.this);
                        if (lan.equals("zh") || !lan.equals("en")) {
                            setLocale(MainActivity.this, Locale.US);

                        } else if (lan.equals("en") || !lan.equals("zh")) {
                            setLocale(MainActivity.this, Locale.SIMPLIFIED_CHINESE);
                        }
                        break;
                }
                return true;
            }
        });
        MultPermission();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vp_Main != null) {
            vp_Main = null;
        }
        HttpRequest.getInstance().unsubscribe(observable);
    }



    /**
     * 点击跳转用户详细信息
     */
    private void UserInforLink() {

        //先判断中英文
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) { //英文
            SpannableString mSpannableString = new SpannableString(getString(R.string.sideslip_welcom));
            //加粗字体
            StyleSpan mStyleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
            mSpannableString.setSpan(mStyleSpan, 8, 20, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加点击事件
            //使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法
            mSpannableString.setSpan(new MyClickableSpan(""), 8, 20, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加前背景色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
            mSpannableString.setSpan(foregroundColorSpan, 8, 20, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            tv_userInfo_hvfromvn.setMovementMethod(LinkMovementMethod.getInstance());
            tv_userInfo_hvfromvn.setText(mSpannableString);

        } else {//中文
            SpannableString mSpannableString = new SpannableString(getString(R.string.sideslip_welcom));
            //加粗字体
            StyleSpan mStyleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
            mSpannableString.setSpan(mStyleSpan, 2, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //设置点击事件
            //使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法
            mSpannableString.setSpan(new MyClickableSpan(""), 2, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加前背景色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
            mSpannableString.setSpan(foregroundColorSpan, 2, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            tv_userInfo_hvfromvn.setMovementMethod(LinkMovementMethod.getInstance());
            tv_userInfo_hvfromvn.setText(mSpannableString);
        }
    }


    class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();//创建Intent对象
            intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
            intent.putExtra("content", content);//可以传递数据到下个页面
            intent.setData(Uri.parse("http://www.baidu.com"));//为Intent设置数据
            startActivity(intent);//将Intent传递给Activity
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //设置不显示下划线，默认显示
            //  ds.setUnderlineText(false);
            super.updateDrawState(ds);
        }


    }

    @Override
    protected void initData() {
        super.initData();
        homeFrag = HomeFragment.getInstance(HomeFragment.class, null);
        investFrag = InvestFragment.getInstance(InvestFragment.class, null);
        userFrag = UserFragment.getInstance(UserFragment.class, null);
        List<Fragment> list = new ArrayList<>();
        list.add(homeFrag);
        list.add(investFrag);
        list.add(userFrag);
        adapter = new FragmentAdapter(getSupportFragmentManager(), _context, list);
        vp_Main.setAdapter(adapter);
        vp_Main.setOffscreenPageLimit(3);
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("ResourceType")
        @Override
        public void onPageSelected(int position) {
            if (menuItem != null) {
                menuItem.isChecked();
            } else {
                bav_Main.getMenu().getItem(0).setChecked(false);
            }
            switch (position) {
                case 0:
                    mToolbar.setTitle(getResources().getString(R.string.home));
                    toolbarVisible(View.VISIBLE);
                    fragmentFlag = 0;
                    isShowMenu = true;
                    getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    invalidateOptionsMenu();
                    break;
                case 1:
                    mToolbar.setTitle(getResources().getString(R.string.investment));
                    toolbarVisible(View.GONE);
                    fragmentFlag = 1;
                    isShowMenu = true;
                    getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    invalidateOptionsMenu();
                    break;

                case 2:
                    mToolbar.setTitle(getResources().getString(R.string.mine));
                    toolbarVisible(View.VISIBLE);
                    fragmentFlag = 2;
                    isShowMenu = false;
                    getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    invalidateOptionsMenu();
                    break;
            }
            menuItem = bav_Main.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //item.getOrder()对应menu里的orderInCategory属性值
            vp_Main.setCurrentItem(item.getOrder());
            return true;
        }
    };


    @Override
    public void setValue(Object... param) {
        Bundle bundleData = (Bundle) param[0];
        int flag = bundleData.getInt("fragmentflag");
//        switch (flag){
//            case 2:
//                showSearchOnMenu=true;
//                this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
//                 invalidateOptionsMenu();
//
//                break;
//        }
    }






    /*==================Android6.0运行时权限(基于RxPermission开源库)===========================*/

    /**
     * 同时请求多个权限（合并结果）的情况
     */

    private void MultPermission() {
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this); // 创建实例
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE

        )//权限名称，多个权限之间逗号分隔ca
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        //执行顺序——1【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            //   LogUtil.e(TAG, "已经获取权限");
                        } else {
                            // 未获取权限
                            ToastCustomUtil.showShortToast("您没有授权该权限，请在设置中打开授权");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // LogUtil.e(TAG, "授权异常请检查处理");//可能是授权异常的情况下的处理
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //LogUtil.e(TAG, "获取权限执行完毕");//执行顺序——2
                    }
                });
    }


    @Override
    protected void doActivityResult(int requestCode, int resultCode, Intent data) {
        super.doActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            homeFrag.onActivityResult(requestCode, resultCode, data);
        }

    }
}
