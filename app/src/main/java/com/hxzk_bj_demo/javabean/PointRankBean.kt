package com.hxzk_bj_demo.javabean

/**
 *作者：created by zjt on 2020/9/11
 *描述:
 *
 */


data class PointRankBean(
    val curPage: Int,
    val datas: List<Data>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Data(
    val coinCount: Int,
    val level: Int,
    val rank: String,
    val userId: Int,
    val username: String
)