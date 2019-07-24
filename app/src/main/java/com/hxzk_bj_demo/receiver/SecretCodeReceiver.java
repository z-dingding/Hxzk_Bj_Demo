package com.hxzk_bj_demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.ui.activity.WelcomeActivity;

import static android.provider.Telephony.Sms.Intents.SECRET_CODE_ACTION;

/**
 * 作者：created by ${zjt} on 2019/7/15
 * 描述:
 */
public class SecretCodeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && SECRET_CODE_ACTION.equals(intent.getAction())){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setClass(context, WelcomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
