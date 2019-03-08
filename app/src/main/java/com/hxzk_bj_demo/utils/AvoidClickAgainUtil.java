package com.hxzk_bj_demo.utils;

/**
短时间内防止二次点击
*/

public final class AvoidClickAgainUtil {

    private AvoidClickAgainUtil() {

    }

    private static long mLastClickTime;// 最后一次点击按钮的时间
	private static long mTimeInterval = 3000;//自定义允许再次点击的时间间隔


    /**
     * 判断短时间是否再次点击
     * true 是短时间点击了
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - mLastClickTime) < mTimeInterval) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }
}