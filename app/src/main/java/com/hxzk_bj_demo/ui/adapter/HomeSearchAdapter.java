package com.hxzk_bj_demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.HomeSearchBean;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 作者：created by ${zjt} on 2019/3/7
 * 描述:首页搜索adapter
 */
public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.HomeSearchViewHolder> {

    Context mContext;
    List<HomeSearchBean.DatasBean> mListDatas;

    public HomeSearchAdapter(Context context, List<HomeSearchBean.DatasBean> listDatas) {
        this.mContext = context;
        this.mListDatas = listDatas;
    }


    @NonNull
    @Override
    public HomeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_homesearch, parent, false);
        return new HomeSearchViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull HomeSearchViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (position <= 2) {
            holder.tvRanking.setTextColor(mContext.getResources().getColor(R.color.homesearch_cacel));
        }
        holder.tvRanking.setText((position + 1) + "");
        holder.tvTitle.setText(mListDatas.get(position).getTitle());
        holder.itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(mListDatas.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListDatas.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class HomeSearchViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLinear;
        TextView tvRanking;
        TextView tvTitle;

        public HomeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLinear = itemView.findViewById(R.id.linear_item);
            tvRanking = itemView.findViewById(R.id.tv_position);
            tvTitle = itemView.findViewById(R.id.tv_titleitem);
        }
    }


    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(HomeSearchBean.DatasBean homeListBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }
}
