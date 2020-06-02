package com.hxzk_bj_demo.mvp.constract;

import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.mvp.view.base.BaseView;

import rx.Observable;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public interface LoginConstract {

    interface LoginView extends BaseView {
        @Override
        void onShowLoading();

        @Override
        void onHiddenLoading();

        @Override
        void onFail(Throwable throwable);

        void onResult(BaseResponse<LoginOutBean> loginBean);
    }


    interface  loginModel{
        Observable<BaseResponse<LoginOutBean>> login(String name ,String pwd);
    }


    interface loginPreseneter{
        void login(String name ,String pwd);
    }


}
