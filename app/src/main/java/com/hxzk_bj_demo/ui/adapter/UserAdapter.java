package com.hxzk_bj_demo.ui.adapter;

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
 * 作者：created by ${zjt} on 2019/3/18
 * 描述:
 */
public class UserAdapter extends RecyclerView.Adapter {


    public static final  int FUNCTIONITEMVIEWTYPE =1111;
    public static final  int DETAILITEMVIEWTYPE =2222;


    Context mContext;
    List<UserItemBean> mDatas;

    public UserAdapter(Context mContext, List<UserItemBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder =null;
        if(viewType == FUNCTIONITEMVIEWTYPE){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_useradapter_fun, parent, false);
            mViewHolder =new FunctionViewHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_useradapter_detail, parent, false);
            mViewHolder =new DetailViewHolder(itemView);
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  FunctionViewHolder){
            FunctionViewHolder functionViewHolder = (FunctionViewHolder) holder;
            Glide.with(mContext).load(mDatas.get(position).getLocaImg()).into(functionViewHolder.ivFun);
            functionViewHolder.tvTitle.setText(mDatas.get(position).getTitle());
        }else{
            DetailViewHolder detailViewHolder = (DetailViewHolder) holder;
            Glide.with(mContext).load(mDatas.get(position).getLocaImg()).into(detailViewHolder.ivDetail);
            detailViewHolder.tvDetail.setText(mDatas.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  mDatas.get(position).getItemViewType();
    }



   public class  FunctionViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFun;
        TextView  tvTitle;

       public FunctionViewHolder(@NonNull View itemView) {
           super(itemView);
           ivFun=itemView.findViewById(R.id.iv_fun);
           tvTitle=itemView.findViewById(R.id.tv_fun);
       }
   }


   public class DetailViewHolder extends RecyclerView.ViewHolder{
        TextView tvDetail;
        ImageView ivDetail;

        public DetailViewHolder(@NonNull View itemView) {
           super(itemView);
            tvDetail=itemView.findViewById(R.id.tv_detail);
            ivDetail=itemView.findViewById(R.id.iv_detail);
       }
   }


}
