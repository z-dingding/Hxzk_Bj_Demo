package com.hxzk_bj_demo.ui.mvpbase.model;

import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.mvpbase.constract.LoginConstract;

import rx.Observable;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public class LoginModel implements LoginConstract.loginModel {



    @Override
    public Observable<BaseResponse<LoginOutBean>> login(String account, String pwd) {
        return HttpRequest.getInstance().getServiceInterface().login(account, pwd);
    }
}
