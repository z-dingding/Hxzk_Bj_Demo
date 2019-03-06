package com.hxzk_bj_demo.widget.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 作者：created by ${zjt} on 2019/3/6
 * 描述:自定义RecyclerView，在不改动 RecyclerView 原有 adapter 的情况下，使其拥有加载更多功能和自定义底部视图。
 * 参考资料：https://github.com/nukc/LoadMoreWrapper
 * @touch执行顺序：dispatchTouchEvent（Action_down）
 * ——onScrollStateChanged==RecyclerView.SCROLL_STATE_DRAGGING[1]——
 * 【onTouchEvent（Action_move）——onScrolled】循环——onTouchEvent（Action_up）
 * ——onScrollStateChanged==RecyclerView.SCROLL_STATE_IDLE[0]
 */
public class WRecyclerView extends RecyclerView {
    private static final String TAG = WRecyclerView.class.getSimpleName();

    /**自定义适配器，用于在基础列表数据的基础上添加底部区域*/
    private WRecyclerViewAdapter wRecyclerViewAdapter;

    /**自定义上拉加载的监听器*/
    private WRecyclerViewListener mRecyclerListener;

    /**===================底部--上拉加载区域========================*/
    private WRecyclerViewFooter mFooterView;
    /**是否启用上拉加载功能的标记：true-启用；false-禁用*/
    private boolean mEnablePullLoad = true;
    /**上拉加载区域是否正在显示的标记*/
    private boolean isShowFooter = false;
    /**是否处于加载状态的标记：true-加载；false-正常*/
    private boolean mPullLoading = false;

    /**当前是否处于快速滑动的状态 :默认为false*/
    private boolean isQuickSlide = false;

    /**当前是否处于下拉刷新的状态 :默认为false*/
    private boolean isPullRefresh = false;

    /**快速移动产生惯性的移动距离值（自定义的临界值）*/
    private final static int FAST_MOVE_DY = 150;//原先是100

    /**recyclerView最后一个可见的item的下标值*/
    private int lastVisibleItem = 0;
    /**recyclerView总item数目*/
    private int mTotalItemCount = 0;


