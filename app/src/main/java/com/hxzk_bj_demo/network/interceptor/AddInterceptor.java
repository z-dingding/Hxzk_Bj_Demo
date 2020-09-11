package com.hxzk_bj_demo.network.interceptor;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.hxzk_bj_demo.common.MainApplication;
import com.hxzk_bj_demo.utils.NetWorkUtil;
import com.hxzk_bj_demo.utils.SPUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;

/**
 * 作者：created by ${zjt} on 2019/2/26
 * 描述:AddCookiesInterceptor请求拦截器，
 * 这个拦截的作用就是判断如果该请求存在cookie，
 * 则为其添加到请求Header的Cookie中
 */
public class AddInterceptor implements Interceptor {

    private Map<String, String> headers;
    private Context context;

    /**
     *  主要是为了缓存这里是公共的，还有允许自定义指定缓存，之后在研究
     */
    public AddInterceptor(Map<String, String> headers, Context context) {
        this.headers = headers;
        this.context = context;
    }


    public AddInterceptor() {
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie();
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
        }
        //拦截器实现关键部分是调用chain.proceed(request)。
        // 这个方法是所有HTTP工作发生的地方，以满足请求和响应的需求。
        return chain.proceed(builder.build());
    }


    /**
     * 获取之前存储在SP中的cookie
     * @return
     */
    private String getCookie() {
        return (String) SPUtils.get(MainApplication.getAppContext(), KEY_COOKIE, "");
    }


    /**
     *执行退出登录后,清空本地保存的cookie
     */
    public static void clearCookie(Context context , String clearKey ){
        SPUtils.remove(context,clearKey);
    }


}
