package com.hxzk_bj_demo.mvp.constract

import android.content.Context
import com.hxzk_bj_demo.javabean.IntegralBean
import com.hxzk_bj_demo.javabean.IntegralListBean
import com.hxzk_bj_demo.javabean.LoginOutBean
import com.hxzk_bj_demo.mvp.view.base.BaseView
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import io.reactivex.disposables.Disposable
import rx.Observable
import java.util.*

/**
 *作者：created by hxzk on 2020/6/10
 *描述:
 *
 */
interface IntegralConstract {

    interface IntegralView : BaseView {
        override fun onShowLoading()
        override fun onHiddenLoading()
        override fun onFail(throwable: Throwable)
        fun onIngegralResult(loginBean: BaseResponse<IntegralListBean>)
    }

    interface IntegalModel{
       fun integralListApi(pageNum : Int,context : Context, subscriber: BaseSubscriber<BaseResponse<IntegralListBean>>) : Observable<BaseResponse<IntegralListBean>>
    }

    interface IntegralPresenter{
        fun intergralListP(pageNum : Int,context : Context) :Observable<BaseResponse<IntegralListBean>>
    }
}