package com.hxzk.bj.x5webview.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.hxzk.bj.x5webview.ImageBrowserActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.BitmapLuminanceSource;
import com.uuzuche.lib_zxing.decoding.DecodeFormatManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by zhuguohui on 2018/10/9.
 */

public class Mobile {
    Context context;
    Handler handler = new Handler(Looper.getMainLooper());
    ArrayList<String> imageURLList = new ArrayList<>();

    public Mobile(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void scanCode(final String imageUrl) {
        if (scanListener != null) {
            scanListener.onScanStart();
        }
        if (TextUtils.isEmpty(imageUrl)) {
            if (scanListener != null) {
                scanListener.onScanFailed("图片地址为空");
            }
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getImage(imageUrl);
                }
            });
           // getImage(imageUrl);

        }
    }

    @JavascriptInterface
    public void addImageUrl(String url) {
        imageURLList.add(url);
    }

    @JavascriptInterface
    public void showImage(String url) {
        Intent intent = new Intent(context, ImageBrowserActivity.class);
        intent.putStringArrayListExtra(ImageBrowserActivity.IMAGE_BROWSER_LIST, imageURLList);
        intent.putExtra(ImageBrowserActivity.IMAGE_BROWSER_INIT_SRC, url);
        context.startActivity(intent);
    }

    private void getImage(final String imageUrl) {

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                Bitmap myBitmap = null;
                try {
                    bitmap = Glide.with(context.getApplicationContext())
                            .asBitmap()
                            .load(imageUrl)
                            .submit(360, 480).get();
                     myBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return myBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                CodeUtil.analyzeBitmap(bitmap, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        if (scanListener != null) {
                            scanListener.onScanSuccess(result);
                        }
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        if (scanListener != null) {
                            scanListener.onScanFailed("未识别到二维码");
                        }
                    }
                });
            }
        }.execute();

    }


//    private void getImage(String imageUrl) {
//        Glide.with(context.getApplicationContext()).load(imageUrl)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//
//                        CodeUtil.analyzeBitmap(resource, new AnalyzeCallback() {
//                            @Override
//                            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                                if (scanListener != null) {
//                                    scanListener.onScanSuccess(result);
//                                }
//                            }
//
//                            @Override
//                            public void onAnalyzeFailed() {
//                                if (scanListener != null) {
//                                    scanListener.onScanFailed("未识别到二维码");
//                                }
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//                        if (scanListener != null) {
//                            scanListener.onScanFailed("加载图片失败[" + e.getMessage() + "]");
//                        }
//                    }
//                });
//    }

    public interface ScanListener {
        void onScanStart();

        void onScanFailed(String info);

        void onScanSuccess(String result);
    }

    private ScanListener scanListener;

    public void setScanListener(ScanListener scanListener) {
        this.scanListener = scanListener;
    }



}
