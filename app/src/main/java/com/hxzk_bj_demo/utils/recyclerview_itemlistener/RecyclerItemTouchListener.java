package com.hxzk_bj_demo.utils.recyclerview_itemlistener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：created by ${zjt} on 2018/8/29
 * 描述:recyclerview的item触摸监听（item的长按或短按监听）
 */
 public abstract  class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {

    /**手势识别器对象**/
    GestureDetectorCompat mGestureDetectorCompat;
    /**RecyclerView对象**/
    RecyclerView mRecyclerView;


    public RecyclerItemTouchListener(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mGestureDetectorCompat=new GestureDetectorCompat(recyclerView.getContext(),new SimpGuestDetectorListener());
    }

//处理拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //将MotionEvnet传递给SimpleOnGestureListener用于确定位置
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }
//处理触摸事件
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }
    //处理触摸冲突

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

   //定义抽象方法回传ViewHolder
   public abstract void  onShortItemListener(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongItemListener(RecyclerView.ViewHolder viewHolder);

    public class SimpGuestDetectorListener extends GestureDetector.SimpleOnGestureListener{
        //点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            //根据坐标用findChildViewUnder获取itmeView
            View itemView=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if(itemView != null){
                //根据item获取Viewholder
                RecyclerView.ViewHolder mViewHolder =mRecyclerView.getChildViewHolder(itemView);
                onShortItemListener(mViewHolder);
            }

            return true;//表示自己消费
        }

//长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            //根据坐标用findChildViewUnder获取itmeView
            View itemView=mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if(itemView != null){
                //根据item获取Viewholder
                RecyclerView.ViewHolder mViewHolder =mRecyclerView.getChildViewHolder(itemView);
                onLongItemListener(mViewHolder);
            }

            super.onLongPress(e);
        }
    }

}