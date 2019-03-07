package com.hxzk_bj_demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.HomeSearchBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.HomeSearchAdapter;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 作者：created by ${zjt} on 2019/3/7
 * 描述:首页搜索Activity
 */
public class HomeSearchActivity extends BaseBussActivity {


    @BindView(R.id.tv_search)
    ImageView tvSearch;
    @BindView(R.id.et_seachcontent)
    EditText etSeachcontent;
    @BindView(R.id.tv_cancelsearch)
    TextView tvCancelsearch;

    Observable<BaseResponse<HomeSearchBean>> observable;
    Subscriber<BaseResponse<HomeSearchBean>> subscriber;


    //页码
    int mPageNum =0;
    @BindView(R.id.recycler_result)
    RecyclerView recyclerResult;
    HomeSearchAdapter mHomeSearchAdapter;
    //搜索结果集合
    List<HomeSearchBean.DatasBean> mSearchResultData;


    Handler mHandler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x111:
                    if(mHomeSearchAdapter == null){
                        mHomeSearchAdapter =new HomeSearchAdapter(HomeSearchActivity.this,mSearchResultData);
                        recyclerResult.setAdapter(mHomeSearchAdapter);
                    }else{
                        mHomeSearchAdapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };


    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initView() {
        super.initView();
        //隐藏toolbar
        toolbarVisible(View.GONE);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
    }


    @Override
    protected void initData() {
        super.initData();
        //设置为线性管理器
        recyclerResult.setLayoutManager(new LinearLayoutManager(this));
        //给item增加自带分割线
        recyclerResult.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mSearchResultData =new LinkedList<>();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().unsubscribe(observable);
    }

    @OnClick({R.id.tv_search, R.id.tv_cancelsearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String key = etSeachcontent.getText().toString();
                if (!TextUtils.isEmpty(key)) {
                    requestSearch(mPageNum,key);
                } else {
                    ToastCustomUtil.showLongToast(getString(R.string.content_is_empty));
                }

                break;
            case R.id.tv_cancelsearch:
                finishActivity();
                break;
        }
    }


    /**
     * 搜索请求
     */
    private void requestSearch(int pageNum,String key) {

        subscriber = new Subscriber<BaseResponse<HomeSearchBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastCustomUtil.showLongToast(e.getMessage());

            }

            @Override
            public void onNext(BaseResponse<HomeSearchBean> homeSearchBeanBaseResponse) {
                for(int i=0;i<homeSearchBeanBaseResponse.getData().getDatas().size();i++){
                    mSearchResultData.add(homeSearchBeanBaseResponse.getData().getDatas().get(i));
                }
                mHandler.sendEmptyMessage(0x111);
            }

        };

        observable = HttpRequest.getInstance().getServiceInterface().homeSearch(pageNum,key);
        HttpRequest.getInstance().toSubscribe(observable, subscriber);

    }


}
