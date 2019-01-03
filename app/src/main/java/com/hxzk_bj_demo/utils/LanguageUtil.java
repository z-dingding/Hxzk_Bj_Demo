package com.hxzk_bj_demo.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by ${赵江涛} on 2018-2-24.
 * 作用:
 */

public class LanguageUtil {


    /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public static void set(boolean isEnglish, Activity mActivity) {

        Configuration configuration =mActivity.getResources().getConfiguration();
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        mActivity.getResources().updateConfiguration(configuration, displayMetrics);
        mActivity.recreate();
    }



}
