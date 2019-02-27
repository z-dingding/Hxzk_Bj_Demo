package com.hxzk_bj_demo.network;


import android.text.TextUtils;

import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：created by ${zjt} on 2019/2/26
 * 描述:AddCookiesInterceptor请求拦截器，
 * 这个拦截的作用就是判断如果该请求存在cookie，
 * 则为其添加到请求Header的Cookie中
 */
public class AddInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        Request.Builder builder =request.newBuilder();
        String cookie=getCookie(request.url().toString(),request.url().host().toString());
        if(!TextUtils.isEmpty(cookie)){
            builder.addHeader("Cookie",cookie);
        }
        return chain.proceed(builder.build());
    }


    private String getCookie(String url,String domain) {
        if(!TextUtils.isEmpty(url) && !TextUtils.isEmpty((String) SPUtils.get(MyApplication.getAppContext(),domain,""))){
          return (String) SPUtils.get(MyApplication.getAppContext(),domain,"");
        }
        return null;
    }


}
