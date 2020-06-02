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

    public static final String  sConstant ="cons";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityJump.AddToTack(WelcomeActivity.this);

        View view = View.inflate(this, R.layout.activity_welcome,null);
        //取消状态栏,充满全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        String data = getIntent().getStringExtra("type");
        if(!TextUtils.isEmpty(data)){
            ToastCustomUtil.showLongToast(data);
        }

        initData();
    }



    private void initData() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;


        TextView ivSui =findViewById(R.id.tv_animationone);
        TextView ivYi =findViewById(R.id.tv_animationtwo);
        ivSui.setVisibility(View.VISIBLE);
        ivYi.setVisibility(View.VISIBLE);
        ObjectAnimator oa_suione = ObjectAnimator.ofFloat(ivSui,"TranslationY",-200,screenHeight/2);
        ObjectAnimator oa_suitwo = ObjectAnimator.ofFloat(ivSui,"TranslationX",0,screenWidth/2+400);
        ObjectAnimator oa_yione = ObjectAnimator.ofFloat(ivYi,"TranslationY",-200,screenHeight/2);
        ObjectAnimator oa_yitwo = ObjectAnimator.ofFloat(ivYi,"TranslationX",0,-(screenWidth/2+400));

        AnimatorSet set =new AnimatorSet();
        set.play(oa_suitwo).with(oa_yitwo).after(oa_yione).after(oa_suione);
        set.setDuration(1000);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                String cookies =(String) SPUtils.get(MainApplication.getAppContext(),KEY_COOKIE,"");
                if(!TextUtils.isEmpty(cookies)){
                    ActivityJump.NormalJumpAndFinish(WelcomeActivity.this, MainActivity.class);
                }else{
                    Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    animNext();
                    finish();
                }


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
