package com.hxzk_bj_demo.network;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.javabean.BannerBean;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.javabean.HomeListBean;
import com.hxzk_bj_demo.javabean.HomeSearchBean;
import com.hxzk_bj_demo.javabean.IntegralBean;
import com.hxzk_bj_demo.javabean.IntegralListBean;
import com.hxzk_bj_demo.javabean.InversBean;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.javabean.PublicListData;
import com.hxzk_bj_demo.javabean.PublicNumBean;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:
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
     * @return
     * @Query("pageNum") int pageNum,
     */
    @POST("article/query/0/json")
    Observable<BaseResponse<HomeSearchBean>> homeSearch(@Query("k") String searchContent);


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
     * 删除收藏 站内文章
     * @param id
     * @return
     */

    @POST("lg/uncollect_originId/{id}/json")
    Observable<JsonObject> deleteCollectArtical(@Path("id") String id);


    /**
     * 获取首页Banner接口
     * @return
     */
    @GET("query?siteCode=bm35000001&database=all&method=json&qt=%E8%8D%AF%E5%93%81&page=1&pageSize=10")
    Observable<JsonObject> testMethod();


    /**
     * 获取个人积分
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<BaseResponse<IntegralBean>> integralApi();
    /**
     * 获取积分列表
     * @param pageNum
     */
    @GET("lg/coin/list/{pageNum}/json")
   Observable<BaseResponse<IntegralListBean>> inegralListApi(@Path("pageNum") int pageNum);
}
