package com.xzt.xrouter.router;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/**
 * 作者：Created by Ding on 2019/2/17
 * 文件描述：路由器的核心，消息中转类,专门用来处理消息的
 */
public class Xrouter {

    private static final String TAG = "Xrouter";

    //使用volatile关键字的好处是保证线程可见
    public static volatile Xrouter  mXrouter ;
    //使用HashMap初始化一个队列mXactions，用来存储不同种类的消息通道
    public static HashMap<String,Xaction> mXactions ;


    private  Xrouter() {
        mXactions =new HashMap<>();
    }

//单例模式,支持多线程。参考：https://www.jianshu.com/p/769f2593c94e
    public static Xrouter getInstance(){
        if(mXrouter == null){
           synchronized (Xrouter.class){
               if(mXrouter == null){
                   mXrouter =new Xrouter();
               }
           }
        }
        return mXrouter;
    }


    //往消息队列插入不同消息通道的方法，这里参数包括通道名称和通道本身Xaction.java
    public  void registerAction(String actionKey,Xaction xaction){
        if(mXactions.containsKey(actionKey)){
            Log.e(TAG, "该通道已经注册");
        }else{
            mXactions.put(actionKey,xaction);
        }

    }



    //对应通道发送消息的方法
    public  XrouterResponse senMessage(Context context ,XrouterRequest xrouterRequest){
       XrouterResponse mXrouterResponse=new XrouterResponse();
       Xaction mXaction =getmXAction(xrouterRequest.getActionName());
       //Xaction对象不为空，说明该通道存在，之前已经注册，可以正常发送消息
       if(mXaction != null){
           //将发送的消息内容(xrouterRequest.getData())传递给对应的Xaction的实现类
           Object mObject =mXaction.startAction(context,xrouterRequest.getData());
           mXrouterResponse.setResponseResult(XrouterResponse.RESPONSE_SUCCESS_CODE,XrouterResponse.RESPONSE_SUCCESS,mObject);

       }else{
           mXrouterResponse.setResponseResult(XrouterResponse.RESPONSE_FAIL_CODE,XrouterResponse.RESPONSE_FAIL,"该Xaction没有创建");

       }

     return mXrouterResponse;
    }


    //返回消息通道对应的对象
    public Xaction  getmXAction(String actionName){
        if(mXactions.containsKey(actionName)){
            return mXactions.get(actionName);
        }
        return null;
    }


}
