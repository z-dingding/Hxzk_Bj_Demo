package com.hxzk_bj_demo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.hxzk.bj.common.X5ActionMessage;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.HomeSearchBean;
import com.hxzk_bj_demo.javabean.SearchTagBean;
import com.hxzk_bj_demo.network.BaseResponse;
import com.hxzk_bj_demo.network.HttpRequest;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.HomeSearchAdapter;
import com.hxzk_bj_demo.utils.AvoidClickAgainUtil;
import com.hxzk_bj_demo.utils.KeyboardUtil;
import com.hxzk_bj_demo.utils.LogUtil;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.DrawableTextView;
import com.xzt.xrouter.router.Xrouter;
import com.xzt.xrouter.router.XrouterRequest;
import com.xzt.xrouter.router.XrouterResponse;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.hxzk_bj_demo.R2.id.linear_historysearch;

/**
 * 作者：created by ${zjt} on 2019/3/7
 * 描述:首页搜索Activity
 */
public class HomeSearchActivity extends BaseBussActivity {
    private static final String TAG = "HomeSearchActivity";


    @BindView(R.id.tv_search)
    ImageView tvSearch;
    @BindView(R.id.et_seachcontent)
    EditText etSeachcontent;
    @BindView(R.id.tv_cancelsearch)
    TextView tvCancelsearch;

    Observable<BaseResponse<HomeSearchBean>> observable;
    Subscriber<BaseResponse<HomeSearchBean>> subscriber;


    //页码
    int mPageNum = 0;
    //总页数
    int allPageNum;
    @BindView(R.id.recycler_result)
    RecyclerView recyclerResult;
    HomeSearchAdapter mHomeSearchAdapter;
    //搜索结果集合
    List<HomeSearchBean.DatasBean> mSearchResultData;
    /**
     * 历史搜索flexbox的数据源
     */
    ArrayList<SearchTagBean> list_history;
    /**
     * 记录点击的是哪个ScreenBean对象
     */
    SearchTagBean clickSearchTagBean;
    
