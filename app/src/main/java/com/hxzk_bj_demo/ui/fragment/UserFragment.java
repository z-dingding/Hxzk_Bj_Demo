package com.hxzk_bj_demo.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.UserItemBean;
import com.hxzk_bj_demo.ui.activity.PatternLockActivity;
import com.hxzk_bj_demo.ui.adapter.UserAdapter;
import com.hxzk_bj_demo.ui.adapter.UserFunAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.ClearCacheUtil;
import com.hxzk_bj_demo.utils.MarioResourceHelper;
import com.hxzk_bj_demo.utils.activity.ActivityJump;
import com.hxzk_bj_demo.utils.recyclerview_itemlistener.RecyclerItemTouchListener;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leeandy007 on 2017/6/15.
 * 我的Fragment
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_user)
    RecyclerView mRecycler;
    @BindView(R.id.recyclerfun_user)
    RecyclerView mRecyclerFun;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.iv_collapsingbg)
    ImageView ivCollapsingbg;
    @BindView(R.id.collapsingtabl)
    CollapsingToolbarLayout collapsingToolbarLayout;


    View fmLayoutView;
    /**
     * 本地图片的数组
     */
    public static int[] localPhoto = {R.drawable.author_artical, R.drawable.recommended_music, R.drawable.recommended_video, R.drawable.statistical, R.drawable.more};
    /**
     * 标题数组
     */
    public static String[] localtitle;


    /**
     * 功能部分数据（包括本地图片地址数组和标题数组的内容）
     */
    List mFunction;
    List mDetail;


    UserAdapter mUserAdapter;
    UserFunAdapter mUserFunAdapter;
    @BindView(R.id.toolbar_setting)
    TextView toolbarSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        fmLayoutView = view;

    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        toolbar.setTitle("");
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
        mFunction = new ArrayList<UserItemBean>();
        mDetail = new ArrayList<UserItemBean>();

        localtitle = new String[]{mContext.getResources().getString(R.string.recommended_artical), mContext.getResources().getString(R.string.recommended_video), mContext.getResources().getString(R.string.recommended_music), mContext.getResources().getString(R.string.recommended_statistical), mContext.getResources().getString(R.string.more)};
        for (int i = 0; i < localPhoto.length; i++) {
            UserItemBean userItemBean = new UserItemBean();
            userItemBean.setTitle(localtitle[i]);
            userItemBean.setDes("");
            userItemBean.setLocaImg(localPhoto[i]);
            mFunction.add(userItemBean);

        }

        String[] languages = getResources().getStringArray(R.array.userframent_item);
        for (int i = 0; i < languages.length; i++) {
            UserItemBean userItemBean = new UserItemBean();
            userItemBean.setTitle(languages[i]);
            if (languages[i].equals("清除缓存")) {
                try {
                    String totalSize = ClearCacheUtil.getTotalCacheSize(mContext);
                    userItemBean.setDes(totalSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            userItemBean.setLocaImg(R.drawable.forward);
            mDetail.add(userItemBean);
        }


        LinearLayoutManager detailLayoutManager = new LinearLayoutManager(mContext);
        detailLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerFun.setLayoutManager(detailLayoutManager);
        mUserFunAdapter = new UserFunAdapter(mContext, mFunction);
        mRecyclerFun.setAdapter(mUserFunAdapter);


        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecycler.setLayoutManager(linearLayoutManager);
        mUserAdapter = new UserAdapter(mContext, mDetail);
        mRecycler.setAdapter(mUserAdapter);
    }


    @Override
    protected void initEvent() {
        Bundle mBundle = new Bundle();
        mBundle.putInt("fragmentflag", 2);
        mCallBack.setValue(mBundle);

        mRecyclerFun.addOnItemTouchListener(new RecyclerItemTouchListener(mRecyclerFun) {

            @Override
            public void onShortItemListener(RecyclerView.ViewHolder viewHolder) {
                UserFunAdapter.FunctionViewHolder myViewHolder = (UserFunAdapter.FunctionViewHolder) viewHolder;
                String title = (String) myViewHolder.tvTitle.getText();
                ToastCustomUtil.showLongToast(title);
            }

            @Override
            public void onLongItemListener(RecyclerView.ViewHolder viewHolder) {

            }
        });

        mRecycler.addOnItemTouchListener(new RecyclerItemTouchListener(mRecycler) {

            @Override
            public void onShortItemListener(RecyclerView.ViewHolder viewHolder) {
                UserAdapter.DetailViewHolder myViewHolder = (UserAdapter.DetailViewHolder) viewHolder;
                String title = (String) myViewHolder.tvDetail.getText();
                switch (title) {
                    case "图案解锁":
                        ActivityJump.NormalJump(mContext, PatternLockActivity.class);
                        break;
                    case "清除缓存":
                        ClearCacheUtil.clearAllCache(mContext);
                        myViewHolder.tvPromptDetail.setText("");

                        ToastCustomUtil.showLongToast(getString(R.string.clearCacheSuccess));

                        break;

                    default:
                        ToastCustomUtil.showLongToast(getString(R.string.inDevelopment));
                        break;
                }
            }

            @Override
            public void onLongItemListener(RecyclerView.ViewHolder viewHolder) {

            }
        });
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void notifyByThemeChanged() {
        MarioResourceHelper helper = MarioResourceHelper.getInstance(mContext);
        int color = helper.getColorByAttr(R.attr.custom_attr_app_statusbar_bg);
        collapsingToolbarLayout.setContentScrimColor(color);
        if (mUserAdapter != null) {
            mUserAdapter.notifyByThemeChanged();
        }
        if (mUserFunAdapter != null) {
            mUserFunAdapter.notifyByThemeChanged();
        }
    }

    @OnClick(R.id.toolbar_setting)
    public void onViewClicked() {
        ToastCustomUtil.showLongToast("123131");
    }
}
