package com.hxzk_bj_demo.network;

import com.hxzk_bj_demo.javabean.InversBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:
 */

public interface ServiceInterface {


    //检查权限的接口

   // http://192.168.1.112:8080/ygcy/app/appcallAction!getSjListJsonTest.do?
   // entinfoqueryBean.pn=1  页数
   // &entinfoqueryBean.pageSize=20  条目
   // &isYgcy=1 //是否是阳光餐饮企业
   // &entinfoqueryBean.lat=39.625   纬度
   // &entinfoqueryBean.lng=119.632 经度
   //@GET("app/appcallAction!getSjListJson.do")

    @GET("app/appcallAction!getSjListJsonTest.do")
    Observable<InversBean> getRefreshData(@Query("entinfoqueryBean.pn") String pageNum , @Query("entinfoqueryBean.pageSize") String itemNum, @Query("isYgcy") String isYgcy , @Query("entinfoqueryBean.lat") String entinfoqueryBean_lat , @Query("entinfoqueryBean.lng") String entinfoqueryBean_lng);




}
