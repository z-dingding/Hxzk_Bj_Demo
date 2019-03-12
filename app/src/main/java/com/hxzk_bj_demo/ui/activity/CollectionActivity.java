package com.hxzk_bj_demo.ui.activity;
import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.BaseSubscriber;
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
        mRecyclerCollect.setListener(this);
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
    public void getPosition(int position) {

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
            public void onResult(BaseResponse<CollectionBean> collectionBean) {
                mData =collectionBean.getData().getDatas();
                if(mData.size() != 0){
                    //给recyclerview填充数据
                    mAdapter =new CollectionAdapter(CollectionActivity.this,mData);
                    @SuppressLint("WrongConstant") LinearLayoutManager manager =new LinearLayoutManager(CollectionActivity.this,LinearLayoutManager.VERTICAL,false);
                    mRecyclerCollect.addItemDecoration(new DividerItemDecoration(CollectionActivity.this, DividerItemDecoration.VERTICAL));
                    mRecyclerCollect.setLayoutManager(manager);
                    mRecyclerCollect.setAdapter(mAdapter);

                    mAdapter.setOnItemDelListener(new CollectionAdapter.OnItemDelListener() {
                        @Override
                        public void delItemPos(int position) {
                            //同时删除服务器数据
                            delPosition =position;
                            int articalId=((CollectionBean.DatasBean)mData.get(position)).getId();
                           if(!TextUtils.isEmpty(articalId+"")){
                               delCollection(articalId+"");
                           }
                        }
                    });
                }else{
                    ToastCustomUtil.showShortToast("您还没有收藏上商家哦!");
                }
            }
        };
        mObservable =HttpRequest.getInstance().getServiceInterface().collectArticalList(pageNum);
        HttpRequest.getInstance().toSubscribe(mObservable,mSubscriber);

    }


    /**
     * 删除收藏
     * @param articalId 文件id
     */
    private void delCollection(String articalId){

        mDelSubscriber= new Subscriber<JsonObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());

            }

            @Override
            public void onNext(JsonObject jsonObject) {
                try {
                    JSONObject mJSONObject =new JSONObject(jsonObject.toString());
                    if(!mJSONObject.getString("errorCode").equals("0")){
                        ToastCustomUtil.showLongToast(mJSONObject.getString("errorMsg"));
                    }else{
                        mData.remove(delPosition);
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() == 0) {
                            ToastCustomUtil.showShortToast("已经没数据啦");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mDelObservable =HttpRequest.getInstance().getServiceInterface().deleteCollectArtical(articalId);
        HttpRequest.getInstance().toSubscribe(mDelObservable,mDelSubscriber);
    }
}
