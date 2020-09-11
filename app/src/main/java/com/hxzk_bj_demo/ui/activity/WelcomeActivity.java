package com.hxzk_bj_demo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxzk_bj_demo.R;

import com.hxzk_bj_demo.common.MainApplication;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import androidx.appcompat.app.AppCompatActivity;

import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;

/**
 * Created by Ding on 2017/12/24
 * 作用：欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityJump.AddToTack(WelcomeActivity.this);

        View view = View.inflate(this, R.layout.activity_welcome,null);
        //取消状态栏,充满全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
        initData();
    }



    private void initData() {
        //如果cookie为空，说明用户没有登录,可能是首次登录或者退出登录再次进入
        String cookies =(String) SPUtils.get(MainApplication.getAppContext(),KEY_COOKIE,"");
        if(TextUtils.isEmpty(cookies)){
            animation();
        }else{
            ActivityJump.NormalJumpAndFinish(WelcomeActivity.this, MainActivity.class);
        }

    }


    /**
     * 启动动画()
     */
    private void animation(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;


        TextView ivSui =findViewById(R.id.tv_animationone);
        TextView ivYi =findViewById(R.id.tv_animationtwo);
        ivSui.setVisibility(View.VISIBLE);
        ivYi.setVisibility(View.VISIBLE);
        //先从上到中间
        ObjectAnimator oa_suione = ObjectAnimator.ofFloat(ivSui,"TranslationY",-200,screenHeight/2);
        ObjectAnimator oa_yione = ObjectAnimator.ofFloat(ivYi,"TranslationY",-200,screenHeight/2);
        //以当前左上角为原点，继续执行
        ObjectAnimator oa_suitwo = ObjectAnimator.ofFloat(ivSui,"TranslationX",0,screenWidth/2+400);
        ObjectAnimator oa_yitwo = ObjectAnimator.ofFloat(ivYi,"TranslationX",0,-(screenWidth/2+400));

        AnimatorSet set =new AnimatorSet();
        //先执行oa_yione和oa_suione在执行oa_suitwo和oa_yitwo
        set.play(oa_suitwo).with(oa_yitwo).after(oa_yione).after(oa_suione);
        set.setDuration(2000);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ActivityJump.NormalJumpAndFinish(WelcomeActivity.this, LoginActivity.class);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }



    @Override
    protected void onStop() {
        super.onStop();
        ActivityJump.popSpecifiedActivity(WelcomeActivity.class);
    }
}
