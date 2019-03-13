package com.xzt.xrouter.router;

import android.content.Context;

import java.util.HashMap;

/**
 * 作者：Created by Ding on 2019/2/17
 * 文件描述：消息通道类
 */
public abstract  class Xaction {

    //参数上下文和请求消息的数据,hashMap的velue为object支持多种类型,返回的object是消息是否开始发送，或发送成功
    public abstract  Object startAction(Context context, HashMap<String ,Object> hashMap);
}
