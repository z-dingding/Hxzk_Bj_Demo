package com.hxzk_bj_demo.utils;

import android.os.CountDownTimer;
import android.widget.Button;

import com.hxzk_bj_demo.R;


/**
 * Created by ${赵江涛} on 2017-7-17.
 * 作用:倒计时工具类,都是在UI线程中.
 */

public class CountDownTimerUtil extends CountDownTimer {
Button btn_GetVer_Main;

    /** *
     *
     * @param millisInFuture 总共多少秒
     * @param countDownInterval  间隔
     */
    public CountDownTimerUtil(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn_GetVer_Main=btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn_GetVer_Main.setBackgroundResource(R.drawable.selector_btn_bg_verification);
        btn_GetVer_Main.setText("重新发送("+(millisUntilFinished/1000)+")S");
        btn_GetVer_Main.setEnabled(false);
    }

    @Override
    public void onFinish() {
        btn_GetVer_Main.setText("重发随机登录码");
        btn_GetVer_Main.setBackgroundResource(R.drawable.selector_btn_bg_verification);
        btn_GetVer_Main.setEnabled(true);//如果手机号没有输入,验证码失去焦点
    }
}
