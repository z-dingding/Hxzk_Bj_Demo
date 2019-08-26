package com.hxzk_bj_demo.mvp.view;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public interface NoteBookView {
    void showSnackBar (String msg);

    void clearEditext();

    /**
     * 插入单条数据成功,加动画效果
     */
    void insertIetmNotify(int position);
    /**
     * 刷新指定位置的数据
     */
    void rangePositionNotify(int startPosition , int stopPosition);
    /**
     * 删除单条数据
     */
    void removeItemNotify(int postion);
    /**
     * 刷新全部数据
     */
    void notifyDataSetChanage();
    /**
     * 专门用来展示dialog的方法
     */
    void showAlert(AlertDialog alertDialog);

}
