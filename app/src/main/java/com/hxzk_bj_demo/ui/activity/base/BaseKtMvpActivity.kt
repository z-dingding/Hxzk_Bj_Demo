package com.hxzk_bj_demo.ui.activity.base

import com.hxzk_bj_demo.mvp.presenter.base.BasePreseneter
import com.hxzk_bj_demo.mvp.view.base.BaseView

/**
 *作者：created by hxzk on 2020/6/11
 *描述:
 *
 */
 abstract class BaseKtMvpActivity<T : BasePreseneter<*>> : BaseKtActivity(), BaseView{

    var presenter: T? = null


    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDettachView()
    }
}