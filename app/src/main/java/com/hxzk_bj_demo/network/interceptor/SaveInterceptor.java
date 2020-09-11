package com.hxzk_bj_demo.network.interceptor;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hxzk_bj_demo.common.Const;
import com.hxzk_bj_demo.common.MainApplication;
import com.hxzk_bj_demo.javabean.EventBuseBean;
import com.hxzk_bj_demo.ui.activity.LoginActivity;
import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.ui.activity.WelcomeActivity;
import com.hxzk_bj_demo.utils.AvoidClickAgainUtil;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.hxzk_bj_demo.common.Const.KEY_COOKIE;

/**
 * 作者：created by ${zjt} on 2019/2/26
 * 描述:自定义拦截器实现cookie的持久化，还一种方式是自定义CookieJar
 * 从response获取set-cookie字段的值，然后通过SharedPreferences保存在本地。
 */
public class SaveInterceptor implements Interceptor {

    private Context context;

    public SaveInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        Response response =chain.proceed(request);
        //先请求后响应，如果登录后set-cookie就不会有值了，首次登录返回
        if(!response.headers("set-cookie").isEmpty()){
            List<String>  cookies =response.headers("set-cookie");
            String cookie =encodeCookie(cookies);
            saveCookie(KEY_COOKIE,cookie);
        }
        response = responseIntercept(response);
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



    public Response responseIntercept(Response response) {
        try {
            //不能直接使用response.body().string()的方式获取请求返回，
            // 因为response.body().string()之后，response中的流会被关闭，程序会报错
            //我们需要创建出一个新的response进行处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            String responseStr = responseBody.string();
            if (!TextUtils.isEmpty(responseStr)&& isJson(responseBody.contentType())) {
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    if (jsonObject.has("errorMsg") && jsonObject.has("errorCode")) {
                        String msg = jsonObject.getString("errorMsg");
                        int code = jsonObject.getInt("errorCode");
                        //如果包含登录字符就认为服务器提示登录
                        if (msg.contains("登录")) {
                            //清空保存在本地的cookie
                            EventBus.getDefault().post(new EventBuseBean(Const.EVENTBUS_RESETLOGIN));
                        }
                    }else{
                        //Domain=wanandroid.com; Expires=Wed, 07-Oct-2020 02:46:20 GMT;loginUserName_wanandroid_com=hxzk123456;token_pass=1e86ac80fd05bacec2ab641a62d0aa98; Path=/;loginUserName=hxzk123456;token_pass_wanandroid_com=1e86ac80fd05bacec2ab641a62d0aa98
                        // HttpOnly; Path=/;JSESSIONID=A28331372EE214E891D731EDE9C16245; Secure
                        List<String>  cookies =response.headers("set-cookie");
                        String cookie =encodeCookie(cookies);
                        saveCookie(KEY_COOKIE,cookie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 判断数据是不是json
     * @param mediaType
     * @return
     */
    private  boolean isJson(MediaType mediaType) {
        String subtype = mediaType.subtype();
        if (mediaType == null) return false;
        //type值是text，表示是文本这一大类，允许打印
        if (mediaType.type() != null && mediaType.type().equals("application") && subtype.toLowerCase().equals("json")) {
            return true;
        }
        return false;
    }
}
