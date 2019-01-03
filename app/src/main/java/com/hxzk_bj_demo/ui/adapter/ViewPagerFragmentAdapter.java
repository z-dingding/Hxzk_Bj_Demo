package com.hxzk_bj_demo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ding on 2017/5/25.
 */
public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragment_alist;
    String [] titles;


    public ViewPagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragment_alist, String [] titles) {
        super(fm);
        this.fragment_alist=fragment_alist;
        this.titles= titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_alist.get(position);
    }

    @Override
    public int getCount() {
        return fragment_alist.size() != 0?fragment_alist.size() :0;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
