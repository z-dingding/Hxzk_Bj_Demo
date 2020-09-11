package com.hxzk_bj_demo.mvp.model

import com.hxzk_bj_demo.javabean.PointRankBean
import com.hxzk_bj_demo.mvp.constract.RankConstract
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import com.hxzk_bj_demo.network.HttpRequest
import rx.Observable

/**
 *作者：created by zjt on 2020/9/7
 *描述:
 *
 */
class RankModel : RankConstract.RankModel {
    override fun rankListApi(pageNum: Int, subscriber: BaseSubscriber<BaseResponse<PointRankBean>>) :Observable<BaseResponse<PointRankBean>>{
      val observable : Observable<BaseResponse<PointRankBean>>  = HttpRequest.getInstance().serviceInterface.rankListApi(pageNum)
        HttpRequest.getInstance().toSubscribe(observable,subscriber)
        return observable
    }

}