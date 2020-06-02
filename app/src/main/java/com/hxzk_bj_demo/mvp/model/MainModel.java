package com.hxzk_bj_demo.mvp.model;

import com.hxzk_bj_demo.javabean.IntegralBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.mvp.constract.MainConstract;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.HttpRequest;

import rx.Observable;

/**
 * 作者：created by hxzk on 2020/6/2
 * 描述:
 */
public class MainModel implements MainConstract.MainModel {
    @Override
    public Observable<BaseResponse<LoginOutBean>> loginOutApi() {
        return HttpRequest.getInstance().getServiceInterface().loginout();
    }

    @Override
    public Observable<BaseResponse<IntegralBean>> integralApi() {
        return HttpRequest.getInstance().getServiceInterface().integralApi();
    }
}
