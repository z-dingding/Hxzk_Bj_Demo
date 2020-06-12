package com.hxzk_bj_demo.mvp.presenter

import android.content.Context
import com.hxzk_bj_demo.javabean.IntegralListBean
import com.hxzk_bj_demo.mvp.constract.IntegralConstract
import com.hxzk_bj_demo.mvp.model.IntegralModel
import com.hxzk_bj_demo.mvp.presenter.base.BasePreseneter
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import com.hxzk_bj_demo.ui.activity.IntegralActivity
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil
import rx.Observable

/**
 *作者：created by hxzk on 2020/6/10
 *描述:
 *
 */
class IntegralPresener : BasePreseneter<IntegralActivity>(),IntegralConstract.IntegralPresenter{

    var mIntegralModel : IntegralModel = IntegralModel()
    lateinit var  subscriber: BaseSubscriber<BaseResponse<IntegralListBean>>

    override fun intergralListP(pageNum: Int,context :Context): Observable<BaseResponse<IntegralListBean>> {
      subscriber =object :BaseSubscriber<BaseResponse<IntegralListBean>>(context){
          override fun onShowLoading() {
             mView.onShowLoading()
          }

          override fun onHiddenLoading() {
              mView.onHiddenLoading()
          }

          override fun onResult(t: BaseResponse<IntegralListBean>) {
                  mView.onIngegralResult(t)
          }

          override fun onFail(e: Throwable) {
              mView.onFail(e)
          }

      }
        return mIntegralModel.integralListApi(pageNum,context,subscriber)
    }
}