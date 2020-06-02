package com.hxzk_bj_demo.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.common.Const;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.ui.activity.base.BaseMvpActivity;
import com.hxzk_bj_demo.mvp.constract.LoginConstract;
import com.hxzk_bj_demo.mvp.presenter.LoginPreseneter;
import com.hxzk_bj_demo.utils.KeyBoardHelperUtil;
import com.hxzk_bj_demo.utils.MarioResourceHelper;
import com.hxzk_bj_demo.utils.ProgressDialogUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${赵江涛} on 2017-12-26.
 * 作用:登录界面
 */

public class LoginActivity extends BaseMvpActivity<LoginPreseneter> implements LoginConstract.LoginView {

    private static final String TAG = "LoginActivity";


    @BindView(R.id.edt_account_login)
    EditText edt_Account_Login;
    @BindView(R.id.edt_pwd_login)
    EditText edt_Pwd_Login;
    @BindView(R.id.btn_loginin_login)
    Button btn_Loginin_Login;
    @BindView(R.id.tv_otherwaylogin_login)
    TextView tv_otherWayLogin;
    @BindView(R.id.tv_register_login)
    TextView tv_rigister_login;


    private int bottomHeight;
    private KeyBoardHelperUtil boardHelper;
    private View layoutBottom;
    /**
     * 根布局View
     */
    private LinearLayout layoutContent;


    /**
     * 账号
     */
    String account;
    /**
     * 密码
     */
    String pwd;


    @Override
    protected int setLayoutId() {
        _context = LoginActivity.this;
        isShowMenu = false;
        return R.layout.activity_login;
    }


    @Override
    protected void initView() {
        super.initView();
        layoutContent = findViewById(R.id.rootLinear_login);
        initToolbar(R.drawable.back, getResources().getString(R.string.login));
        layoutBottom = findViewById(R.id.layout_bottom);

        //初始化软键盘工具类
        boardHelper = new KeyBoardHelperUtil(this);
        boardHelper.onCreate();
    }

    @Override
    protected void initEvent() {
        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        //获取底部占位空白区域的高度,必须在子线程中获取,否则为0
        layoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = layoutBottom.getHeight();
            }
        });


    }

    @Override
    protected void initData() {
        presenter = new LoginPreseneter(LoginActivity.this);
        presenter.onAttachView(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        account = (String) SPUtils.get(LoginActivity.this, Const.KEY_LOGIN_ACCOUNT, "");
        pwd = (String) SPUtils.get(LoginActivity.this, Const.KEY_LOGIN_PWD, "");
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
            edt_Account_Login.setText(account);
            edt_Pwd_Login.setText(pwd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardHelper.onDestory();
    }

    @Override
    public void notifyByThemeChanged() {
        MarioResourceHelper helper = MarioResourceHelper.getInstance(LoginActivity.this);
        helper.setBackgroundResourceByAttr(mRootLinear, R.attr.custom_attr_app_bg);
        helper.setBackgroundResourceByAttr(mToolbar, R.attr.custom_attr_app_toolbar_bg);
        helper.setBackgroundResourceByAttr(statebarView, R.attr.custom_attr_app_statusbar_bg);
        helper.setBackgroundResourceByAttr(btn_Loginin_Login, R.attr.custom_attr_app_btn_bg);
        int color = helper.getColorByAttr(R.attr.custom_attr_app_textcolor);
        mToolbar.setTitleTextColor(color);
        edt_Account_Login.setTextColor(color);
        edt_Pwd_Login.setTextColor(color);
        tv_otherWayLogin.setTextColor(color);
        tv_rigister_login.setTextColor(color);
    }

    private KeyBoardHelperUtil.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelperUtil.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;
            if (bottomHeight > height) {
                layoutBottom.setVisibility(View.GONE);
            } else {
                //计算结果存在偏差就加了个56px
                int offset = bottomHeight - height - 56;
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

    @OnClick({R.id.tv_otherwaylogin_login, R.id.btn_loginin_login, R.id.tv_register_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_otherwaylogin_login:
                ActivityJump.NormalJump(LoginActivity.this, OntherWayLoginActivity.class);
                break;
            case R.id.tv_register_login:
                ActivityJump.NormalJump(LoginActivity.this, RegisterActivity.class);
                break;

            case R.id.btn_loginin_login:
                account = edt_Account_Login.getText().toString();
                pwd = edt_Pwd_Login.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
                    presenter.login(account, pwd);
                } else {
                    ToastCustomUtil.showLongToast("请输入正确的账号密码!");
                }
                break;
            default:
        }
    }


    @Override
    public void onShowLoading() {
        ProgressDialogUtil.getInstance().mshowDialog(LoginActivity.this);
    }

    @Override
    public void onHiddenLoading() {
        ProgressDialogUtil.getInstance().mdismissDialog();
    }

    @Override
    public void onFail(Throwable throwable) {
        ToastCustomUtil.showLongToast(throwable.getMessage());
    }

    @Override
    public void onResult(BaseResponse<LoginOutBean> loginBean) {
        SPUtils.put(LoginActivity.this, Const.KEY_LOGIN_ACCOUNT, account);
        SPUtils.put(LoginActivity.this, Const.KEY_LOGIN_PWD, pwd);
        //当用户使用自有账号登录时，可以这样统计：
        MobclickAgent.onProfileSignIn(account);
        ActivityJump.NormalJumpAndFinish(LoginActivity.this, MainActivity.class);
    }
}
