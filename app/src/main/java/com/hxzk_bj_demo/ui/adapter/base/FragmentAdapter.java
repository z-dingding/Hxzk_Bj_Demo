package com.hxzk_bj_demo.ui.adapter.base;

import android.content.Context;

import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    private List<Fragment> list;

    private FragmentManager fm;

    public FragmentAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.list = list;
    }

    /**
     * 获取给定位置对应的Fragment。
     *
     * @param position 给定的位置
     * @return 对应的Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //事务的创建是在instantiateItem和destroyItem方法中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = getItem(position);
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();

    }
}
