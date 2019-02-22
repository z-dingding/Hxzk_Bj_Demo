package com.hxzk_bj_demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.ui.activity.WelcomeActivity;

import java.util.Locale;

import butterknife.internal.Utils;

import static com.hxzk_bj_demo.common.Const.KEY_APP_LANGUAGE;

/**
 * Created by ${赵江涛} on 2018-2-24.
 * 作用:
 */

public class LanguageUtil {



    public static boolean isSameWithSetting(Context context) {
        String lang = context.getResources().getConfiguration().locale.getLanguage();
        return lang.equals(getAppLanguage(context));
    }


    /**
     * 刷新语言
     * @param context
     * @param myLocale
     */
    public static void setLocale(Context context,Locale myLocale){
       String lang=myLocale.getLanguage();
        //获得res资源对象
        Resources res = context.getResources();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = res.getDisplayMetrics();
        // 获得设置对象
        Configuration conf = res.getConfiguration();
        //区别17版本（其实在17以上版本通过 config.locale设置也是有效的，不知道为什么还要区别）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(myLocale);//设置简体中文
        } else {
            conf.locale = myLocale;
        }
        res.updateConfiguration(conf, dm);
        saveLanguageSetting(context,myLocale);

        ((Activity)context).recreate();
    }




    /**
     *   App语言持久化,将选择的语言类型保存，下次进入app依然有效
     */
    public static void saveLanguageSetting(Context context, Locale locale){
        SPUtils.put(context,KEY_APP_LANGUAGE,locale.getLanguage());
    }

    /**
     * 获取当前系统所使用的语言，中文和英文
     * @param context
     * @return
     */
    public static String getAppLanguage(Context context){
        return (String)SPUtils.get(context,KEY_APP_LANGUAGE,Locale.getDefault().getLanguage());
    }



    public static Locale getAppLocale(Context context){
        Locale locale = null;
        String lang = (String)SPUtils.get(context,KEY_APP_LANGUAGE,Locale.getDefault().getLanguage());
        if(!lang.equals(Locale.SIMPLIFIED_CHINESE.getLanguage())&&!lang.equals(Locale.ENGLISH.getLanguage())){
            locale = Locale.SIMPLIFIED_CHINESE;
        }else if(lang.equals(Locale.SIMPLIFIED_CHINESE.getLanguage())){
            locale = Locale.SIMPLIFIED_CHINESE;
        }else if(lang.equals(Locale.US.getLanguage())){
            locale =Locale.US;
        }
      
        return locale;
    }






}
