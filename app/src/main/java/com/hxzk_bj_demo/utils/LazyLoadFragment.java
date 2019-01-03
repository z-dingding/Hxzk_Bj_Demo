package com.hxzk_bj_demo.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：created by ${zjt} on 2018/10/12
 * 描述:
 */
public abstract class LazyLoadFragment extends android.support.v4.app.Fragment {


    /**当前Fragment是否首次可见，默认是首次可见**/
    private boolean mIsFirstVisible = true;
    /**当前Fragment的View是否已经创建**/
    private boolean isViewCreated = false;
    /**当前Fragment的可见状态，一种当前可见，一种当前不可见**/
    private boolean currentVisibleState = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            //Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
            if (isVisibleToUser && !currentVisibleState) {
                disPatchFragment(true);
                //Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
            } else if (!isVisibleToUser && currentVisibleState) {
                disPatchFragment(false);
            }
        }
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated=true;//在onCreateView执行完毕，将isViewCreated改为true; 
    }

    @Override
    public void onStart() {
        super.onStart();
     //LogUtil.e(getClass().getSimpleName(),"------>onStart");
        //isHidden()是Fragment是否处于隐藏状态和isVisible()有区别
        //getUserVisibleHint(),Fragement是否可见
        if(!isHidden()&& getUserVisibleHint()){//如果Fragment没有隐藏且可见
            //执行分发的方法,三种结果对应自Fragment的三个回调，对应的操作，Fragment首次加载，可见，不可见
            disPatchFragment(true);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        //LogUtil.e(getClass().getSimpleName(),"------>onResume");
        if(!mIsFirstVisible){
            //表示点击home键又返回操作,设置可见状态为ture
            if(!isHidden()&& !getUserVisibleHint() && currentVisibleState){
                disPatchFragment(true);
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //LogUtil.e(getClass().getSimpleName(),"------>onPause");
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if(!isHidden()&& getUserVisibleHint()){
            disPatchFragment(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //LogUtil.e(getClass().getSimpleName(),"------>onDestroyView");
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false;
        mIsFirstVisible = true;
    }


    /**
     *
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    public  void   disPatchFragment(boolean visible){
        String aa =getClass().getSimpleName();
        //如果父Fragment不可见,则不向下分发给子Fragment
        if(visible && isParentFragmentVsible()){return;}

        // 如果当前的 Fragment 要分发的状态与 currentVisibleState 相同(都为false)我们就没有必要去做分发了。
        if (currentVisibleState == visible) {
            return;
        }

        currentVisibleState=visible;
        if(visible){//Fragment可见
            if(mIsFirstVisible){//可见又是第一次
                mIsFirstVisible=false;//改变首次可见的状态
                onFragmentFirst();
            }//可见但不是第一次
            onFragmentVisble();
            //可见状态的时候内层 fragment 生命周期晚于外层 所以在 onFragmentResume 后分发
            dispatchChildFragmentVisibleState(true);
        }else {//不可见
            onFragmentInVisible();
            dispatchChildFragmentVisibleState(false);
        }
    };


    /**
     * 重新分发给子Fragment
     * @param visible
     */
    private void dispatchChildFragmentVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        @SuppressLint("RestrictedApi") List<android.support.v4.app.Fragment> fragments = childFragmentManager.getFragments();
        if(fragments != null){
            if (!fragments.isEmpty()) {
                for (Fragment child : fragments) {
                    if (child instanceof LazyLoadFragment && !child.isHidden() && child.getUserVisibleHint()) {
                        ((LazyLoadFragment) child).disPatchFragment(visible);
                    }
                }
            }
        }

    }

    //Fragemnet首次可见的方法
    public void onFragmentFirst(){
        //LogUtil.e(getClass().getSimpleName(),"首次可见");
    };
    //Fragemnet可见的方法
    public void onFragmentVisble(){//子Fragment调用次方法，执行可见操作
      //  LogUtil.e(getClass().getSimpleName(),"可见");
    };
    //Fragemnet不可见的方法
    public void onFragmentInVisible(){
       // LogUtil.e(getClass().getSimpleName(),"不可见");
    };


    /**
     *判断多层嵌套的父Fragment是否显示
     */
    private boolean isParentFragmentVsible() {
        LazyLoadFragment fragment = (LazyLoadFragment) getParentFragment();
        return fragment != null && !fragment.getCurrentVisibleState();
    }

    /**
     * 获取当前Fragment的可见状态
     * @return
     */
    private boolean getCurrentVisibleState() {
        return currentVisibleState;
    }





}
