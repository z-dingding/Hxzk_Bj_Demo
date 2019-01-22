package com.hxzk_bj_demo.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hxzk_bj_demo.R;

import androidx.collection.LruCache;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by ${赵江涛} on 2018-1-11.
 * 作用:
 */

public class PagerAdapterBanner extends PagerAdapter {
    private String [] imgUrl;
    private Context mContext;
    private LruCache<Integer,Bitmap> mCache;

    public PagerAdapterBanner(String [] data,Context context){
        imgUrl = data;
        mContext = context;


        //初始化LruCache高效利用已经加载好的图片
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory * 3 / 8;  //缓存区的大小
        mCache = new LruCache<Integer, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();////返回Bitmap的大小
            }
        };
    }

    @Override
    public int getCount() {

        return imgUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vp_home,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_vp_home);


        //升级到4.8版本之后的新写法
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.errorview)
                .fallback(new ColorDrawable(Color.RED));

        Glide.with(mContext).load(imgUrl[position]).apply(requestOptions).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }


    /**
     * 判断时候内存缓存bitmap
     * @param id
     * @param imageView
     */
    public void loadBitmapIntoTarget(Integer id,ImageView imageView){
        //首先尝试从内存缓存中获取是否有对应id的Bitmap
        Bitmap bitmap = mCache.get(id);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else {
            //如果没有则开启异步任务去加载
            new LoadBitmapTask(imageView).execute(id);
        }

    }


    /**
     * 考虑在加载图片之前，对图片进行缩放
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height >= reqHeight || width > reqWidth){
            while ((height / (2 * inSampleSize)) >= reqHeight
                    && (width / (2 * inSampleSize)) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private class LoadBitmapTask extends AsyncTask<Integer,Void,Bitmap>{

        private ImageView imageView;

        public LoadBitmapTask(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;     //1、inJustDecodeBounds置为true，此时只加载图片的宽高信息
            BitmapFactory.decodeResource(mContext.getResources(),params[0],options);
            options.inSampleSize = calculateInSampleSize(options,
                    dp2px(mContext,240),
                    dp2px(mContext,360));          //2、根据ImageView的宽高计算所需要的采样率
            options.inJustDecodeBounds = false;    //3、inJustDecodeBounds置为false，正常加载图片
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),params[0],options);
            //把加载好的Bitmap放进LruCache内
            mCache.put(params[0],bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

    }



}
