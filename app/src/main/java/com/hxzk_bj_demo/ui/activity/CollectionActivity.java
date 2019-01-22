package com.hxzk_bj_demo.ui.activity;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.CollectionAdapter;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import com.hxzk_bj_demo.widget.CustomRecyclerView;
import org.litepal.crud.DataSupport;
import java.util.List;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by ${赵江涛} on 2018-1-24.
 * 作用:
 */

public class CollectionActivity extends BaseBussActivity implements CustomRecyclerView.onGetListener{


    @BindView(R.id.recycler_collect)
    CustomRecyclerView mRecyclerCollect;
   //数据源
    List<CollectionBean> mData;

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
        //获取数据库中的数据
         mData = DataSupport.findAll(CollectionBean.class);
        if(mData.size() != 0){
            //给recyclerview填充数据
            CollectionAdapter mAdapter =new CollectionAdapter(CollectionActivity.this,mData);
           LinearLayoutManager manager =new LinearLayoutManager(CollectionActivity.this,LinearLayoutManager.VERTICAL,false);
            mRecyclerCollect.addItemDecoration(new DividerItemDecoration(CollectionActivity.this, DividerItemDecoration.VERTICAL));
            mRecyclerCollect.setLayoutManager(manager);
            mRecyclerCollect.setAdapter(mAdapter);
        }else{
            ToastCustomUtil.showShortToast("您还没有收藏上商家哦!");
        }
    }


    @Override
    public void getPosition(int position) {
        ToastCustomUtil.showShortToast("您点击的商家名称是"+mData.get(position).getEntName());
    }
}
