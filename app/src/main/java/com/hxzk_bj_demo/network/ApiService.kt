package com.hxzk_bj_demo.network

import com.hxzk_bj_demo.javabean.SquareDataBean
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 *作者：created by zjt on 2020/9/15
 *描述:
 *
 */
interface ApiService {
    /**
     * 获取排行榜接口
     */
    @GET("user_article/list/{pageNum}/json")
    suspend fun squareDatasApi(@Path("pageNum") pageNum: Int): BaseResponse<SquareDataBean>
}