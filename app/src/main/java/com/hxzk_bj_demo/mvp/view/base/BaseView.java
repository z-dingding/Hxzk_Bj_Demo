package com.hxzk_bj_demo.mvp.view.base;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public interface BaseView {

    void onShowLoading();
    void onHiddenLoading();
    void onFail(Throwable throwable);
}
