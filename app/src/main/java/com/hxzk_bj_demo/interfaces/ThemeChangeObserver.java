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
     * 刷新主题，注意这里刷新的主题都是没有再style中定义的，需要代码获取资源，然后赋值给控件
     * 如果是已经在style中定义的且控件在xml里引用了的，在首次setTheme时就自动赋值了
     */
    void notifyByThemeChanged();
}
