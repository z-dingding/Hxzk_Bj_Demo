package com.hxzk_bj_demo.javabean;

import org.litepal.crud.DataSupport;

/**
 * Created by ${赵江涛} on 2018-1-24.
 * 作用:收藏实体类
 */

public class CollectionBean extends DataSupport {

    int _id;
    String entName;
    String entAddress;
    String tiem;
    String  imgUrl;

    public String getTiem() {
        return tiem;
    }

    public void setTiem(String tiem) {
        this.tiem = tiem;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }
}
