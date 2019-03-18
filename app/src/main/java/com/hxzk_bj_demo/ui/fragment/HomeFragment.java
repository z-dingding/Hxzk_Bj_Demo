package com.hxzk_bj_demo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.hxzk.bj.common.X5ActionMessage;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.BannerBean;
import com.hxzk_bj_demo.javabean.HomeListBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.ExceptionHandle;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.other.ZoomOutPageTransformer;
import com.hxzk_bj_demo.ui.adapter.HomeListAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.ProgressDialogUtil;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.xrecyclerview.WRecyclerView;
import com.wenld.wenldbanner.AutoTurnViewPager;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner.OnPageClickListener;
import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.UIContact;
import com.wenld.wenldbanner.helper.ViewHolder;
import com.xzt.xrouter.router.Xrouter;
import com.xzt.xrouter.router.XrouterRequest;
import com.xzt.xrouter.router.XrouterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by leeandy007 on 2017/6/15.
 * 懒加载主要关注的几个生命周期：onCreatedView + onActivityCreated + onResume + onPause + onDestroyView
 */


public class HomeFragment extends BaseFragment {


    private static final String TAG = "HomeFragment";


    @BindView(R.id.autoTurnViewPager)
    AutoTurnViewPager autoTurnViewPager;
    @BindView(R.id.defaultPageIndicator)
    DefaultPageIndicator defaultPageIndicator;
    @BindView(R.id.linear_root_home)
    LinearLayout linearRootHome;

    ExecutorService fixThreadPool;

    //Banner请求链接
    LinkedList<BannerBean.DataBean> bannerList;


    Observable<BannerBean> observable;
    Subscriber<BannerBean> subscriber;



    Observable<BaseResponse<HomeListBean>> homeListBeanObservable;
    Subscriber<BaseResponse<HomeListBean>> baseHomeListSubscriber;



    /**下拉刷新组件*/
    private SwipeRefreshLayout swipe_container;

    private WRecyclerView mRecyclerView;
    private List<HomeListBean.DatasBean> listitemList;
    private HomeListAdapter mHomeListAdapter;

    //当前页数
    private int curPageIndex = 0;
    //序号
    private int position = 0;
    //每页显示条数
    private int pageSize =20;
    //总页数
    private int totalPage;

    private LinearLayout nodata_layout;//暂无数据区域


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111:
                    //初始化Banner
                    initBanner();
                    break;

