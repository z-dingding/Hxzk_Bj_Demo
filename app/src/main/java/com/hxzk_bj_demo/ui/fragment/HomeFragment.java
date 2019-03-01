package com.hxzk_bj_demo.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
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
import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.UIContact;
import com.wenld.wenldbanner.helper.ViewHolder;
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

    static Context mContext;
    @BindView(R.id.autoTurnViewPager)
    AutoTurnViewPager autoTurnViewPager;
    @BindView(R.id.defaultPageIndicator)
    DefaultPageIndicator defaultPageIndicator;
    @BindView(R.id.linear_root_home)
    LinearLayout linearRootHome;

    ExecutorService fixThreadPool;

    //Banner请求链接
    LinkedList<BannerBean.DataBean>  bannerList;


    Observable<BaseResponse<BannerBean>> observable;
    BaseSubscriber<BaseResponse<BannerBean>> subscriber;


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
        //初始化Banner
        initBanner();
        //创建线程池
         fixThreadPool= Executors.newFixedThreadPool(3);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpRequest.getInstance().unsubscribe(observable);
    }

    /**
     * 初始化Banner
     */
    private void initBanner(){
        requestBanner();

        int[] indicatorGrouop = new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused};

        Holder holder = new Holder<String>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent, int pos, int viewType) {
                return ViewHolder.createViewHolder(context, parent, R.layout.item_vp_home, getViewType(pos));
            }

            @Override
            public void UpdateUI(final Context context, final ViewHolder viewHolder, int position, final String data) {

                try {
                    fixThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final Bitmap myBitmap = Glide.with(context)
                                      //  .centerCrop()
                                        .asBitmap() //必须
                                       .load(data)
                                      .into(500, 500)
                                       .get();
                                if(myBitmap != null){
                                    final ImageView imageView =viewHolder.getView(R.id.iv_item_vp_home);
                                   //调用View的Post方法更新Ui
                                    imageView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView.setImageBitmap(myBitmap);
                                        }
                                    });

                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }  catch (Exception e) {
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
        UIContact uiContact = UIContact.with(autoTurnViewPager, defaultPageIndicator)
                //设置数据
                .setData(bannerList);


    }

    /**
     * 请求banner图片信息
     */
    private void requestBanner() {
        bannerList = new LinkedList();
       subscriber =new BaseSubscriber<BaseResponse<BannerBean>>(mContext) {



           @Override
           public void onError(ExceptionHandle.ResponeThrowable e) {
               ToastCustomUtil.showLongToast(e.message);

           }

           @Override
           public void onNext(BaseResponse<BaseResponse<BannerBean>> baseResponse) {
               List mList =new LinkedList();
               mList =baseResponse.getData().getData().getData();
               for(Object bean : mList){
                   bannerList.add((BannerBean.DataBean) bean);
               }

           }


       };

        Observable<BaseResponse<BannerBean>> observable =HttpRequest.getInstance().getServiceInterface().homeBanner();
        //用observable提供的onErrorResumeNext 则可以将你自定义的Func1 关联到错误处理类中
        observable.onErrorResumeNext(new BaseSubscriber.HttpResponseFunc<>());
        HttpRequest.getInstance().toSubscribe(observable,subscriber);
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





