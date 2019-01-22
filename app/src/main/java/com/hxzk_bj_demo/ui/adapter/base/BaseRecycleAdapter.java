package com.hxzk_bj_demo.ui.adapter.base;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {

    private Context context;

    private List<T> list;

    private int resId;

    public BaseRecycleAdapter(Context context, List<T> list, int resId) {
        this.context = context;
        this.list = list;
        this.resId = resId;
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, resId, null);
        return initViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(BaseRecycleViewHolder holder, int position) {
        dealView(context, position, holder);
    }

    public T getItem(int postion){
        return list.get(postion);
    }


    /**
     * 调用此方法删除itemo
     * @param position
     */
    public void removeRecycle(int position) {
        list.remove(position);
        //同时删除数据库保存的数据
        int deleteNum =DataSupport.deleteAll(CollectionBean.class, "entName = ?",((CollectionBean)list.get(position)).getEntName());
        if(deleteNum != 0){
            ToastCustomUtil.showShortToast("删除了"+deleteNum+"条数据");
        }
         this.notifyDataSetChanged();
        if (list.size() == 0) {
          ToastCustomUtil.showShortToast("已经没数据啦");
        }
    }


    /**
     * 初始化ViewHolder
     * */
    public abstract BaseRecycleViewHolder initViewHolder(View view);

    /**
     * 处理视图
     * */
    public abstract void dealView(Context context, int position, BaseRecycleViewHolder holder);

}
