package com.hxzk_bj_demo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.UserItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：created by ${zjt} on 2019/3/19
 * 描述:
 */
public class UserFunAdapter extends RecyclerView.Adapter{




    Context mContext;
    List<UserItemBean> mDatas;


    public UserFunAdapter(Context mContext, List<UserItemBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @SuppressLint("WrongConstant")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder = null;
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_useradapter_fun, parent, false);
            mViewHolder = new UserFunAdapter.FunctionViewHolder(itemView);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserFunAdapter.FunctionViewHolder) {
            UserFunAdapter.FunctionViewHolder functionViewHolder = (UserFunAdapter.FunctionViewHolder) holder;
            Glide.with(mContext).load(mDatas.get(position).getLocaImg()).into(functionViewHolder.ivFun);
            functionViewHolder.tvTitle.setText(mDatas.get(position).getTitle());
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class FunctionViewHolder extends RecyclerView.ViewHolder {
            ImageView ivFun;
            TextView tvTitle;

            public FunctionViewHolder(@NonNull View itemView) {
                super(itemView);
                ivFun = itemView.findViewById(R.id.iv_fun);
                tvTitle = itemView.findViewById(R.id.tv_fun);
            }
        }




    }



