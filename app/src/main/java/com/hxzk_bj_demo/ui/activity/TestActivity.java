package com.hxzk_bj_demo.ui.activity;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.BannerBean;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseActivity;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 作者：created by ${zjt} on 2019/4/17
 * 描述:
 */
public class TestActivity extends BaseActivity {

    Observable<JsonObject> observable;
    Subscriber<JsonObject> subscriber;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        subscriber = new Subscriber<JsonObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());
            }

            @Override
            public void onNext(JsonObject baseResponse) {
               String s  = baseResponse.toString();
            }
        };

        Observable<JsonObject> observable = HttpRequest.getInstance().getServiceInterface().testMethod();
        //用observable提供的onErrorResumeNext 则可以将你自定义的Func1 关联到错误处理类中
        //observable.onErrorResumeNext(new BaseSubscriber.HttpResponseFunc<>());
        HttpRequest.getInstance().toSubscribe(observable, subscriber);
    }

    @Override
    public void notifyByThemeChanged() {

    }
}
