package com.hxzk_bj_demo.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.utils.ActivityJump;
import com.hxzk_bj_demo.utils.CountDownTimerUtil;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import java.util.Random;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：created by ${zjt} on 2019/1/3
 * 描述:
 */
public class OntherWayLoginActivity extends BaseBussActivity {

    public static final int NOTIFY_ID = 100; // NotifyCationId,类似于唯一标识,确定是不是同一个Notification,如果两个Notification用一个，则显示一个Notifycatino。


    @BindView(R.id.et_verification_otherwaylogin)
    EditText etVerification;
    @BindView(R.id.btn_getverification)
    Button btnGetverification;
    @BindView(R.id.btn_login)
    Button btnLogin;


    CountDownTimerUtil countDownTimerUtil;

    String verificationCode;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_otherwaylogin;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar(R.drawable.back, "随机码登录");
        countDownTimerUtil = new CountDownTimerUtil(60000, 1000, btnGetverification);

    }

    @OnClick({R.id.btn_getverification, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getverification:
                countDownTimerUtil.start();
                verificationCode=getVerificationCode();
                sendNotify(verificationCode);
                break;
            case R.id.btn_login:

                if(!TextUtils.isEmpty(etVerification.getText())){
                    String verification=etVerification.getText().toString();
                    if(verification.equals(verificationCode)){
                        countDownTimerUtil.cancel();
                        jumpFinishCurrentActivity(OntherWayLoginActivity.this,MainActivity.class);
                        ActivityJump.finnishAllActivitys();
                    }else{
                        ToastCustomUtil.showLongToast("请输入正确的随机登录码");
                    }
                }else{
                    ToastCustomUtil.showLongToast("请输入随机登录码");
                }
                break;
        }
    }

    /**
     * 获取随机验证码
     * @return
     */
    public String getVerificationCode(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++) {
            result += random.nextInt(10);
        }
            return result;
    }

    /**
     * 发送通知，模拟短信发送
     * @param verificationCode
     */
    public void sendNotify(String verificationCode){
        // 创建通知栏管理工具
        NotificationManager barmanage = (NotificationManager)this 
                .getSystemService(NOTIFICATION_SERVICE);
        // 实例化通知栏构造器
        Notification.Builder builder = new Notification.Builder(
                OntherWayLoginActivity.this);
        builder.setTicker("您有一条信息,待查收")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                // 添加震动提醒
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle("随意App").setContentText("您的随机登录码是"+verificationCode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 需要注意build()是在API level
            // 16(4.1)及之后增加的，在API11(3.0)中可以使用getNotificatin()来代替
            Notification notification = builder
                    .getNotification();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            barmanage.notify(NOTIFY_ID, notification);

        }
    }
}
