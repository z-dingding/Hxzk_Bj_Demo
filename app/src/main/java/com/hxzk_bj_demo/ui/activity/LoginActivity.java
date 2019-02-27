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
import com.hxzk_bj_demo.javabean.LoginBean;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.utils.KeyBoardHelperUtil;
import com.hxzk_bj_demo.utils.Md5Utils;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

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

    //账号
    String account;
    //密码
    String pwd;

    /**Subscription 用于取消或添加订阅*/
    private Subscription mSubscription;

    @Override
    protected int setLayoutId() {
        _context = LoginActivity.this;
        isShowMenu = false;
        return R.layout.activity_login;
    }

    @Override
    protected void onStart() {
        super.onStart();
         account= (String) SPUtils.get(LoginActivity.this,Const.KEY_LOGIN_ACCOUNT,"");
         pwd= (String) SPUtils.get(LoginActivity.this,Const.KEY_LOGIN_PWD,"");
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
                account=edt_Account_Login.getText().toString();
                 pwd=edt_Pwd_Login.getText().toString();
                if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)){
                    SPUtils.put(LoginActivity.this, Const.KEY_LOGIN_ACCOUNT,account);
                    SPUtils.put(LoginActivity.this,Const.KEY_LOGIN_PWD,pwd);
                    //edt_Pwd_Login.setText("111111111111111111111111");

                    //每次开始新的请求前,先取消上一次请求的订阅关系，避免内存泄漏
                    HttpRequest.getInstance().unsubscribe();
                    Subscriber<LoginBean> subscriber =new  Subscriber<LoginBean>(){

                        @Override
                        public void onCompleted() {
                            mdismissDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastCustomUtil.showLongToast(e.getMessage());
                            mdismissDialog();
                        }

                        @Override
                        public void onNext(LoginBean loginBean) {
                            if(loginBean.getResult().equals("success")){
                                jumpFinishCurrentActivity(LoginActivity.this,MainActivity.class);
                            }else{
                               ToastCustomUtil.showLongToast(loginBean.getResMsg());
                            }
                        }
                    };

                    Observable<LoginBean> observable =HttpRequest.getInstance().getServiceInterface().login(account,Md5Utils.MD5(pwd),"1");
                    HttpRequest.getInstance().toSubscribe(observable,subscriber);

                }else{
                    mdismissDialog();
                    ToastCustomUtil.showLongToast("请输入正确的账号密码!");
                }

                break;
        }
    }
}
