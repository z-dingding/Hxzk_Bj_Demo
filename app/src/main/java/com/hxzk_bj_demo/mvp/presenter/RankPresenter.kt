package com.hxzk_bj_demo.mvp.presenter

import android.content.Context
import com.hxzk_bj_demo.javabean.PointRankBean
import com.hxzk_bj_demo.mvp.constract.RankConstract
import com.hxzk_bj_demo.mvp.model.RankModel
import com.hxzk_bj_demo.mvp.presenter.base.BasePreseneter
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import com.hxzk_bj_demo.ui.activity.RankActivity
import rx.Observable

/**
 *作者：created by zjt on 2020/9/7
 *描述:
 *
 */
class RankPresenter(val  rankModel :RankModel): BasePreseneter<RankActivity>() ,RankConstract.RankPresenter {

    override fun rankListP(pageNum: Int,mContext :Context): Observable<BaseResponse<PointRankBean>> {

       val subscriber : BaseSubscriber<BaseResponse<PointRankBean>> = object :BaseSubscriber<BaseResponse<PointRankBean>>(mContext){
           override fun onShowLoading() {
               mView.onShowLoading()
           }

           override fun onHiddenLoading() {
              mView.onHiddenLoading()
           }

           override fun onResult(t: BaseResponse<PointRankBean>?) {
               if (t != null) {
                   mView.rankSuccess(t)
               }
           }

           override fun onFail(e: Throwable?) {
               mView.onFail(e)
           }

       }
        return  rankModel.rankListApi(pageNum,subscriber)
    }
}