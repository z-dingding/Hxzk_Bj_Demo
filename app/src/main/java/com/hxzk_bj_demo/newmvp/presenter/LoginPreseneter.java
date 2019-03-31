package com.hxzk_bj_demo.newmvp.presenter;

import android.content.Context;

import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.newmvp.base.BasePreseneter;
import com.hxzk_bj_demo.newmvp.constract.LoginConstract;
import com.hxzk_bj_demo.newmvp.model.LoginModel;
import com.hxzk_bj_demo.ui.activity.LoginActivity;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import rx.Observable;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public class LoginPreseneter extends BasePreseneter<LoginActivity> implements LoginConstract.loginPreseneter {


    LoginModel loginModel;
    Context mContext;


    BaseSubscriber<BaseResponse<LoginOutBean>> subscriber;
    Observable<BaseResponse<LoginOutBean>> observable;

    public LoginPreseneter(Context context ) {
        this.mContext=context;
        loginModel =new LoginModel();
    }

    @Override
    public void login(String name, String pwd) {

        observable=loginModel.login(name,pwd);
        subscriber = new BaseSubscriber<BaseResponse<LoginOutBean>>(mContext) {

            @Override
            public void onShowLoading() {

            }

            @Override
            public void onHiddenLoading() {

            }


            @Override
            public void onResult(BaseResponse<LoginOutBean> mBaseResponse) {
                if (!mBaseResponse.isOk()) {
                    ToastCustomUtil.showLongToast(mBaseResponse.getMsg());
                }
                mView.onResult(mBaseResponse);
            }
            @Override
            public void onFail(Throwable e) {
                mView.onFail(e);
            }

        };
        HttpRequest.getInstance().toSubscribe(observable, subscriber);
    }
}
