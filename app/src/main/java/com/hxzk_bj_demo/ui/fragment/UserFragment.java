package com.hxzk_bj_demo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;


/**
 * Created by leeandy007 on 2017/6/15.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.toolbar_homeuser)
    Toolbar toolbar;
    @BindView(R.id.tv_content_homeuser)
    TextView tvContent;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }


    @Override
    protected void initData() {
        toolbar.setTitle("我的");
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
        StringBuilder stringBuilder =new StringBuilder();
        for(int i=0;i<100;i++){
            stringBuilder.append("这只是测试数据,别当真");
        }
        tvContent.setText(stringBuilder.toString());
    }


    @Override
    protected void initEvent() {
        Bundle mBundle = new Bundle();
        mBundle.putInt("fragmentflag", 2);
        mCallBack.setValue(mBundle);

    }


}
