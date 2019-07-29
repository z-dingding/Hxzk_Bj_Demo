package com.hxzk_bj_demo.ui.activity;
import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.hxzk.bj.common.X5ActionMessage;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.CollectionAdapter;
import com.hxzk_bj_demo.utils.ProgressDialogUtil;
import com.hxzk_bj_demo.widget.SwipeItemLayout;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.CustomRecyclerView;
import com.xzt.xrouter.router.Xrouter;
import com.xzt.xrouter.router.XrouterRequest;
import com.xzt.xrouter.router.XrouterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ${赵江涛} on 2018-1-24.
 * 作用:
 * implements CustomRecyclerView.onGetListener
 */

public class CollectionActivity extends BaseBussActivity  {

    //CustomRecyclerView mRecyclerCollect;
    @BindView(R.id.recycler_collect)
     RecyclerView mRecyclerCollect;
    CollectionAdapter mAdapter;
    //数据源
    List mData;
    //获取收藏数据观察者和订阅者
    Observable<BaseResponse<CollectionBean>> mObservable;
    Subscriber<BaseResponse<CollectionBean>> mSubscriber;
    //请求页码
    int pageNum;
    //请求删除收藏的观察者和订阅者
    Observable<JsonObject> mDelObservable;
    Subscriber<JsonObject> mDelSubscriber;
    int delPosition;

    @Override
    protected int setLayoutId() {
        _context=CollectionActivity.this;
        isShowMenu = false;
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar(R.drawable.back, "收藏");
    }


    @Override
    protected void initEvent() {
        //mRecyclerCollect.setListener(this);
    }


    @Override
    protected void initData() {
        mData =new LinkedList<CollectionBean.DatasBean>();
        //获取服务器收藏数据
        requestArticalList(pageNum);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().unsubscribe(mObservable);
        HttpRequest.getInstance().unsubscribe(mDelObservable);
    }


    @Override
    public void notifyByThemeChanged() {
        super.notifyByThemeChanged();
        if(mAdapter != null){
            mAdapter.notifyByThemeChanged();
        }
    }

    /**
     * 请求收藏列表
     * @param pageNum
     */
    private void requestArticalList(int pageNum){
        mSubscriber =new BaseSubscriber<BaseResponse<CollectionBean>>(CollectionActivity.this) {


            @Override
            public void onFail(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());
            }

            @Override
            public void onShowLoading() {
                ProgressDialogUtil.getInstance().mshowDialog(CollectionActivity.this);

            }

            @Override
            public void onHiddenLoading() {
                ProgressDialogUtil.getInstance().mdismissDialog();

            }

            @Override
            public void onResult(BaseResponse<CollectionBean> collectionBean) {
                mData =collectionBean.getData().getDatas();
                if(mData.size() != 0){
                    //给recyclerview填充数据
                    mAdapter =new CollectionAdapter(CollectionActivity.this,mData);
                    @SuppressLint("WrongConstant") LinearLayoutManager manager =new LinearLayoutManager(CollectionActivity.this,LinearLayoutManager.VERTICAL,false);
                    mRecyclerCollect.addItemDecoration(new DividerItemDecoration(CollectionActivity.this, DividerItemDecoration.VERTICAL));
                    mRecyclerCollect.setLayoutManager(manager);
                    mRecyclerCollect.setAdapter(mAdapter);
                    //添加侧滑监听
                    mRecyclerCollect.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(CollectionActivity.this));

                    mAdapter.setOnItemDelListener(new CollectionAdapter.OnItemDelListener() {
                        @Override
                        public void delItemPos(int position) {
                            //同时删除服务器数据
                            delPosition = position;
                            mData.remove(delPosition);
                            mAdapter.notifyDataSetChanged();
                            String articalId = String.valueOf(((CollectionBean.DatasBean) mData.get(position)).getId());
                            String originId = String.valueOf(((CollectionBean.DatasBean) mData.get(position)).getOriginId());
                            if (!TextUtils.isEmpty(articalId)) {
                                if(!TextUtils.isEmpty(originId)){
                                    delCollection(articalId ,originId);
                                }else{
                                    delCollection(articalId ,String.valueOf(-1));
                                }

                            }
                        }
                        });
                }else{
                    ToastCustomUtil.showShortToast("您还没有收藏上商家哦!");
                }


                mAdapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickPosition(int pos) {
                        String  linkUrl=((CollectionBean.DatasBean)mData.get(pos)).getLink();
                        XrouterRequest mXrouterRequest =XrouterRequest.create().putData("data",linkUrl).putActionName(X5ActionMessage.X5ACTIONNAME);
                        XrouterResponse mXrouterResponse=Xrouter.getInstance().senMessage(CollectionActivity.this,mXrouterRequest);
                    }
                });
            }
        };
        mObservable =HttpRequest.getInstance().getServiceInterface().collectArticalList(pageNum);
        HttpRequest.getInstance().toSubscribe(mObservable,mSubscriber);

    }


    /**
     * 删除收藏
     * @param originId 文件id
     */
    private void delCollection(String id,String originId){

        mDelSubscriber= new Subscriber<JsonObject>() {
            @Override
            public void onStart() {
                super.onStart();
                ProgressDialogUtil.getInstance().mshowDialog(CollectionActivity.this);
            }

            @Override
            public void onCompleted() {
                ProgressDialogUtil.getInstance().mdismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());
                ProgressDialogUtil.getInstance().mdismissDialog();

            }

            @Override
            public void onNext(JsonObject jsonObject) {
                try {
                    JSONObject mJSONObject =new JSONObject(jsonObject.toString());
                    if(!mJSONObject.getString("errorCode").equals("0")){
                        ToastCustomUtil.showLongToast(mJSONObject.getString("errorMsg"));
                    }else{
                    ToastCustomUtil.showShortToast(getString(R.string.toast_deletecolletcsuccess));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mDelObservable =HttpRequest.getInstance().getServiceInterface().deleteCollectArtical(id,originId);
        HttpRequest.getInstance().toSubscribe(mDelObservable,mDelSubscriber);
    }
}
