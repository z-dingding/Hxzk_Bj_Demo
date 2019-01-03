package com.hxzk_bj_demo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.utils.ActivityJump;

/**
 * Created by Ding on 2017/12/24
 * 作用：欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity{

    public static final String  sConstant ="cons";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityJump.AddToTack(WelcomeActivity.this);

        View view = View.inflate(this, R.layout.activity_welcome,null);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        initData(view);
    }



    private void initData(View view) {

        ObjectAnimator objectAnim1 = ObjectAnimator.ofFloat(view,"alpha",0.1f,1.0f);
        //ObjectAnimator objectAnim2 = ObjectAnimator.ofFloat(view,"TranslationX",0,1200);
        AnimatorSet set =new AnimatorSet();
        set.playSequentially(objectAnim1);
        //set.playSequentially(objectAnim2);
        set.setDuration(3000);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                animNext();
                finish();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }


    /**
     * @Desc 页面跳转动画
     */

    public void animNext() {
        /**<<<------右入左出*/
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //关闭欢迎界面
        ActivityJump.popSpecifiedActivity(WelcomeActivity.class);
    }
}
