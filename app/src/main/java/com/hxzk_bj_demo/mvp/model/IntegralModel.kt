package com.hxzk_bj_demo.mvp.model

import android.content.Context
import com.hxzk_bj_demo.javabean.IntegralListBean
import com.hxzk_bj_demo.javabean.LoginOutBean
import com.hxzk_bj_demo.mvp.constract.IntegralConstract
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import com.hxzk_bj_demo.network.HttpRequest
import rx.Observable

/**
 *作者：created by hxzk on 2020/6/10
 *描述:
 *
 */
class IntegralModel : IntegralConstract.IntegalModel{

    lateinit var observable: Observable<BaseResponse<IntegralListBean>>

    override fun integralListApi(pageNum: Int,context : Context,subscriber : BaseSubscriber<BaseResponse<IntegralListBean>>): Observable<BaseResponse<IntegralListBean>> {
        observable = HttpRequest.getInstance().serviceInterface.inegralListApi(pageNum)
        HttpRequest.getInstance().toSubscribe<BaseResponse<IntegralListBean>>(observable, subscriber)
        return   observable
    }
}