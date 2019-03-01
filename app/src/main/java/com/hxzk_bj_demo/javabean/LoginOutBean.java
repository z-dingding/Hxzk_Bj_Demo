package com.hxzk_bj_demo.javabean;

import com.hxzk_bj_demo.network.BaseResponse;

/**
 * 作者：created by ${zjt} on 2019/3/1
 * 描述:退出登录实体类
 */
public class LoginOutBean  {
//    private int errorCode;
//    private String errorMsg;
    private Object data;


//    public int getCode() {
//        return errorCode;
//    }
//
//    public void setCode(int code) {
//        this.errorCode = code;
//    }
//
//    public String getMsg() {
//        return errorMsg;
//    }
//
//    public void setMsg(String msg) {
//        this.errorMsg = msg;
//    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

//    public boolean isOk() {
//       return errorCode == 0;
//    }
}
