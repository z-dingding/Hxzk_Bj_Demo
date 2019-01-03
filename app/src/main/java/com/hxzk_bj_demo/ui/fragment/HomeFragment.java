package com.hxzk_bj_demo.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.other.ZoomOutPageTransformer;
import com.hxzk_bj_demo.ui.activity.WelcomeActivity;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
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



    /**
     * 初始化Banner
     */
    private void initBanner(){

        List bannerList = new LinkedList<>();
        bannerList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=255672777,4242622346&fm=27&gp=0.jpg");
        bannerList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2232245861,612896867&fm=27&gp=0.jpg");
        bannerList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2621991350,510510471&fm=27&gp=0.jpg");
        bannerList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=952379700,3301162203&fm=27&gp=0.jpg");
        bannerList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4223445256,1173646510&fm=27&gp=0.jpg");


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
                                       .load(data)
                                       .asBitmap() //必须
                                       .centerCrop()
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

}





