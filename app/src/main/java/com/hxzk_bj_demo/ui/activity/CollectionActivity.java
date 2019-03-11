package com.hxzk_bj_demo.ui.activity;
import android.annotation.SuppressLint;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.CollectionAdapter;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.CustomRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.LinkedList;
import java.util.List;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ${赵江涛} on 2018-1-24.
 * 作用:
 */

public class CollectionActivity extends BaseBussActivity implements CustomRecyclerView.onGetListener{


    @BindView(R.id.recycler_collect)
    CustomRecyclerView mRecyclerCollect;
   //数据源
    List mData;

    Observable<BaseResponse<CollectionBean>> mObservable;
    Subscriber<BaseResponse<CollectionBean>> mSubscriber;

    int pageNum;
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
        mRecyclerCollect.setListener(this);
    }

    @Override
    protected void initData() {
        mData =new LinkedList<CollectionBean.DatasBean>();
        //获取数据库中的数据
        requestArticalList(pageNum);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().unsubscribe(mObservable);
    }

    @Override
    public void getPosition(int position) {

    }



    /**
     * 请求收藏列表
     * @param pageNum
     */
    private void requestArticalList(int pageNum){
        mSubscriber =new Subscriber<BaseResponse<CollectionBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                String a =e.getMessage().toString();
               ToastCustomUtil.showLongToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<CollectionBean> collectionBean) {
                mData =collectionBean.getData().getDatas();
                if(mData.size() != 0){
                    //给recyclerview填充数据
                    CollectionAdapter mAdapter =new CollectionAdapter(CollectionActivity.this,mData);
                    @SuppressLint("WrongConstant") LinearLayoutManager manager =new LinearLayoutManager(CollectionActivity.this,LinearLayoutManager.VERTICAL,false);
                    mRecyclerCollect.addItemDecoration(new DividerItemDecoration(CollectionActivity.this, DividerItemDecoration.VERTICAL));
                    mRecyclerCollect.setLayoutManager(manager);
                    mRecyclerCollect.setAdapter(mAdapter);
                }else{
                    ToastCustomUtil.showShortToast("您还没有收藏上商家哦!");
                }


            }
        };
        mObservable =HttpRequest.getInstance().getServiceInterface().collectArticalList(pageNum);
        HttpRequest.getInstance().toSubscribe(mObservable,mSubscriber);

    }
}
