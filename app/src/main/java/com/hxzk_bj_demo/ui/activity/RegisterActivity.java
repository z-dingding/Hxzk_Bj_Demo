package com.hxzk_bj_demo.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustom;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 作者：created by ${zjt} on 2019/2/27
 * 描述：注册页面
 */
public class RegisterActivity extends BaseBussActivity {


    @BindView(R.id.tiet_accoount)
    TextInputEditText tietAccoount;
    @BindView(R.id.tiet_pwd)
    TextInputEditText tietPwd;
    @BindView(R.id.tiet_confirmpwd)
    TextInputEditText tietConfirmpwd;
    @BindView(R.id.btn_register)
    Button btnRegister;


    String sAccount;
    String sPwd;
    String sConfirmPwd;


    Subscriber<JsonObject> subscriber;
    Observable<JsonObject> observable;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar(R.drawable.back, getResources().getString(R.string.rigiser));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().unsubscribe(observable);
    }


    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        sAccount = tietAccoount.getText().toString();
        sPwd = tietPwd.getText().toString();
        sConfirmPwd = tietConfirmpwd.getText().toString();
        if (!TextUtils.isEmpty(sAccount) && !TextUtils.isEmpty(sPwd) && !TextUtils.isEmpty(sConfirmPwd)) {
            if (sPwd.equals(sConfirmPwd)) {
                if (sAccount.length() < 6 || sAccount.length() > 16) {
                    ToastCustomUtil.showLongToast(getString(R.string.toast_accountlength));
                } else if (sConfirmPwd.length() < 6 || sConfirmPwd.length() > 16) {
                    ToastCustomUtil.showLongToast(getString(R.string.toast_pwdlength));
                } else {

                    subscriber = new Subscriber<JsonObject>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastCustomUtil.showLongToast(e.getMessage());
                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            try {
                                JSONObject dataJsonObject = new JSONObject(jsonObject.toString());
                                String errorCode = dataJsonObject.getString("errorCode");
                                String errorMsg = dataJsonObject.getString("errorMsg");
                                if (!errorCode.equals("0")) {
                                    ToastCustomUtil.showLongToast(errorMsg);
                                } else {
                                    ActivityJump.NormalJumpAndFinish(RegisterActivity.this, LoginActivity.class);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    observable = HttpRequest.getInstance().getServiceInterface().rigister(sAccount, sPwd, sConfirmPwd);
                    HttpRequest.getInstance().toSubscribe(observable, subscriber);
                }

            } else {
                ToastCustomUtil.showLongToast(getString(R.string.toast_pwdnotequel));
            }


        } else {
            ToastCustomUtil.showLongToast(getResources().getString(R.string.content_is_empty));
        }
    }
}
