package com.hxzk_bj_demo.network;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.javabean.InversBean;
import com.hxzk_bj_demo.javabean.LoginBean;

import org.json.JSONObject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:接口
 */

public interface ServiceInterface {


    @GET("app/appcallAction!getSjListJsonTest.do")
    Observable<InversBean> getRefreshData(@Query("entinfoqueryBean.pn") String pageNum , @Query("entinfoqueryBean.pageSize") String itemNum, @Query("isYgcy") String isYgcy , @Query("entinfoqueryBean.lat") String entinfoqueryBean_lat , @Query("entinfoqueryBean.lng") String entinfoqueryBean_lng);


    /**
     * 登录接口
     * @param account 用户名
     * @param pwd 密码
     * @param type 登录类型,普通登录为1，其他如微信登录为0
     * @return
     */
    @GET("app/appUserAction!getUserLogin.do")
    Observable<LoginBean> login(@Query("ygAppUser.username") String account , @Query("eygAppUser.password") String pwd, @Query("ygAppUser.usertype") String type);


    /**
     * 退出登录接口
     * @return
     */
    @GET("app/appUserAction!logout.do")
    Observable<JsonObject> loginout();


}
