package com.hxzk_bj_demo.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewFragment;

import com.google.android.material.tabs.TabLayout;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.TabItemModelBean;
import com.hxzk_bj_demo.ui.adapter.ContentPagerAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by leeandy007 on 2017/6/15.
 *
 */

public class InvestFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private CustomViewPager mTabViewPager;

    private List<TabItemModelBean> tabIndicators;
    private List<Fragment> tabFragments;

    private ContentPagerAdapter contentAdapter;


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
         tabIndicators = new ArrayList<TabItemModelBean>();
        tabIndicators.add(new TabItemModelBean("百度"));
        tabIndicators.add(new TabItemModelBean("CSDN"));
        tabIndicators.add(new TabItemModelBean("博客园"));
        tabIndicators.add(new TabItemModelBean("极客头条"));
        tabIndicators.add(new TabItemModelBean("优设"));
        tabIndicators.add(new TabItemModelBean("玩Android"));
        tabIndicators.add(new TabItemModelBean("掘金"));
    }

    //当Fragemnt首次可见
    @Override
    public void onFragmentFirst() {
        super.onFragmentFirst();
        //初始化碎片集合
        tabFragments = new ArrayList<>();

        for(int i=0;i<tabIndicators.size();i++){
            TabItemModelBean tabItemModel = tabIndicators.get(i);
            tabFragments.add(OntherFragment.getInstance(OntherFragment.class,null));
        }
        //实例化Adapter
        contentAdapter = new ContentPagerAdapter(getActivity().getSupportFragmentManager(),tabIndicators,tabFragments);
        mTabViewPager.setAdapter(contentAdapter);
        //TabLayout和ViewPager相关联
        mTabLayout.setupWithViewPager(mTabViewPager);
    }


    @Override
    public void onFragmentVisble() {
        super.onFragmentVisble();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                ToastCustomUtil.showLongToast(tabIndicators.get(tab.getPosition()).getTabTitle().toString());

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
}
