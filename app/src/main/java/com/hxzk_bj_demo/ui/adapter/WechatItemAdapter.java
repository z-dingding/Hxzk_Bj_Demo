package com.hxzk_bj_demo.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.InversBean;
import com.hxzk_bj_demo.javabean.PublicListData;

import java.util.List;

//
//******************************************************************
// * * * * *   * * * *   *     *       Created by OCN.Yang
// * *     *   *         * *   *       Time:2017/2/24 13:09.
// * *     *   *         *   * *       Email address:ocnyang@gmail.com
// * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
// ******************************************************************



/**
 *
 * BaseQuickAdapter
 * 第一个泛型是数据实体类型，第二个BaseViewHolder是ViewHolder其目的是为了支持扩展ViewHolder。
 *
 *  BaseItemDraggableAdapter 如果添加拖拽,滑动删除则extends BaseItemDraggableAdapter
 *
 *  BaseMultiItemQuickAdapter 多类型的ItemType
 **/



public class WechatItemAdapter extends BaseQuickAdapter<PublicListData.DatasBean, BaseViewHolder> {

    public boolean isNotLoad;
    public int mImgWidth;
    public int mImgHeight;





    public WechatItemAdapter(List<PublicListData.DatasBean> data, boolean isNotLoadImg, int imgWidth, int imgHeight) {
        super(R.layout.item_wechat_style1, data); //Item的样式
        isNotLoad = isNotLoadImg;
        mImgWidth = imgWidth;
        mImgHeight = imgHeight;
    }


/**　补充:viewHolder.getLayoutPosition() 获取当前item的position */

    @Override
    protected void convert(BaseViewHolder helper, PublicListData.DatasBean item) {
                helper.setText(R.id.title_wechat_style1, TextUtils.isEmpty(item.getTitle()) ? mContext.getString(R.string.wechat_select) : item.getTitle())
                //item子控件的点击事件
                        .addOnClickListener(R.id.title_wechat_style1)
                         .addOnClickListener(R.id.img_collection_invest);
                if (!isNotLoad) {
                    if(helper.getPosition() % 2 == 0){
                        //求余的结果,随机选择图片,暂时将图片地址存到Author字段
                        String imgUrl="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4254193034,1760695166&fm=27&gp=0.jpg";

                        //升级到4.8版本之后的新写法
                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.lodingview)
                                .error(R.drawable.errorview)
                                .override(mImgWidth / 2, mImgWidth / 2)
                                .fallback(new ColorDrawable(Color.RED));

                        Glide.with(mContext.getApplicationContext())
                                .load(imgUrl) //加载图片的地址
                                .apply(requestOptions)
                                //淡入淡出动画
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into((ImageView) helper.getView(R.id.img_wechat_style));

                    }else{
                        String imgUrl="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1905162638,2896429914&fm=27&gp=0.jpg";
                        //升级到4.8版本之后的新写法
                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.lodingview)
                                .error(R.drawable.errorview)
                                .override(mImgWidth / 2, mImgWidth / 2)
                                .fallback(new ColorDrawable(Color.RED));

                        Glide.with(mContext.getApplicationContext())
                                .load(imgUrl)
                                .apply(requestOptions)
                                //淡入淡出动画
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into((ImageView) helper.getView(R.id.img_wechat_style));
                    }
                }


    }
}
