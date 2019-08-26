package com.hxzk_bj_demo.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by ${赵江涛} on 2017-12-25.
 * 作用:软键盘帮助类
 * 作者：leandom
 * 链接：https://www.jianshu.com/p/9eb57a8ff5e5
 */

public class KeyBoardHelperUtil {

    private Activity activity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private int screenHeight;

    /**
     * 软键盘高度
     * 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
     */
    private int blankHeight = 0;

    public KeyBoardHelperUtil(Activity activity) {
        this.activity = activity;
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        ////当输入法显示时，允许窗口重新计算尺寸，使内容不被输入法所覆盖。
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //如果不是竖屏切换为竖屏
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onCreate() {
        //android.R.id.content 提供了视图的根元素，是一个FrameLayout，
        // 只有一个子元素，就是平时在onCreate方法中设置的setContentView
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onDestory() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            //获取App区域宽高信息对象(不包括状态栏)
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            //rect.bottom应该是可视区域高度
            //屏幕高度-可视高度(不包括invisiable) =理想状态下底部空白区域的高度(软键盘高度)
            int newBlankheight = screenHeight - rect.bottom;
            if (newBlankheight != blankHeight) {
                if (newBlankheight > blankHeight) {
                    // 软键盘弹出
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardPop(newBlankheight);
                    }
                } else {
                    // 软键盘关闭
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardClose(blankHeight);
                    }
                }
            }
            blankHeight = newBlankheight;
        }
    };

    public void setOnKeyBoardStatusChangeListener(
            OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void OnKeyBoardPop(int keyBoardheight);

        void OnKeyBoardClose(int oldKeyBoardheight);
    }


}
