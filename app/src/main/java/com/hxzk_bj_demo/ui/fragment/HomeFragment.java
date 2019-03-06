package com.hxzk_bj_demo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxzk.bj.common.X5ActionMessage;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.BannerBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.ExceptionHandle;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.other.ZoomOutPageTransformer;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.wenld.wenldbanner.AutoTurnViewPager;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner.OnPageClickListener;
import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.UIContact;
import com.wenld.wenldbanner.helper.ViewHolder;
import com.xzt.xrouter.router.Xrouter;
import com.xzt.xrouter.router.XrouterRequest;
import com.xzt.xrouter.router.XrouterResponse;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111:
                    //初始化Banner
                    initBanner();
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
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        //请求Bannder数据
       requestBanner();
        //创建线程池
        fixThreadPool = Executors.newFixedThreadPool(3);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpRequest.getInstance().unsubscribe(observable);
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
                XrouterRequest mXrouterRequest =XrouterRequest.create().putData("data",bannerList.get(i).getUrl().toString()).putActionName(X5ActionMessage.X5ACTIONNAME);
                XrouterResponse mXrouterResponse=Xrouter.getInstance().senMessage(mContext,mXrouterRequest);
                Toast.makeText(mContext,mXrouterResponse.getResponseResult()+"",Toast.LENGTH_LONG).show();
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





