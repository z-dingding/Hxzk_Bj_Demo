package com.hxzk_bj_demo.interfaces;

/**
 * 创建接口监听主题变化
 */

public interface ThemeChangeObserver {

    /**
     * 加载当前主题
     */
    void loadingCurrentTheme();

    /**
     * 刷新主题
     */
    void notifyByThemeChanged();
}
