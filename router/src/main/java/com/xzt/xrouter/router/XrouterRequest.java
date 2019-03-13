
package com.xzt.xrouter.router;

import java.util.HashMap;

/**
 * 作者：Created by Ding on 2019/2/17
 * 文件描述：定义消息发送的载体类XrouterRequest
 */
public class XrouterRequest {

    //消息通道名称
    private String  mActionName;
    //消息通道携带的数据
    private HashMap<String ,Object > mData;


    private XrouterRequest(){
        mData =new HashMap<>();
    }

    public static XrouterRequest create(){
        return new XrouterRequest();
    }


     public XrouterRequest putData(String key, Object value){
         mData.put(key,value);
         return this;
     }

    public XrouterRequest putActionName(String actionName){
        mActionName=actionName;
        return this;
    }


     public String getActionName(){
        return this.mActionName;
     }


     public HashMap<String ,Object> getData(){
        return this.mData;
     }


}
