package com.hxzk_bj_demo.mvp.constract

import android.content.Context
import com.hxzk_bj_demo.javabean.PointRankBean
import com.hxzk_bj_demo.mvp.view.base.BaseView
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.BaseSubscriber
import rx.Observable

/**
 *作者：created by zjt on 2020/9/7
 *描述:
 *
 */
interface RankConstract  {
    interface  RankView : BaseView{
        override fun onShowLoading()
        override fun onHiddenLoading()
        override fun onFail(throwable: Throwable?)
        fun rankSuccess(jsonBean :BaseResponse<PointRankBean>)
    }

    interface  RankPresenter {
        fun rankListP(pageNum : Int,mContext : Context) :Observable<BaseResponse<PointRankBean>>
    }


    interface  RankModel{
        fun rankListApi(pageNum : Int,observable : BaseSubscriber<BaseResponse<PointRankBean>>) :Observable<BaseResponse<PointRankBean>>
    }
}