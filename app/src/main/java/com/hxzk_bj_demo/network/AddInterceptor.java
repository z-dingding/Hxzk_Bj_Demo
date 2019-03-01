package com.hxzk_bj_demo.network;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.utils.NetWorkUtil;
import com.hxzk_bj_demo.utils.SPUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.CacheControl;
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

    private Map<String, String> headers;
    private Context context;

    //主要是为了缓存这里是公共的，还有允许自定义指定缓存，之后在研究
    public AddInterceptor(Map<String, String> headers, Context context) {
        this.headers = headers;
        this.context = context;
    }


    public AddInterceptor(){}


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        Request.Builder builder =request.newBuilder();
        String cookie=getCookie(request.url().toString(),request.url().host().toString());
        if(!TextUtils.isEmpty(cookie)){
            builder.addHeader("Cookie",cookie);
        }

                            /*   公共缓存部分先注释*/
//        builder.cacheControl(CacheControl.FORCE_CACHE).url(chain.request().url())
//                .build();
//        if (!NetWorkUtil.isNetworkAvailable(context)) {
//
//            ((Activity)context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(context, "当前无网络!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }        if (headers != null && headers.size() > 0) {
//            Set<String> keys = headers.keySet();
//            for (String headerKey : keys) {
//                builder.addHeader(headerKey, headers.get(headerKey)).build();
//            }
//        }
//        if (NetWorkUtil.isNetworkAvailable(context)) {
//            int maxAge = 60; // read from cache for 60 s
//            builder
//                    .removeHeader("Pragma")
//                    .addHeader("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        } else {
//            int maxStale = 60 * 60 * 24 * 14; // tolerate 2-weeks stale
//            builder
//                    .removeHeader("Pragma")
//                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
        return chain.proceed(builder.build());
    }


    private String getCookie(String url,String domain) {
        if(!TextUtils.isEmpty(url) && !TextUtils.isEmpty((String) SPUtils.get(MyApplication.getAppContext(),domain,""))){
          return (String) SPUtils.get(MyApplication.getAppContext(),domain,"");
        }
        return null;
    }


}
