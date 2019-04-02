package com.hxzk_bj_demo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 作者：created by ${zjt} on 2019/4/2
 * 描述:获取屏幕宽高
 */
public class ScreenUtil {
    public static final String WIDTH = "width";// 宽度常量
    public static final String HEIGHT = "height";// 高度常量

    private WindowManager windowManager;
    private DisplayMetrics metrics = new DisplayMetrics();

    public ScreenUtil(Context context) {
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取手机屏幕的宽度，单位px
     * @param s 宽或高类型
     * @return
     */
    public int getScreenSizePX(String s) {
        // 获取显示度量，该显示度量描述了显示的大小和密度
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int w_or_h = 0;
        switch (s){
            case WIDTH://宽度
                w_or_h = metrics.widthPixels;
                break;
            case HEIGHT:// 高度
                w_or_h = metrics.heightPixels;
                break;
        }
        return w_or_h;// 返回手机屏幕的宽度或者高度
    }


    /**
     * 获取手机屏幕的宽度，单位Dp
     * @param s 宽或高类型
     * @return
     */
    public int getScreenSizeDP(String s) {
        // 获取显示度量，该显示度量描述了显示的大小和密度
        windowManager.getDefaultDisplay().getMetrics(metrics);
        //屏幕密度 3.0,density值表示每英寸有多少个显示点，与分辨率是两个概念。HVGA屏density=160；QVGA屏density=120
        float density =metrics.density;
        //屏幕密度Dpi 480,
        int desityDpi=metrics.densityDpi;
        int w_or_h = 0;
        switch (s){
            case WIDTH://宽度 360,640
                w_or_h = (int) (metrics.widthPixels/density);
                break;
            case HEIGHT:// 高度
                w_or_h = (int) (metrics.heightPixels/density);
                break;
        }
        return w_or_h;// 返回手机屏幕的宽度或者高度
    }
}
