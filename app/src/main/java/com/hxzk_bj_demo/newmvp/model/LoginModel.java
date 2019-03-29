package com.hxzk_bj_demo.newmvp.model;

import com.hxzk_bj_demo.common.Const;
import com.hxzk_bj_demo.javabean.LoginOutBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.newmvp.constract.LoginConstract;
import com.hxzk_bj_demo.ui.activity.LoginActivity;
import com.hxzk_bj_demo.ui.activity.MainActivity;
import com.hxzk_bj_demo.utils.SPUtils;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import rx.Observable;

/**
 * 作者：created by ${zjt} on 2019/3/29
 * 描述:
 */
public class LoginModel implements LoginConstract.loginModel {



    @Override
    public Observable<BaseResponse<LoginOutBean>> login(String account, String pwd) {
        return HttpRequest.getInstance().getServiceInterface().login(account, pwd);
    }
}
