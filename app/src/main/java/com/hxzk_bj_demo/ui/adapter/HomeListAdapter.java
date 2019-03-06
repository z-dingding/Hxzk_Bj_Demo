package com.hxzk_bj_demo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.HomeListBean;
import com.hxzk_bj_demo.ui.adapter.base.BaseRecycleAdapter;
import com.hxzk_bj_demo.ui.adapter.base.BaseRecycleViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：created by ${zjt} on 2019/3/6
 * 描述:
 */
public class HomeListAdapter extends BaseRecycleAdapter {


    List<HomeListBean.DataBean.DatasBean> listData;

    public HomeListAdapter(Context context, List list, int resId) {
        super(context, list, resId);
        listData = list;
    }

    @Override
    public BaseRecycleViewHolder initViewHolder(View view) {

        BaseViewHolder baseViewHolder = new BaseViewHolder(view);
        return baseViewHolder;
    }


    @Override
    public void dealView(Context context, int position, BaseRecycleViewHolder holder) {
        holder.initData(context, listData.get(position), position);

    }


    private class BaseViewHolder extends BaseRecycleViewHolder {

        TextView tvTitle, tvAuthor, tvDate;
        CardView cardView;

        public BaseViewHolder(View view) {
            super(view);
        }

        @Override
        public void initView(View view) {
            super.initView(view);
            tvTitle = view.findViewById(R.id.tv_title_cardview);
            tvAuthor = view.findViewById(R.id.tv_author_cardview);
            tvDate = view.findViewById(R.id.tv_date_cardview);
             cardView=view.findViewById(R.id.cardview_homeitem);


        }

        @Override
        public void initData(Context context, Object bean, int position) {
            super.initData(context, bean, position);

            tvTitle.setText(((HomeListBean.DataBean.DatasBean) bean).getTitle());
            tvAuthor.setText(((HomeListBean.DataBean.DatasBean) bean).getAuthor());
            tvDate.setText(((HomeListBean.DataBean.DatasBean) bean).getNiceDate());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(position);
                    mOnItemClickLitener.onItemLongClick(position);
                }
            });
        }
    }



    OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick( int position);

        void onItemLongClick( int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        mOnItemClickLitener = onItemClickLitener;
    }
}
