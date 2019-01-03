package com.hxzk_bj_demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.CollectionBean;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${赵江涛} on 2018-1-25.
 * 作用:
 */

public class CollectionAdapter extends RecyclerView.Adapter {



    private Context context;
    private LayoutInflater inflater;
    private List<CollectionBean> lists = new ArrayList<>();

    public CollectionAdapter(Context context, List<CollectionBean> lists) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(inflater.inflate(R.layout.item_recycler_collection, null, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        CollectionBean collectionBean =lists.get(position);

        Glide.with(context.getApplicationContext())
                .load(lists.get(position).getImgUrl()) //加载图片的地址
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.errorview)
                //在此处设置了图片的大小
                .crossFade(1000)//淡入淡出动画
                .into(viewHolder.imgPhoto);
        viewHolder.tvName.setText(collectionBean.getEntName());
        viewHolder.tvTime.setText(collectionBean.getTiem());
        viewHolder.tvAddress.setText(collectionBean.getEntAddress());

        viewHolder.layout.scrollTo(0, 0);

    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }
    }

    public void removeRecycle(int position) {

        //同时删除数据库保存的数据
        int deleteNum = DataSupport.deleteAll(CollectionBean.class, "entName = ?",(lists.get(position)).getEntName());
        if(deleteNum != 0){
            ToastCustomUtil.showShortToast("删除了"+deleteNum+"条数据");
        }
        lists.remove(position);
       notifyDataSetChanged();
        if (lists.size() == 0) {
            ToastCustomUtil.showShortToast("已经没数据啦");
        }
    }



    /**
     * Created by tangyangkai on 16/6/12.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView  tvName;
        TextView  tvTime;
        TextView  tvAddress;


        public ImageView img;
        public LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            img= (ImageView) itemView.findViewById(R.id.item_delete_img);
            layout= (LinearLayout) itemView.findViewById(R.id.item_recycler_ll);


            imgPhoto= (ImageView) itemView.findViewById(R.id.imgitem_photo_colleciton);
            tvName= (TextView) itemView.findViewById(R.id.tvitem_entname_colleciton);
            tvTime=(TextView) itemView.findViewById(R.id.tvitem_showtime_collection);;
            tvAddress=(TextView) itemView.findViewById(R.id.tvitem_entaddress_collection);

        }
    }

}
