package com.hxzk_bj_demo.javabean;

/**
 * 作者：created by ${zjt} on 2019/3/18
 * 描述:
 */
public class UserItemBean {
    /**
     * 标题
     */
    String title;
    /**
     * 描述
     */
    String des;
    /**
     * 图片地址
     */
    int locaImg;
    /**
     * item类型
     */

    int itemViewType;

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public UserItemBean() {
    }

    public UserItemBean(String title, String des, int locaImg, int itemViewType) {
        this.title = title;
        this.des = des;
        this.locaImg = locaImg;
        this.itemViewType = itemViewType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getLocaImg() {
        return locaImg;
    }

    public void setLocaImg(int locaImg) {
        this.locaImg = locaImg;
    }
}
