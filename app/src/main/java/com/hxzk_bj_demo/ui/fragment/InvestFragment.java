package com.hxzk_bj_demo.ui.fragment;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.PublicNumBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.ExceptionHandle;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.adapter.ContentPagerAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by leeandy007 on 2017/6/15.
 *
 */

public class InvestFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private CustomViewPager mTabViewPager;

    private List<PublicNumBean> tabIndicators;
    private List<Fragment> tabFragments;

    private ContentPagerAdapter contentAdapter;

    Observable<BaseResponse<List<PublicNumBean>>> mObservable;
    Subscriber<BaseResponse<List<PublicNumBean>>>  mSubscriber;



    @Override
    protected int getLayoutId() {
        return (R.layout.fragment_invest);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_top);
        mTabViewPager = (CustomViewPager) view.findViewById(R.id.vp_tab);
        //禁止预加载【如果想要延迟首个选项卡的销毁时间，那么就需要设置这个数值高点】
        mTabViewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        //初始化选项卡子项的文本、超链接model集合
         tabIndicators = new ArrayList<>();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpRequest.getInstance().unsubscribe(mObservable);

    }


    //当Fragemnt首次可见
    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //请求公众号列表
        requestPublicList();
    }


    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                ToastCustomUtil.showLongToast(tabIndicators.get(tab.getPosition()).getName().toString());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中了tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中了tab的逻辑
            }
        });
    }


    private void requestPublicList() {
        mSubscriber =new BaseSubscriber<BaseResponse<List<PublicNumBean>>>(mContext) {
            @Override
            public void onResult(BaseResponse<List<PublicNumBean>> listBaseResponse) {
                if(listBaseResponse.isOk()){
                    for(PublicNumBean mPublicNumBean : listBaseResponse.getData()){
                        tabIndicators.add(mPublicNumBean);
                    }
                    //初始化碎片集合
                    tabFragments = new ArrayList<>();

                    for(int i=0;i<tabIndicators.size();i++){
                        Bundle mBundle =new Bundle();
                        mBundle.putString("publicId",tabIndicators.get(i).getId()+"");
                        tabFragments.add(BaseFragment.getInstance(OntherFragment.class,mBundle));
                    }
                    //实例化Adapter
                    contentAdapter = new ContentPagerAdapter(getActivity().getSupportFragmentManager(),tabIndicators,tabFragments);
                    mTabViewPager.setAdapter(contentAdapter);
                    //TabLayout和ViewPager相关联
                    mTabLayout.setupWithViewPager(mTabViewPager);
                }else{
                    ToastCustomUtil.showLongToast(listBaseResponse.getMsg());
                }


            }

            @Override
            public void onError(Throwable e) {
               ToastCustomUtil.showLongToast(e.getMessage());
            }

//            @Override
//            public void onFail(ExceptionHandle.ResponeThrowable e) {
//
//            }
        };

        mObservable=HttpRequest.getInstance().getServiceInterface().publicNum();
        HttpRequest.getInstance().toSubscribe(mObservable,mSubscriber);

    }
}
