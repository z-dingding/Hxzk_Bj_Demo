package com.hxzk_bj_demo.mvp.presenter;

import android.content.Context;

import com.hxzk_bj_demo.javabean.IntegralBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.mvp.constract.MainConstract;
import com.hxzk_bj_demo.mvp.model.MainModel;
import com.hxzk_bj_demo.mvp.presenter.base.BasePreseneter;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.network.interceptor.AddInterceptor;
import com.hxzk_bj_demo.ui.activity.LoginActivity;
import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.umeng.analytics.MobclickAgent;

import rx.Observable;
import rx.Subscriber;

import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;

/**
 * 作者：created by hxzk on 2020/6/2
 * 描述:
 */
public class MainPresenter extends BasePreseneter<MainActivity> implements MainConstract.MainPresenter {

    MainModel mainModel;
    Context mContext;

    public MainPresenter(Context context) {
      mainModel =new MainModel();
      this.mContext=context;

    }

    @Override
    public Observable<BaseResponse<LoginOutBean>> loginOutP() {
        Observable<BaseResponse<LoginOutBean>> observable =mainModel.loginOutApi();
        Subscriber<BaseResponse<LoginOutBean>>  subscriber = new BaseSubscriber<BaseResponse<LoginOutBean>>(mContext) {

            @Override
            public void onShowLoading() {
            mView.onShowLoading();
            }

            @Override
            public void onHiddenLoading() {
                mView.onHiddenLoading();
            }

            @Override
            public void onResult(BaseResponse<LoginOutBean> baseResponse) {
                mView.onLoginoutResult(baseResponse);
            }

            @Override
            public void onFail(Throwable e) {
                mView.onFail(e);
            }


        };
        HttpRequest.getInstance().toSubscribe(observable, subscriber);
        return observable;
    }

    @Override
    public Observable<BaseResponse<IntegralBean>> integralP() {
        Observable<BaseResponse<IntegralBean>> observable =mainModel.integralApi();
        Subscriber<BaseResponse<IntegralBean>>  subscriber = new BaseSubscriber<BaseResponse<IntegralBean>>(mContext) {

            @Override
            public void onShowLoading() {
                mView.onShowLoading();
            }

            @Override
            public void onHiddenLoading() {
                mView.onHiddenLoading();
            }

            @Override
            public void onResult(BaseResponse<IntegralBean> baseResponse) {
                mView.onIntegralResult(baseResponse);
            }

            @Override
            public void onFail(Throwable e) {
                mView.onFail(e);
            }


        };
        HttpRequest.getInstance().toSubscribe(observable, subscriber);
        return observable;
    }
}
