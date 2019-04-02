package com.hxzk_bj_demo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.UserItemBean;
import com.hxzk_bj_demo.utils.MarioResourceHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：created by ${zjt} on 2019/3/18
 * 描述:
 */
public class UserAdapter extends RecyclerView.Adapter {




    Context mContext;
    List<UserItemBean> mDatas;


    public UserAdapter(Context mContext, List<UserItemBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @SuppressLint("WrongConstant")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder =null;
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_useradapter_detail, parent, false);
            mViewHolder =new DetailViewHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DetailViewHolder detailViewHolder = (DetailViewHolder) holder;
            Glide.with(mContext).load(mDatas.get(position).getLocaImg()).into(detailViewHolder.ivDetail);
            detailViewHolder.tvDetail.setText(mDatas.get(position).getTitle());
            if(!TextUtils.isEmpty(mDatas.get(position).getDes())){
                detailViewHolder.tvPromptDetail.setVisibility(View.VISIBLE);
                detailViewHolder.tvPromptDetail.setText(mDatas.get(position).getDes());
            }


           if(helper != null){
               helper.setTextColorByAttr(detailViewHolder.tvDetail,R.attr.custom_attr_app_textcolor);
           }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }


   public class DetailViewHolder extends RecyclerView.ViewHolder{
       public TextView tvDetail;
       public TextView tvPromptDetail;
       public ImageView ivDetail;

        public DetailViewHolder(@NonNull View itemView) {
           super(itemView);
            tvDetail=itemView.findViewById(R.id.tv_detail);
            tvPromptDetail=itemView.findViewById(R.id.tv_promptdetail);
            ivDetail=itemView.findViewById(R.id.iv_detail);
       }
   }



    /**
     * 刷新item的主题
     */
    MarioResourceHelper helper;
    public  void notifyByThemeChanged() {
        helper = MarioResourceHelper.getInstance(mContext);
        notifyDataSetChanged();
    }

}
