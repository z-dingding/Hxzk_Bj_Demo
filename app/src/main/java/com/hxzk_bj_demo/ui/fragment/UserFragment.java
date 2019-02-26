package com.hxzk_bj_demo.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.adapter.ViewPagerFragmentAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by leeandy007 on 2017/6/15.
 */

public  class UserFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }


    @Override
    protected void initData() {

    }


    @Override
    protected void initEvent() {
        Bundle mBundle =new Bundle();
        mBundle.putInt("fragmentflag",2);
        mCallBack.setValue(mBundle);

    }







}
