package com.hxzk_bj_demo.ui.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxzk_bj_demo.common.MyApplication;
import com.hxzk_bj_demo.interfaces.ThemeChangeObserver;
import com.hxzk_bj_demo.utils.LazyLoadFragment;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ding on 2017/12/24
 * 作用：基类Fragment
 */

public abstract class BaseFragment extends LazyLoadFragment implements ThemeChangeObserver  {

    private final String TAG = "BaseFragment";
    /**
     * Fragment回调Activity对象
     */
    public FragmentCallBack mCallBack;
    /**
     * Butterknife绑定对象
     */
    private Unbinder mUnbinder;
    /**
     * 上下文
     */
    protected static Context mContext;
    /**
     * 布局View
     */
    View layoutView = null;



    /**
     * Activity取Fragment所传递的值时调用的回调接口
     */
    public interface FragmentCallBack {
        void setValue(Object... param);
    }


    //获取布局文件ID
    protected abstract int getLayoutId();


    /**
     * 创建fragment的静态方法，方便传递参数
     *
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment> T getInstance(Class clazz, Bundle args) {
        T mFragment = null;
        try {
            mFragment = (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (args != null) {
            mFragment.setArguments(args);
        }
        return mFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            mCallBack = (FragmentCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentCallBack");
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         ((MyApplication) ((Activity)mContext).getApplication()).registerObserver(this);

        if (getLayoutId() != 0) {
            if (layoutView == null) {
                layoutView = inflater.inflate(getLayoutId(), null);
            }
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除。
            ViewGroup parent = (ViewGroup) layoutView.getParent();
            if (parent != null) {
                parent.removeView(layoutView);
            }
            mUnbinder = ButterKnife.bind(this, layoutView);
            initView(layoutView, savedInstanceState);
            initData();
            initEvent();
        }
        return layoutView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        ((MyApplication) ((Activity) getContext()).getApplication()).unregisterObserver(this);

    }


    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initEvent();

    protected abstract void initData();


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
    public void loadingCurrentTheme() {

    }
}