    @BindView(R.id.tv_clearhistory)
    TextView tvClearhistory;
    @BindView(R.id.linear_historysearch)
    LinearLayout  linearHoistorySearch;
    @BindView(R.id.flexbox_historysearch)
    FlexboxLayout historyFlexbox;
    @BindView(R.id.tv_batchSearch)
    TextView tvBatchSearch;
    @BindView(R.id.iv_batchSearch)
    ImageView ivBatchSearch;
    @BindView(R.id.realtive_searchbitch)
    RelativeLayout relativeSearchBitch;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111:
                    if (mHomeSearchAdapter == null) {
                        mHomeSearchAdapter = new HomeSearchAdapter(HomeSearchActivity.this, mSearchResultData);
                        recyclerResult.setAdapter(mHomeSearchAdapter);
                        mHomeSearchAdapter.setOnItemClickListener(new HomeSearchAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(HomeSearchBean.DatasBean homeListBean) {
                                //封装传递的请求数据到XrouterRequest
                                XrouterRequest mXrouterRequest =XrouterRequest.create().putData("data",homeListBean.getApkLink()).putActionName(X5ActionMessage.X5ACTIONNAME);
                                XrouterResponse mXrouterResponse=Xrouter.getInstance().senMessage(HomeSearchActivity.this,mXrouterRequest);
                            }
                        });
                    } else {
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
        etSeachcontent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    mSearchResultData.clear();
                    mHomeSearchAdapter.notifyDataSetChanged();
                    if(list_history.size() != 0){
                        linearHoistorySearch.setVisibility(View.VISIBLE);
                        relativeSearchBitch.setVisibility(View.GONE);
                       }
                }
            }
        });
        etSeachcontent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    KeyboardUtil.hideKeyboard(etSeachcontent);
                    //执行搜索操作
                    String keyWord = etSeachcontent.getText().toString();
                    if (!TextUtils.isEmpty(keyWord)) {
                        requestSearch(mPageNum, keyWord);
                    } else {
                        ToastCustomUtil.showLongToast(getString(R.string.content_is_empty));
                    }
                    return true;
                }
                return false;
            }
        });

    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        super.initData();
        list_history=new ArrayList<>();
        //每次进入该activity就先查询之前保存的历史记录
        list_history = (ArrayList<SearchTagBean>) DataSupport.findAll(SearchTagBean.class);

        for (int i = 0; i < list_history.size(); i++) {
            historyFlexbox.addView(createFlexItemTextView(list_history.get(i), R.drawable.shape_screening_pop_item, false));
        }

        if (list_history.size() == 0) {
            linearHoistorySearch.setVisibility(View.GONE);
        }
            relativeSearchBitch.setVisibility(View.GONE);


        //设置为线性管理器
        recyclerResult.setLayoutManager(new LinearLayoutManager(HomeSearchActivity.this));
        //给item增加自带分割线
        recyclerResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchResultData = new LinkedList<>();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().unsubscribe(observable);
    }

    @OnClick({R.id.tv_search, R.id.tv_cancelsearch,R.id.tv_clearhistory, R.id.tv_batchSearch,R.id.iv_batchSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                //调用避免重复点击的工具类
                if(!AvoidClickAgainUtil.isFastDoubleClick()){
                    String keyWord = etSeachcontent.getText().toString();
                    if (!TextUtils.isEmpty(keyWord)) {
                        requestSearch(mPageNum, keyWord);
                    } else {
                        ToastCustomUtil.showLongToast(getString(R.string.content_is_empty));
                    }
                }else{
                    ToastCustomUtil.showLongToast(getString(R.string.avoidClickAgain));

                }
                break;

            case R.id.tv_cancelsearch:
                finishActivity();
                break;

            case R.id.tv_clearhistory:
                if (DataSupport.deleteAll(SearchTagBean.class) != 0) {
                    list_history.clear();
                    LogUtil.e(TAG,"已经清空所有记录");
                    historyFlexbox.setVisibility(View.GONE);
                    linearHoistorySearch.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_batchSearch:
            case R.id.iv_batchSearch:
                if(mPageNum < allPageNum){
                    mSearchResultData.clear();
                    requestSearch(++mPageNum,etSeachcontent.getText().toString());
                }else{
                 ToastCustomUtil.showLongToast(getResources().getString(R.string.search_endbatch));
                }
                break;
        }
    }



    /**
     * 动态创建TextView,并添加到FlexBox
     *
     * @param searchTagBean
     * @return
     */
    @SuppressLint("ResourceType")
    private TextView createFlexItemTextView(final SearchTagBean searchTagBean, int drawableRes, boolean isHotSearch) {
        final DrawableTextView textView = new DrawableTextView(HomeSearchActivity.this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(searchTagBean.getName());
        textView.setTextSize(12);
        textView.setTextColor(this.getResources().getColor(R.color.homesearch_historyText));
        textView.setBackgroundResource(drawableRes);
        if (!isHotSearch) {//通过boolean值区分热门搜索和历史搜索的样式（历史搜索多一个x）
            textView.setTextColor(this.getResources().getColor(R.color.white));
            Drawable deleteIcon = getResources().getDrawable(R.drawable.del);
            deleteIcon.setBounds(0, 0, deleteIcon.getMinimumWidth(), deleteIcon.getMinimumHeight());
            textView.setCompoundDrawables(null, null, deleteIcon, null);
            textView.setCompoundDrawablePadding(30);//设置图片和text之间的间距
            textView.setPadding(50, 0, 0, 0);

        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSearchTagBean = searchTagBean;
                if (!TextUtils.isEmpty(etSeachcontent.getText().toString())) {
                    etSeachcontent.setText("");
                }
                etSeachcontent.setText(clickSearchTagBean.getName());
                //上个搜索的结果要清空
                mSearchResultData.clear();
                //搜索历史记录要隐藏
                linearHoistorySearch.setVisibility(View.GONE);
                requestSearch(0,etSeachcontent.getText().toString());

            }
        });

        textView.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {

                int deleteNum = DataSupport.deleteAll(SearchTagBean.class,"name = ?",searchTagBean.getName());
                if(deleteNum != 0){
                    LogUtil.e(TAG,"删除成功");
                    textView.setVisibility(View.GONE);
                }

            }
        });

        int padding = dpToPixel(this, 4);
        int paddingLeftAndRight = dpToPixel(this, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = dpToPixel(this, 6);
        int marginTop = dpToPixel(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public int dpToPixel(Activity context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }



    /**
     * 搜索请求
     */
    private void requestSearch(int pageNum, String key) {
        mPageNum=pageNum;
        boolean isHasKeyWord = false;
        //保存搜索记录之前先判断之前又没有存储过,再次查询查询保存的历史记录
        list_history = (ArrayList<SearchTagBean>) DataSupport.findAll(SearchTagBean.class);
        if(list_history.size()>0){
            for (int i = 0; i < list_history.size(); i++) {
                if (list_history.get(i).getName().equals(key)) {
                    isHasKeyWord = true;
                    break;
                } else {
                    isHasKeyWord = false;
                }
            }

        }
        if (!isHasKeyWord) {
            SearchTagBean searchTagBean = new SearchTagBean();
            searchTagBean.setName(key);
            searchTagBean.setIsSelect("false");
            if (searchTagBean.save()) {
              LogUtil.e(TAG, "搜索记录已保存");
            }
        }

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
                //获取当前搜索内容返回页数
                allPageNum=homeSearchBeanBaseResponse.getData().getPageCount();
                if(homeSearchBeanBaseResponse.getData().getDatas().size() >0){
                    for (int i = 0; i < homeSearchBeanBaseResponse.getData().getDatas().size(); i++) {
                        mSearchResultData.add(homeSearchBeanBaseResponse.getData().getDatas().get(i));
                    }
                }else{
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastCustomUtil.showLongToast(getString(R.string.search_noresult));
                        }
                    });

                }

                if (mSearchResultData.size() != 0) {
                    relativeSearchBitch.setVisibility(View.VISIBLE);
                    linearHoistorySearch.setVisibility(View.GONE);
                }else{
                    relativeSearchBitch.setVisibility(View.GONE);
                    linearHoistorySearch.setVisibility(View.VISIBLE);
                }
                mHandler.sendEmptyMessage(0x111);
            }

        };

        observable = HttpRequest.getInstance().getServiceInterface().homeSearch(pageNum, key);
        HttpRequest.getInstance().toSubscribe(observable, subscriber);

    }


}
