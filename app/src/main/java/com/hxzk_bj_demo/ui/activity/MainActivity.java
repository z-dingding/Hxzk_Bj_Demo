package com.hxzk_bj_demo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.Const;

import com.hxzk_bj_demo.common.MainApplication;
import com.hxzk_bj_demo.javabean.IntegralBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.mvp.constract.MainConstract;
import com.hxzk_bj_demo.mvp.presenter.MainPresenter;
import com.hxzk_bj_demo.mvp.view.NoteBookActivity;
import com.hxzk_bj_demo.network.interceptor.AddInterceptor;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.activity.base.BaseMvpActivity;
import com.hxzk_bj_demo.ui.adapter.base.FragmentAdapter;
import com.hxzk_bj_demo.ui.fragment.ConsultingFragment;
import com.hxzk_bj_demo.ui.fragment.HomeFragment;
import com.hxzk_bj_demo.ui.fragment.UserFragment;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.LanguageUtil;
import com.hxzk_bj_demo.utils.ProgressDialogUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.Subscriber;

import static com.hxzk_bj_demo.R.id.drawerlayout_main;
import static com.hxzk_bj_demo.R.id.vp_main;
import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;
import static com.hxzk_bj_demo.utils.LanguageUtil.setLocale;


/**
 * 注意因为BaseFragmeng中定义了FragmentCallBack接口MainActiviyz中用到了Fragment所以要实现，否则报未知错误
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements BaseFragment.FragmentCallBack, MainConstract.MainView {

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
    private Fragment  investFrag, homeFrag,userFrag ;
    private MenuItem menuItem;


    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    /**
     * 侧边栏用户名展示
     */
    TextView tv_userInfo_hvfromvn;
    /**
     * 侧边栏积分展示
     */
    TextView  tv_userIntegral_hvfromvn;

    /**
     * 退出登录被观察者对象
     */
    Observable<BaseResponse<LoginOutBean>> observable;
    /**
     *获取积分被观察者对象
     */
    Observable<BaseResponse<IntegralBean>> IntegralBeanObservable;

    MainPresenter mainPresenter;
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
        mainPresenter =new MainPresenter(this);
        mainPresenter.onAttachView(this);

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
        //防止5.0一下NavigationView没延伸到状态栏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawer.setFitsSystemWindows(true);
            mDrawer.setClipToPadding(false);
        }
        //设置侧滑监听
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        vp_Main.addOnPageChangeListener(mOnPageChangeListener);
        bav_Main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //获取头布局文件
        View headerView = navigationview_Main.getHeaderView(0);
        //过调用headerView中的findViewById方法来查找到头部的控件
        headerView.findViewById(R.id.img_uphoto_hvfromnv).setOnClickListener(v -> ToastCustomUtil.showShortToast("点击了用户头像"));
        tv_userInfo_hvfromvn = (TextView) headerView.findViewById(R.id.tv_userInfo_hvfromvn);
        tv_userIntegral_hvfromvn =headerView.findViewById(R.id.tv_userIntegral_hvfromvn);
        //设置用户详细信息点击
        UserInforLink();
        tv_userIntegral_hvfromvn.setOnClickListener(v -> {
           ActivityJump.NormalJump(this,IntegralActivity.class);
        });
        navigationview_Main.setNavigationItemSelectedListener(item -> {
            //在这里处理item的点击事件
            switch (item.getItemId()) {
                case R.id.theme:
                    if(MainApplication.getAppTheme()){
                        MainApplication.setAppTheme(false);
                    }else{
                        MainApplication.setAppTheme(true);
                    }
                    loadingCurrentTheme();
                    //此处刷新主题，调用所有的注册的观察者
                    ((MainApplication)getApplication()).notifyByThemeChanged();
                    //刷新Activitytoolbar才会变动图标
                    MainActivity.this.recreate();
                    break;
                case R.id.favorite:
                    ActivityJump.NormalJump(MainActivity.this, CollectionActivity.class);
                    mDrawer.closeDrawers();
                    break;

                case R.id.notebook:
                    ActivityJump.NormalJump(MainActivity.this, NoteBookActivity.class);
                    break;

                case R.id.photo:
                    break;

                case R.id.loginout:
                    observable =mainPresenter.loginOutP();
                    break;

                case R.id.settting:
                    String lan = LanguageUtil.getAppLanguage(MainActivity.this);
                    if ("zh".equals(lan) || !"en".equals(lan)) {
                        setLocale(MainActivity.this, Locale.US);
                    } else {
                        setLocale(MainActivity.this, Locale.SIMPLIFIED_CHINESE);
                    }
                    break;
                default :
                    break;

            }
            mDrawer.closeDrawers();
            return true;
        });
        MultPermission();
    }



    @Override
    public void notifyByThemeChanged() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vp_Main != null) {
            vp_Main = null;
        }
        HttpRequest.getInstance().unsubscribe(observable);
        HttpRequest.getInstance().unsubscribe(IntegralBeanObservable);
    }



    /**
     * 点击跳转用户详细信息
     */
    private void UserInforLink() {
        //获取用户名
        String userName = (String) SPUtils.get(this, Const.KEY_LOGIN_ACCOUNT,"");
        String endString =String.format(getResources().getString(R.string.sideslip_welcom).toString(),userName);
        //先判断中英文
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            SpannableString mSpannableString = new SpannableString(getString(R.string.sideslip_welcom));
            //加粗字体
            StyleSpan mStyleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
            mSpannableString.setSpan(mStyleSpan, 8, userName.length()+8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加点击事件
            //使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法
            mSpannableString.setSpan(null, 8, userName.length() + 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加前背景色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
            mSpannableString.setSpan(foregroundColorSpan, 8, userName.length()+8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            tv_userInfo_hvfromvn.setMovementMethod(LinkMovementMethod.getInstance());
            tv_userInfo_hvfromvn.setText(mSpannableString);

        } else {//中文
            SpannableString mSpannableString = new SpannableString(endString);
            //加粗字体
            StyleSpan mStyleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
            mSpannableString.setSpan(mStyleSpan, 2, userName.length()+2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //设置点击事件
            //使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法
            mSpannableString.setSpan(null, 2, userName.length() + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //加前背景色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
            mSpannableString.setSpan(foregroundColorSpan, 2, userName.length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            tv_userInfo_hvfromvn.setMovementMethod(LinkMovementMethod.getInstance());
            tv_userInfo_hvfromvn.setText(mSpannableString);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        homeFrag = HomeFragment.getInstance(HomeFragment.class, null);
        investFrag = ConsultingFragment.getInstance(ConsultingFragment.class, null);
        userFrag = UserFragment.getInstance(UserFragment.class, null);
        List<Fragment> list = new ArrayList<>();
        list.add(homeFrag);
        list.add(investFrag);
        list.add(userFrag);
        adapter = new FragmentAdapter(getSupportFragmentManager(), _context, list);
        vp_Main.setAdapter(adapter);
        vp_Main.setOffscreenPageLimit(3);

        //获取用户积分
        IntegralBeanObservable =mainPresenter.integralP();
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
                    toolbarVisible(View.GONE);
                    fragmentFlag = 2;
                    isShowMenu = false;
                    getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    invalidateOptionsMenu();
                    break;
                    default:
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
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
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
                        //LogUtil.e(TAG, "授权异常请检查处理");//可能是授权异常的情况下的处理
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
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            homeFrag.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onLoginoutResult(BaseResponse<LoginOutBean> loginoutbean) {
        if (!loginoutbean.isOk()) {
            ToastCustomUtil.showLongToast(loginoutbean.getMsg());
        } else {
            //清空保存在本地的cookie
            AddInterceptor.clearCookie(MainActivity.this,KEY_COOKIE);
            ActivityJump.finnishAllActivitys();
            //登出
            MobclickAgent.onProfileSignOff();
            ActivityJump.NormalJumpAndFinish(MainActivity.this, LoginActivity.class);
        }
    }

    @Override
    public void onIntegralResult(BaseResponse<IntegralBean> integralBeanBaseResponse) {
        if (!integralBeanBaseResponse.isOk()) {
            ToastCustomUtil.showLongToast(integralBeanBaseResponse.getMsg());
        } else {
          IntegralBean integralBean =  integralBeanBaseResponse.getData();
            tv_userIntegral_hvfromvn.setText(integralBean.getCoinCount()+">");
        }
    }

    @Override
    public void onShowLoading() {
        ProgressDialogUtil.getInstance().mshowDialog(this);
    }

    @Override
    public void onHiddenLoading() {
        ProgressDialogUtil.getInstance().mdismissDialog();
    }

    @Override
    public void onFail(Throwable throwable) {
        ToastCustomUtil.showLongToast(throwable.getMessage());
    }
}
