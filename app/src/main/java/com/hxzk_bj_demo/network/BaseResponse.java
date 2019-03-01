package com.hxzk_bj_demo.network;

/**
 * 作者：created by ${zjt} on 2019/2/28
 * 描述:定义Response基类,网络返回基类 支持泛型,针对OnNext返回结果
 */
public class BaseResponse<T> {

    private int errorCode =100 ;
    private String errorMsg;
    private T data ;

    public int getCode() {
        return errorCode;
    }

    public void setCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return errorCode == 0;
    }
}
