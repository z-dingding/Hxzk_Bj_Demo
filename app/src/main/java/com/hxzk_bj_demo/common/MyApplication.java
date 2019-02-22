package com.hxzk_bj_demo.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.utils.LanguageUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.squareup.leakcanary.LeakCanary;


import org.litepal.LitePalApplication;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatDelegate;
import butterknife.internal.Utils;
import okhttp3.OkHttpClient;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.hxzk_bj_demo.utils.LanguageUtil.getAppLocale;
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
       // LeakCanary.install(this);
        //正常程序初始化代码…
        //获取全局Context对象
        appContext = getApplicationContext();
        httpClientBuilder = new OkHttpClient.Builder();
        //初始化PersistenerCookiesJar开源库
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getAppContext()));
        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        //给OkttpClient添加开源库
        httpClientBuilder.cookieJar(cookieJar);


        //每次程序启动重新设置上次保存的theme
        if(getAppTheme()){
            //设置为夜间模式，可直接调用
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }else{
            //设置为白天模式
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }

//        String lan = LanguageUtil.getAppLanguage(getAppContext());
//        if(lan.equals("zh") || !lan.equals("en") ){
//            setLocale(appContext,Locale.SIMPLIFIED_CHINESE);
//        }else if(lan.equals("en") || !lan.equals("zh")){
//            setLocale(appContext,Locale.US);
//        }
        //注册Activity生命周期监听回调
        registerActivityLifecycleCallbacks(callbacks);

    }

    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            if (!isSameWithSetting(activity)) {
                String lan = LanguageUtil.getAppLanguage(activity);
                if(lan.equals("zh") || !lan.equals("en") ){
                    setLocale(appContext,Locale.SIMPLIFIED_CHINESE);
                }else if(lan.equals("en") || !lan.equals("zh")){
                    setLocale(appContext,Locale.US);
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

    public Context getApplication() {
        return this;
    }

    /**
     * 获取httpclient.builder对象实例
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClient() {
        if(httpClientBuilder == null){
            synchronized (MyApplication.class){
                httpClientBuilder =new OkHttpClient.Builder();
            }
        }
        return httpClientBuilder;
    }


    /**
     * 默认设置app模式
     */
    public static void setAppTheme(boolean model) {
        SPUtils.put(getAppContext(), "apptheme", model);
    }

    /**
     * Boolean有默认类型，是false 默认白天模式
     */
    public static boolean getAppTheme() {
        return (boolean) SPUtils.get(getAppContext(), "apptheme", false);
    }

}
