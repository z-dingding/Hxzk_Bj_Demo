package com.hxzk_bj_demo.network.interceptor;

import android.text.TextUtils;

import com.hxzk_bj_demo.common.MainApplication;
import com.hxzk_bj_demo.utils.SPUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;

/**
 * 作者：created by ${zjt} on 2019/2/26
 * 描述:自定义拦截器实现cookie的持久化，还一种方式是自定义CookieJar
 * 从response获取set-cookie字段的值，然后通过SharedPreferences保存在本地。
 */
public class SaveInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        Response response =chain.proceed(request);
        if(!response.headers("set-cookie").isEmpty()){
            List<String>  cookies =response.headers("set-cookie");
            String cookie =encodeCookie(cookies);
            saveCookie(KEY_COOKIE,cookie);
        }

        return response;
    }




    /**
     * 整合cookie为唯一字符串
     * @param cookies
     * @return
     */
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb =new StringBuilder();
        Set<String> set =new HashSet<>();
        for(String cookie : cookies){
            String [] arr =cookie.split(";");
            for(String s :arr){
                if(set.contains(s)){
               continue;
                }
                set.add(s);
            }

        }
        Iterator<String> ite =set.iterator();
        while(ite.hasNext()){
            String cookie=ite.next();
            sb.append(cookie).append(";");
        }
        int last=sb.lastIndexOf(";");
        if(sb.length()-1 == last){
          sb.deleteCharAt(last);
        }
        return sb.toString();
    }

    /**
     * 持久化cookie，保存到本地
     * @param cookieKey
     * @param cookies
     */
    private void saveCookie(String cookieKey ,String cookies){
        if(!TextUtils.isEmpty(cookieKey)){
            SPUtils.put(MainApplication.getAppContext(),cookieKey,cookies);
        }

    }

}