                    case 0x222:
                        //如果首页数据为空或者小于每页展现的条数，则禁用上拉加载功能
                        if(curPageIndex == 0){
                            if(listitemList.size() < pageSize){
                                mRecyclerView.setPullLoadEnable(false);//禁用上拉加载功能
                            }else{
                                mRecyclerView.setPullLoadEnable(true);//启用上拉加载功能
                            }
                        }
                        //设置适配器
                        if(mHomeListAdapter == null){
                            //设置适配器
                            mHomeListAdapter = new HomeListAdapter(mContext, listitemList,R.layout.item_homelist);
                            mRecyclerView.setAdapter(mHomeListAdapter);
                            //添加分割线
                            //设置添加删除动画
                            //调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
                            mRecyclerView.setSelected(true);
                            //列表适配器的点击监听事件
                            mHomeListAdapter.setOnItemClickLitener(new HomeListAdapter.OnItemClickLitener() {
                                @Override
                                public void onItemClick(int position) {
                                    //封装传递的请求数据到XrouterRequest
                                    XrouterRequest mXrouterRequest =XrouterRequest.create().putData("data",listitemList.get(position).getLink()).putActionName(X5ActionMessage.X5ACTIONNAME);
                                    XrouterResponse mXrouterResponse=Xrouter.getInstance().senMessage(mContext,mXrouterRequest);
                                }

                                @Override
                                public void onItemLongClick(int position) {
                                    ToastCustomUtil.showLongToast("正在开发中,敬请期待!");
                                }
                            });
                        }else{
                            mHomeListAdapter.notifyDataSetChanged();
                        }
                        stopRefreshAndLoading();//停止刷新和上拉加载
                        break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        nodata_layout =  view.findViewById(R.id.nodata_layout);
        swipe_container = view.findViewById(R.id.list_swiperefreshlayout);
    }

    @Override
    protected void initEvent() {
        //为SwipeRefreshLayout布局添加一个Listener，下拉刷新
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();//刷新列表
            }
        });

        //自定义上拉加载的监听
        mRecyclerView.setWRecyclerListener(new WRecyclerView.WRecyclerViewListener() {
            @Override
            public void onLoadMore() {
                Log.w(TAG, "onLoadMore-正在加载");
                curPageIndex = curPageIndex + 1;
                if (curPageIndex <= totalPage) {
                    requestHomeList(curPageIndex);//更新列表项集合
                } else {
                    //到达最后一页了
                    Toast.makeText(mContext, "我也是有底线滴", Toast.LENGTH_SHORT).show();
                    //隐藏正在加载的区域
                    stopRefreshAndLoading();
                }
            }
        });


    }


    @Override
    protected void initData() {
        // 初始化SwipeRefresh刷新控件
        initSwipeRefreshView();
        //请求Bannder数据
       // requestBanner();
        //创建线程池
        fixThreadPool = Executors.newFixedThreadPool(3);


        //初始化集合
        listitemList = new LinkedList<HomeListBean.DatasBean>();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //首次进来默认加载第一页数据,下标为1
       // requestHomeList(1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpRequest.getInstance().unsubscribe(observable);
        HttpRequest.getInstance().unsubscribe(homeListBeanObservable);
    }


    @Override
    public void notifyByThemeChanged() {

    }


    /**刷新列表*/
    private void refreshList() {
        mRecyclerView.setPullLoadEnable(false);//禁用上拉加载功能
        mRecyclerView.setPullRefresh(true);//设置处于下拉刷新状态中
        curPageIndex = 0;
        position = 0;
        //下拉刷新，需要清空集合
        listitemList.clear();
        //更新列表项集合
        requestHomeList(curPageIndex);
    }


    /**
     * 请求文章列表
     */
    private void requestHomeList(int pageNum) {

        baseHomeListSubscriber= new BaseSubscriber<BaseResponse<HomeListBean>>(mContext) {

            @Override
            public void onResult(BaseResponse<HomeListBean> homeListBeanBaseResponse) {
                totalPage=homeListBeanBaseResponse.getData().getPageCount();
                List linkList=homeListBeanBaseResponse.getData().getDatas();
                for(int i =0 ;i<linkList.size();i++){
                    listitemList.add((HomeListBean.DatasBean) linkList.get(i));
                }
                mHandler.sendEmptyMessage(0X222);
            }


            @Override
            public void onFail(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());
            }


        };

        homeListBeanObservable = HttpRequest.getInstance().getServiceInterface().homeList(pageNum);
        HttpRequest.getInstance().toSubscribe(homeListBeanObservable,baseHomeListSubscriber);
    }

    /**
     * 初始化SwipeRefresh刷新控件
     */
    private void initSwipeRefreshView() {
        //设置进度条的颜色主题，最多能设置四种
        swipe_container.setColorSchemeResources(R.color.swiperefresh_color_1,
                R.color.swiperefresh_color_2,
                R.color.swiperefresh_color_3,
                R.color.swiperefresh_color_4);
        //调整进度条距离屏幕顶部的距离 scale:true则下拉的时候从小变大
        swipe_container.setProgressViewOffset(true, 0, dip2px(mContext,10));
    }


    /**
     * dp转px
     * 16dp - 48px
     * 17dp - 51px*/
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)((dpValue * scale) + 0.5f);
    }

    /**
     * 停止刷新和上拉加载
     */
    private void stopRefreshAndLoading() {
        //检查是否处于刷新状态
        if(swipe_container.isRefreshing()){
            //显示或隐藏刷新进度条，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
            swipe_container.setRefreshing(false);
        }
        //如果正在加载，则获取数据后停止加载动画
        if(mRecyclerView.ismPullLoading()){
            mRecyclerView.stopLoadMore();//停止加载动画
        }
        mRecyclerView.setPullRefresh(false);//设置处于下拉刷新状态中[否]
    }


    /**
     * 请求banner图片信息
     */
    private void requestBanner() {
        bannerList = new LinkedList();
        subscriber = new Subscriber<BannerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());
            }

            @Override
            public void onNext(BannerBean baseResponse) {
                List mList = baseResponse.getData();
                for (Object bean : mList) {
                    bannerList.add((BannerBean.DataBean) bean);
                }
                mHandler.sendEmptyMessage(0x111);
            }
        };

        Observable<BannerBean> observable = HttpRequest.getInstance().getServiceInterface().homeBanner();
        //用observable提供的onErrorResumeNext 则可以将你自定义的Func1 关联到错误处理类中
        //observable.onErrorResumeNext(new BaseSubscriber.HttpResponseFunc<>());
        HttpRequest.getInstance().toSubscribe(observable, subscriber);
    }


    /**
     * 初始化Banner
     */
    private void initBanner() {
        int[] indicatorGrouop = new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused};

        Holder holder = new Holder<BannerBean.DataBean>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent, int pos, int viewType) {
                return ViewHolder.createViewHolder(context, parent, R.layout.item_vp_home, getViewType(pos));
            }

            @Override
            public void UpdateUI(Context context, ViewHolder viewHolder, int position, BannerBean.DataBean data) {
                try {
                    fixThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                                ImageView imageView = viewHolder.getView(R.id.iv_item_vp_home);
                                //You must call this method on the main thread
                            imageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(context)
                                            //.centerCrop()
                                            .asBitmap() //必须
                                            .load(data.getImagePath())
                                            .into(imageView);
                                }
                            });

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getViewType(int position) {
                return 0;
            }
        };
        autoTurnViewPager.setPages(holder)
                .setCanTurn(true)
                .setScrollDuration(3000)
                .setAutoTurnTime(2000);
        autoTurnViewPager.setPageTransformer(new ZoomOutPageTransformer());
        //设置指示器(选中,未选中)
        defaultPageIndicator.setPageIndicator(indicatorGrouop);
        UIContact.with(autoTurnViewPager, defaultPageIndicator)
                //设置数据
                .setData(bannerList);
        autoTurnViewPager.setOnItemClickListener(new OnPageClickListener() {
            @Override
            public void onItemClick(int i) {
                //封装传递的请求数据到XrouterRequest
                XrouterRequest mXrouterRequest =XrouterRequest.create().putData("data",bannerList.get(i).getUrl()).putActionName(X5ActionMessage.X5ACTIONNAME);
                XrouterResponse mXrouterResponse=Xrouter.getInstance().senMessage(mContext,mXrouterRequest);
               // Toast.makeText(mContext,mXrouterResponse.getResponseResult()+"",Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            String scanResult = bundle.getString("qr_scan_result");
            ToastCustomUtil.showLongToast("扫码结果是:" + scanResult);
        }
    }
}





