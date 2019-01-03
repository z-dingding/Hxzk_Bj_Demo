package com.hxzk_bj_demo.ui.fragment.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.utils.LazyLoadFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ding on 2017/12/24
 * 作用：基类Fragment
 */

public abstract class BaseFragment extends LazyLoadFragment {


    private  final String TAG = "BaseFragment";

    public FragmentCallBack mCallBack;


    /**Butterknife绑定对象*/
    private Unbinder mUnbinder;


    /**
     * Activity取Fragment所传递的值时调用的回调接口
     */
    public interface FragmentCallBack {

        /**
         * 传值到activity中
         */
        public void setValue(Object... param);

    }

    protected Context mContext;

    //获取布局文件ID
    protected abstract int getLayoutId();



    /**
     * 创建fragment的静态方法，方便传递参数
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment>T getInstance(Class clazz, Bundle args) {
        T mFragment=null;
        try {
                mFragment= (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(args != null){
            mFragment.setArguments(args);
        }

        return mFragment;
    }



    View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            String name =getClass().getSimpleName();
            if(view == null){
            view = inflater.inflate(getLayoutId(), null); }
            mUnbinder= ButterKnife.bind(this,view);
            initView(view, savedInstanceState);
            initData();
            initEvent();
            }
            return view;
    }


    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initEvent();

    protected abstract void initData();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext =context;
        try {
            mCallBack = (FragmentCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentCallBack");
        }

    }


    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        animNext();
    }

    protected void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        animNext();
    }

    /**
     * @Desc 页面跳转动画
     */

    public void animNext() {
        /**<<<------右入左出*/
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * @Desc 页面返回动画
     */
    public void animBack() {
        /**------>>>左入右出*/
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            doActivityResult(requestCode, data);
        }
    }

    protected void doActivityResult(int requestCode, Intent intent) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
