package com.hxzk_bj_demo.ui.adapter;

import android.view.ViewGroup;

import com.hxzk_bj_demo.javabean.TabItemModelBean;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * 作者：created by ${zjt} on 2019/2/28
 * 描述: 当viewpager中fragment数量多的时候用FragmentStatePagerAdapter，反之则用FragmentPagerAdapter。
 * 在自定义的viewpager适配器类中重写destroyItem方法，来解决重新加载的问题
 */
public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private List<TabItemModelBean> tabIndicators;
    /**碎片集合*/
    private List<Fragment> fragmentList;

    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    /**
     * 自定义构造函数：用于传递碎片集合过来
     * 一般都写上*/
    public ContentPagerAdapter(FragmentManager fm, List<TabItemModelBean> tabIndicators, List<Fragment> fragmentList) {
        super(fm);
        this.tabIndicators = tabIndicators;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position).getTabTitle();
    }


}
