package com.hxzk_bj_demo.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.hxzk.bj.common.X5ActionMessage;
import com.hxzk.bj.x5webview.action.X5Action;
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver;
import com.hxzk_bj_demo.network.AddInterceptor;
import com.hxzk_bj_demo.network.SaveInterceptor;
import com.hxzk_bj_demo.ui.activity.WelcomeActivity;
import com.hxzk_bj_demo.utils.LanguageUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xzt.xrouter.router.Xrouter;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatDelegate;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.hxzk_bj_demo.utils.LanguageUtil.isSameWithSetting;
import static com.hxzk_bj_demo.utils.LanguageUtil.setLocale;

/**
 * Created by ${赵江涛} on 2017-12-21.
 * 作用:
 */

  /* Java程序创建一个实例的过程为：
       1.分配内存空间
       2. 初始化对象
       3.将内存空间的地址赋值给对应的引用*/

public class MyApplication extends LitePalApplication {

    //通过volatile关键字来确保安全，使用该关键字修饰的变量在被变更时会被其他变量可见
    private volatile static Context appContext = null;
    private volatile static OkHttpClient.Builder httpClientBuilder;

    private List<ThemeChangeObserver> mThemeChangeObserverStack; //  主题切换监听栈


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            //这个过程专用于LeakCanary用于堆分析,在这个过程中，你不应该init你的应用程序。
            return;
           /* LeakCanary的内存泄露提示一般会包含三个部分:
            第一部分(LeakSingle类的sInstance变量)引用第二部分(LeakSingle类的mContext变量),
             导致第三部分(MainActivity类的实例instance)泄露.
                    如果手动启动activity进行内存检测则需要执行:adb shell am start  -n [包名]/[Activity名]*/
        }
        LeakCanary.install(this);
        //正常程序初始化代码…
        //获取全局Context对象
        appContext = getApplicationContext();
        httpClientBuilder = getOkHttpClientbBuild();
        //注册Activity生命周期监听回调
        registerActivityLifecycleCallbacks(callbacks);
        //初始化路由
        initRouter();
        //友盟相关
        initUMeng();


    }

    /**
     * 初始化友盟相关
     */
    private void initUMeng() {
        //接口控制【友盟+】LOG的输出
        UMConfigure.setLogEnabled(true);
        //添加初始化方法:上下文,AppKey,Channel,设备类型，Push推送业务的secret
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


    /**
     * 注册路由
     */
    private void initRouter() {
        Xrouter.getInstance().registerAction(X5ActionMessage.X5ACTIONNAME, new X5Action());
    }

    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            if (!isSameWithSetting(activity) && !(activity instanceof WelcomeActivity)) {
                String lan = LanguageUtil.getAppLanguage(activity);
                if (lan.equals("zh") || !lan.equals("en")) {
                    setLocale(activity, Locale.SIMPLIFIED_CHINESE);
                } else if (lan.equals("en") || !lan.equals("zh")) {
                    setLocale(activity, Locale.US);
                }
            }

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

        //其他生命周期重载方法。
    };


    /**
     * 获取全局的上下文对象
     *
     * @return
     */
    public static Context getAppContext() {
        return appContext;
    }


    /**
     * 获取httpclient.builder对象实例
     *
     * @return
     */
    private static final HashMap<String, List<Cookie>> cookiestore = new HashMap<>();


    public static OkHttpClient.Builder getOkHttpClientbBuild() {
        if (httpClientBuilder == null) {
            synchronized (MyApplication.class) {
                httpClientBuilder = new OkHttpClient.Builder()
                        .connectTimeout(50000, TimeUnit.SECONDS)
                        .readTimeout(50000, TimeUnit.SECONDS)
                        .writeTimeout(50000, TimeUnit.SECONDS)
                        //cookie的持久化添加到请求头
                        .addInterceptor(new AddInterceptor())
                        //cookie的持久化保存到本地
                        .addInterceptor(new SaveInterceptor());
            }
        }
        return httpClientBuilder;
    }


    /**
     * 默认设置app主题
     */
    public static void setAppTheme(boolean model) {
        SPUtils.put(getAppContext(), "apptheme", model);
    }

    /**
     * Boolean默认类型是false即默认白天模式
     */
    public static boolean getAppTheme() {
        return (boolean) SPUtils.get(getAppContext(), "apptheme", false);
    }

    /**
     * 获得observer堆栈
     * */
    private List<ThemeChangeObserver> obtainThemeChangeObserverStack() {
        if (mThemeChangeObserverStack == null)
            mThemeChangeObserverStack = new ArrayList<>();
        return mThemeChangeObserverStack;
    }

    /**
     * 向堆栈中添加observer
     * */
    public void registerObserver(ThemeChangeObserver observer) {
        if (observer == null || obtainThemeChangeObserverStack().contains(observer)) return ;
        obtainThemeChangeObserverStack().add(observer);
    }

    /**
     * 从堆栈中移除observer
     * */
    public void unregisterObserver(ThemeChangeObserver observer) {
        if (observer == null || !(obtainThemeChangeObserverStack().contains(observer))) return ;
        obtainThemeChangeObserverStack().remove(observer);
    }

    /**
     * 向堆栈中所有对象发送更新UI的指令
     * */
    public void notifyByThemeChanged() {
        List<ThemeChangeObserver> observers = obtainThemeChangeObserverStack();
        for (ThemeChangeObserver observer : observers) {
            observer.loadingCurrentTheme(); //
            observer.notifyByThemeChanged(); //
        }
    }

}
