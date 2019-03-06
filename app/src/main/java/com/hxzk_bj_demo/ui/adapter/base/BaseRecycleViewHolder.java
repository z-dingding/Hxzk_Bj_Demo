package com.hxzk_bj_demo.ui.adapter.base;

import android.content.Context;
import android.view.View;

import com.hxzk_bj_demo.javabean.HomeListBean;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecycleViewHolder(View view) {
        super(view);
       initView(view);
    }

    public void initView(View view){


    }

    public void initData(Context context, T t, int position){

    }

}
