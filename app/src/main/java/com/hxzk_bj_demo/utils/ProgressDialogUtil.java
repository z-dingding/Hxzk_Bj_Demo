package com.hxzk_bj_demo.utils;

import android.content.Context;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.widget.XDialog;

/**
 * 作者：created by ${zjt} on 2019/3/1
 * 描述:登录等加载进度框
 */
public class ProgressDialogUtil {


    public static ProgressDialogUtil progressDialog;
    private  static XDialog loadingDialog;


    private ProgressDialogUtil() {
    }



    public static ProgressDialogUtil getInstance(){
        if(progressDialog == null){
           synchronized (ProgressDialogUtil.class){
               if(progressDialog == null){
                   progressDialog =new ProgressDialogUtil();
                   return progressDialog;
               }
           }
        }
        return progressDialog;
    }



    /**
     * 显示dialog
     * @param mContext 上下文
     */
    public void mshowDialog(Context mContext) {
        if (null == loadingDialog) {
            loadingDialog = new XDialog(mContext, R.style.Dialog_image, "");
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        } else {
            if (!loadingDialog.isShowing()){
                loadingDialog.show();
            }
        }
    }


    /**
     * 让dialog消失
     */
    public void mdismissDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }


    public XDialog  xDialogInstance(){
        return loadingDialog;
    }
}