    public WRecyclerView(Context context) {
        super(context);
        initWithContext(context);
    }
    public WRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }
    public WRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mFooterView = new WRecyclerViewFooter(context);

        /*解决bug:
         * 使用 RecyclerView 加官方下拉刷新的时候，如果绑定的 List 对象在更新数据之前进行了 clear，而这时用户紧接着迅速上滑 RV，就会造成崩溃，而且异常不会报到你的代码上，属于RV内部错误。
         * 初次猜测是，当你 clear 了 list 之后，这时迅速上滑，而新数据还没到来，导致 RV 要更新加载下面的 Item 时候，找不到数据源了，造成 crash.
         * https://blog.csdn.net/lvwenbo0107/article/details/52290536*/
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isPullRefresh) {//如果正在刷新，则不能滑动
                    return true;
                } else {
                    return false;
                }
            }
        });


        /**RecyclerView的滚动监听器*/
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.w(TAG,"{onScrollStateChanged}newState="+newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_DRAGGING:/*当前的recycleView被拖动滑动，类似action_down的效果1*/
                        isQuickSlide = false;//每一次开始滑动之前，设置快速滑动状态值为false
                        if(mEnablePullLoad){
                            mFooterView.show();//因为快速滑动产生惯性的时候会进行隐藏底部上拉加载区域，所以需要在下次正常滑动之前在这里重新显示FootView
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:/*当前的recycleView在滚动到某个位置的动画过程,但没有被触摸滚动.调用 scrollToPosition(int) 应该会触发这个状态2*/
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:/*停止滑动状态0*/
                        //isQuickSlide = false;//解开这个注释，就是不管快速滑动还是正常滑动，只要显示最后一条列表，就会自动加载数据
                        if(isShowFooter && !mPullLoading && !isQuickSlide){//也就是手指慢慢滑动出来foot区域的情况
                            startLoadMore(); //停止滑动后，如果上拉加载区域正在显示并且没有处于正在加载状态并且不是快速滑动状态，那么就开始加载
                        }else{
                            mFooterView.setState(WRecyclerViewFooter.STATE_NORMAL);//底部区域显示【查看更多】
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取当前显示的最后一个子项下标值
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                mTotalItemCount = layoutManager.getItemCount();
                if (layoutManager instanceof GridLayoutManager) {
                    lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    int max = lastPositions[0];
                    for (int value : lastPositions) {
                        if (value > max) {
                            max = value;
                        }
                    }
                    lastVisibleItem = max;
                } else {
                    lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }

                /*
                 * 对于正常移动的情况，如果到了最后一个子项（底部上拉加载区域），并且启用了上拉加载功能
                 * */

                if(mEnablePullLoad){

                    if(lastVisibleItem >= mTotalItemCount - 1) {//【实现松开手指后才会加载，暂时有问题--貌似没有问题了】
                        //if(lastVisibleItem >= mTotalItemCount - 1 - 1 ) {//减去的1，代表的footView，再减去的1代表下标值（0开始）【实现滑动到底部自动加载】
                        if(dy > 0){//向下滑动
                            isShowFooter = true;
                            /*
                             * 如果移动的距离大于FAST_MOVE_DY，则表明当前处于快速滑动产生惯性的情况，则隐藏底部上拉加载区域，这样就控制了底部上拉加载区域的不正常显示*/
                            if(dy > FAST_MOVE_DY){//当快速移动，产生惯性的时候，隐藏底部上拉加载区域
                                mFooterView.hide();
                                isQuickSlide = true;//只要有一次滑动的距离超过快速滑动临界值，则代表当前处于快速滑动状态
                            }else{
                                Log.w(TAG, "{onScrolled}mFooterView.getBottomMargin()="+mFooterView.getBottomMargin());
                                mFooterView.setState(WRecyclerViewFooter.STATE_READY);//底部区域显示【上拉加载更多】
                            }
                        }else{
                            //向上滑动，不做任何处理
                        }
                    }else {
                        /*如果还没有到最后一个子项，前提条件是启用了上拉加载功能
                         * 则：(1)设置上拉加载区域显示状态值为false
                         * (2)隐藏上拉加载区域*/
                        isShowFooter = false;
                        mFooterView.hide();
                    }
                }
            }
        });
    }

    /**
     * 设置适配器
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        wRecyclerViewAdapter = new WRecyclerViewAdapter(adapter, mFooterView, mEnablePullLoad);
        super.setAdapter(wRecyclerViewAdapter);
    }


    /**是否正在上拉加载*/
    public boolean ismPullLoading() {
        return mPullLoading;
    }
    /*==================================上拉加载功能==========================================*/
    /**
     * 设置启用或者禁用上拉加载功能
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            //禁用上拉加载功能
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            //启用上拉加载功能
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(WRecyclerViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
        /**解决当第一页就显示出来footview的时候，下拉刷新崩溃的问题
         * java.lang.IllegalArgumentException: Scrapped or attached views may not be recycled. isScrap:false isAttached:true
         * 是指view没有被recycled（回收），也就是foot区域没有被回收
         * https://blog.csdn.net/u013106366/article/details/54024113*/
        if(wRecyclerViewAdapter != null){
            wRecyclerViewAdapter.setmEnablePullLoad(mEnablePullLoad);
            Log.w(TAG,"wRecyclerViewAdapter.notifyDataSetChanged()");
            wRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**开始加载，显示加载状态*/
    private void startLoadMore() {
        if(mEnablePullLoad){
            if(mPullLoading)return;
            mPullLoading = true;
            mFooterView.setState(WRecyclerViewFooter.STATE_LOADING);
            if (mRecyclerListener != null) {
                mRecyclerListener.onLoadMore();
            }
        }
    }

    /**
     * 停止加载，还原到正常状态
     */
    public void stopLoadMore() {
        if(mEnablePullLoad){//启用上拉加载功能
            if (mPullLoading == true) {//如果处于加载状态
                mPullLoading = false;
                mFooterView.setState(WRecyclerViewFooter.STATE_NORMAL);
                mFooterView.hide();
            }
        }
    }

    /**是否是否处于下拉刷新的状态*/
    public void setPullRefresh(boolean pullRefresh) {
        isPullRefresh = pullRefresh;
    }

    /*==================================自定义下拉刷新和上拉加载的监听器==========================================*/
    /**
     * 自定义下拉刷新和上拉加载的监听器
     */
    public interface WRecyclerViewListener {
        /**上拉加载*/
        public void onLoadMore();
    }

    public void setWRecyclerListener(WRecyclerViewListener l) {
        mRecyclerListener = l;
    }


}
