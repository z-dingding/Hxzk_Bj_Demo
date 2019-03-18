package com.hxzk_bj_demo.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.UserItemBean;
import com.hxzk_bj_demo.ui.activity.CollectionActivity;
import com.hxzk_bj_demo.ui.adapter.UserAdapter;
import com.hxzk_bj_demo.ui.fragment.base.BaseFragment;
import com.hxzk_bj_demo.utils.MarioResourceHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public  class UserFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_user)
    RecyclerView mRecycler;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.iv_collapsingbg)
    ImageView ivCollapsingbg;

    View fmLayoutView;
    /**
     * 本地图片的数组
     */
    public static int  [] localPhoto={R.drawable.author_artical,R.drawable.recommended_music,R.drawable.recommended_video,R.drawable.statistical,R.drawable.more};
    /**
     * 标题数组
     */
    public static String [] localtitle;


    /**
     * 功能部分数据（包括本地图片地址数组和标题数组的内容）
     */
    List mFunction;


    UserAdapter mUserAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        fmLayoutView=view;

    }

    @Override
    protected void initData() {
        toolbar.setTitle("我的");
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
        mFunction=new ArrayList<UserItemBean>();
        localtitle= new String[]{mContext.getResources().getString(R.string.recommended_artical), mContext.getResources().getString(R.string.recommended_video), mContext.getResources().getString(R.string.recommended_music), mContext.getResources().getString(R.string.recommended_statistical), mContext.getResources().getString(R.string.more)};


        for(int i=0;i<localPhoto.length;i++){
            UserItemBean userItemBean =new UserItemBean();
            userItemBean.setTitle(localtitle[i]);
            userItemBean.setDes("");
            userItemBean.setLocaImg(localPhoto[i]);
            userItemBean.setItemViewType(UserAdapter.FUNCTIONITEMVIEWTYPE);
            mFunction.add(userItemBean);

        }

        String[] languages = getResources().getStringArray(R.array.userframent_item);
        for(int i =0;i<languages.length;i++){
            UserItemBean userItemBean =new UserItemBean();
            userItemBean.setTitle(languages[i]);
            userItemBean.setDes("");
            userItemBean.setLocaImg(R.drawable.forward);
            userItemBean.setItemViewType(UserAdapter.DETAILITEMVIEWTYPE);
            mFunction.add(userItemBean);
        }
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecycler.setLayoutManager(linearLayoutManager);
        mUserAdapter =new UserAdapter(mContext,mFunction);
        mRecycler.setAdapter(mUserAdapter);
    }


    @Override
    protected void initEvent() {
        Bundle mBundle = new Bundle();
        mBundle.putInt("fragmentflag", 2);
        mCallBack.setValue(mBundle);

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void notifyByThemeChanged() {
        MarioResourceHelper helper = MarioResourceHelper.getInstance(mContext);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }





}
