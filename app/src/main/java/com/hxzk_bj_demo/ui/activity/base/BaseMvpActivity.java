package com.hxzk_bj_demo.ui.activity.base;

import com.hxzk_bj_demo.mvp.presenter.base.BasePreseneter;
import com.hxzk_bj_demo.mvp.view.base.BaseView;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public abstract class BaseMvpActivity<T extends BasePreseneter> extends BaseBussActivity implements BaseView {

    public T  presenter ;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.onDettachView();
        }
    }
}
