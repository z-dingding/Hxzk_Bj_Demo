package com.hxzk_bj_demo.network;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.javabean.BannerBean;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.javabean.HomeListBean;
import com.hxzk_bj_demo.javabean.HomeSearchBean;
import com.hxzk_bj_demo.javabean.InversBean;
import com.hxzk_bj_demo.javabean.LoginBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.javabean.PublicListData;
import com.hxzk_bj_demo.javabean.PublicNumBean;

import org.json.JSONObject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    Observable<BaseResponse<LoginOutBean>> login(@Query("username") String username , @Query("password") String password);


    /**
     * 退出登录接口
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginOutBean>>loginout();


    /**
     * 获取首页Banner接口
     * @return
     */
    @GET("banner/json")
    Observable<BannerBean> homeBanner();


    /**
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum 页码
     * @return
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<HomeListBean>> homeList(@Path("pageNum") int pageNum);


    /**
     * 首页请求搜索接口
     * @param searchKey
     * @return
     */
    @POST("article/query/{pageNum}/json")
    Observable<BaseResponse<HomeSearchBean>> homeSearch(@Path("pageNum") int pageNum,@Query("k") String searchKey);


    /**
     * 获取公众号列表
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<PublicNumBean>>> publicNum();


    /**
     * 获取公众号列表数据
     * @param publicId
     * @param publicNum
     * @return
     */
    @GET("wxarticle/list/{publicId}/{publicNum}/json")
    Observable<BaseResponse<PublicListData>> publicList(@Path("publicId") String publicId, @Path("publicNum") String publicNum);



    /**
     * 收藏站内文章
     * @param articalId 文章id
     * @return
     */
    @POST("lg/collect/{articalId}/json")
    Observable<JsonObject> collectArical(@Path("articalId") String articalId);


    /**
     *
     * @param pageNum 页码
     * @return
     */
    @GET("lg/collect/list/{pageNum}/json")
    Observable<BaseResponse<CollectionBean>> collectArticalList(@Path("pageNum") int pageNum);


    /**
     * 删除收藏
     * @param aricalId
     * @return
     */
    @POST("lg/uncollect_originId/{aricalId}/json")
    Observable<JsonObject> deleteCollectArtical(@Path("aricalId") String aricalId);


}
