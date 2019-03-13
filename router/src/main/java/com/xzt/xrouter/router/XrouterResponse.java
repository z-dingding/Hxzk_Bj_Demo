package com.xzt.xrouter.router;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：Created by Ding on 2019/2/17
 * 文件描述：定义消息发送的响应类XrouterRequest
 */
public class XrouterResponse {

    public static final String RESPONSE_SUCCESS="发送消息成功";
    public static final String RESPONSE_FAIL="发送消息失败";
    public static final  int RESPONSE_SUCCESS_CODE=1;
    public static final  int RESPONSE_FAIL_CODE=0;


    //状态码
     private int mcode;
     //描述：消息发送成功或失败
     private String mdes;
     //消息反馈的其他信息
     private Object mbody;



     //获取响应消息
     public JSONObject getResponseResult(){
         JSONObject mJSONObject=null;
         try {
             mJSONObject =new JSONObject();
             mJSONObject.put("code",mcode).put("des",mdes).put("content",mbody);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return mJSONObject;
     }



     //设置响应消息
     public  void setResponseResult(int code ,String des,Object obj){
         this.mcode=code;
         this.mdes=des;
         this.mbody=obj;
     }






}
