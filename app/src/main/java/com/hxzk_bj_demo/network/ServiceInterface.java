package com.hxzk_bj_demo.network;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.javabean.InversBean;
import com.hxzk_bj_demo.javabean.LoginBean;

import org.json.JSONObject;

import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 注册接口
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    Observable<JsonObject> rigister(@Query("username") String username , @Query("password") String password, @Query("repassword") String repassword);




    /**
     * 登录接口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("user/login")
    Observable<JsonObject> login(@Query("username") String username , @Query("password") String password);


    /**
     * 退出登录接口
     * @return
     */
    @GET("user/logout/json")
    Observable<JsonObject> loginout();



}
