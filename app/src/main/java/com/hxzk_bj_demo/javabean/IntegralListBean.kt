package com.hxzk_bj_demo.javabean

/**
 *作者：created by hxzk on 2020/6/9
 *描述:
 *
 */
data class IntegralListBean(
//    val `data`: Data,
//    val errorCode: Int,
//    val errorMsg: String
//)
//
//data class Data(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class DataX(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)