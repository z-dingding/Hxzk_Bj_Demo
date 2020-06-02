package com.hxzk_bj_demo.mvp.constract;

import com.hxzk_bj_demo.javabean.IntegralBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.mvp.view.base.BaseView;
import com.hxzk_bj_demo.network.BaseResponse;

import rx.Observable;

/**
 * 作者：created by hxzk on 2020/6/2
 * 描述:
 */
public interface MainConstract {

    interface MainView extends BaseView {
        @Override
        void onShowLoading();

        @Override
        void onHiddenLoading();

        @Override
        void onFail(Throwable throwable);

        void onLoginoutResult(BaseResponse<LoginOutBean> loginBean);

        void onIntegralResult(BaseResponse<IntegralBean> integralBeanBaseResponse);
    }

    interface MainPresenter{
        Observable<BaseResponse<LoginOutBean>> loginOutP();
        Observable<BaseResponse<IntegralBean>> integralP();
    }


    interface MainModel{
        Observable<BaseResponse<LoginOutBean>> loginOutApi();
        Observable<BaseResponse<IntegralBean>> integralApi();
    }
}
