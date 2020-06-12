package com.hxzk_bj_demo.ui.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hxzk.bj.x5webview.statusbartextcolor.StatebusTextColorUtil
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.common.MainApplication
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver

/**
 *作者：created by hxzk on 2020/6/11
 *描述:
 *
 */
abstract class BaseKtActivity :AppCompatActivity() , ThemeChangeObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupActivityBeforeCreate()
        super.onCreate(savedInstanceState)
        if (setLayoutId() != 0) {
            setContentView(setLayoutId())
        }
        initView()
        initEvent()
        initData()
    }


    /**
     * 设置layout布局文件
     */
    protected abstract fun setLayoutId(): Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化监听事件
     */
    protected abstract fun initEvent()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 先将当前Activity注册，然后设置主题
     */
    open fun setupActivityBeforeCreate() {
        (application as MainApplication).registerObserver(this)
        loadingCurrentTheme()
    }


    @Override
    override fun loadingCurrentTheme() {
        //夜间模式

        //夜间模式
        if (MainApplication.getAppTheme()) {
            setTheme(R.style.Base_CustomTheme_Night)
            StatebusTextColorUtil.setStatusBarColor(this, R.color.custom_color_app_status_bg_night)
            StatebusTextColorUtil.setLightStatusBar(this, false)
        } else { //白天模式
            setTheme(R.style.Base_CustomTheme_Day)
            StatebusTextColorUtil.setStatusBarColor(this, R.color.custom_color_app_status_bg_day)
            StatebusTextColorUtil.setLightStatusBar(this, true)
        }


    }


}