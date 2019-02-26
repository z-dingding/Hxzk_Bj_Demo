package com.hxzk_bj_demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.Const;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.utils.KeyBoardHelperUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${赵江涛} on 2017-12-26.
 * 作用:登录界面
 */

public class LoginActivity extends BaseBussActivity {

    private static final String TAG = "LoginActivity";


    @BindView(R.id.edt_account_login)
    EditText edt_Account_Login;
    @BindView(R.id.edt_pwd_login)
    EditText edt_Pwd_Login;
    @BindView(R.id.btn_loginin_login)
    Button btn_Loginin_Login;
    @BindView(R.id.tv_otherwaylogin_login)
    TextView tv_otherWayLogin;



    private int bottomHeight;
    private KeyBoardHelperUtil boardHelper;
    private View layoutBottom;
    private View layoutContent;




    @Override
    protected int setLayoutId() {
        _context = LoginActivity.this;
        isShowMenu = false;
        return R.layout.activity_login;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String account= (String) SPUtils.get(LoginActivity.this,Const.KEY_LOGIN_ACCOUNT,"account");
        String pwd= (String) SPUtils.get(LoginActivity.this,Const.KEY_LOGIN_PWD,"pwd");
        if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)){
            edt_Account_Login.setText(account);
            edt_Pwd_Login.setText(pwd);
        }
    }

    @Override
    protected void initView() {
        super.initView();


        initToolbar(R.drawable.back, getResources().getString(R.string.login));
        //为 Activity 指定 windowSoftInputMode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        layoutContent = findViewById(R.id.layout_content);
        layoutBottom = findViewById(R.id.layout_bottom);
        boardHelper = new KeyBoardHelperUtil(this);
        boardHelper.onCreate();
    }


    private KeyBoardHelperUtil.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelperUtil.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;
            if (bottomHeight > height) {
                layoutBottom.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - height;
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                        .getLayoutParams();
                lp.topMargin = offset;
                layoutContent.setLayoutParams(lp);
            }

        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != layoutBottom.getVisibility()) {
                layoutBottom.setVisibility(View.VISIBLE);
            }
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                layoutContent.setLayoutParams(lp);
            }

        }
    };

    @Override
    protected void initEvent() {
        super.initEvent();

        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        layoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = layoutBottom.getHeight();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
    }



    @OnClick({R.id.tv_otherwaylogin_login, R.id.btn_loginin_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_otherwaylogin_login:
                addActivityToManager(LoginActivity.this,OntherWayLoginActivity.class);
                break;
            case R.id.btn_loginin_login:
                mshowDialog(LoginActivity.this);
                if(edt_Account_Login.getText().toString().equals("xzt") &&edt_Pwd_Login.getText().toString().equals("xzt")){
                    SPUtils.put(LoginActivity.this, Const.KEY_LOGIN_ACCOUNT,edt_Account_Login.getText().toString());
                    SPUtils.put(LoginActivity.this,Const.KEY_LOGIN_PWD,edt_Pwd_Login.getText().toString());
                    edt_Pwd_Login.setText("111111111111111111111111");
                    btn_Loginin_Login.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mdismissDialog();
                            jumpFinishCurrentActivity(LoginActivity.this,MainActivity.class);
                        }
                    },3000);
                }else{
                    mdismissDialog();
                    ToastCustomUtil.showLongToast("请输入正确的账号密码!");
                }

                break;
        }
    }
}
