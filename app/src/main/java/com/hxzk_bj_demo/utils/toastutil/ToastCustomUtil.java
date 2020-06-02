package com.hxzk_bj_demo.utils.toastutil;

import android.view.Gravity;
import android.widget.Toast;

import com.hxzk_bj_demo.common.MainApplication;


/**
 * Create By HaiyuKing
 * Used 简单的Toast封装类,自定义Toast的显示风格
 */
public class ToastCustomUtil {

	private static ToastCustom toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

	/**
	 * 短时间显示Toast【居下】
	 * @param msg 显示的内容-字符串*/
	public static void showShortToast(String msg) {
		if(MainApplication.getAppContext() != null){
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}
	/**
	 * 短时间显示Toast【居中】
	 * @param msg 显示的内容-字符串*/
	public static void showShortToastCenter(String msg){
		if(MainApplication.getAppContext() != null) {
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}

	/**
	 * 短时间显示Toast【居上】
	 * @param msg 显示的内容-字符串*/
	public static void showShortToastTop(String msg){
		if(MainApplication.getAppContext() != null) {
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP, 0, 0);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}

	/**
	 * 长时间显示Toast【居下】
	 * @param msg 显示的内容-字符串*/
	public static void showLongToast(String msg) {
		if(MainApplication.getAppContext() != null) {
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_LONG);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}
	/**
	 * 长时间显示Toast【居中】
	 * @param msg 显示的内容-字符串*/
	public static void showLongToastCenter(String msg){
		if(MainApplication.getAppContext() != null) {
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}
	/**
	 * 长时间显示Toast【居上】
	 * @param msg 显示的内容-字符串*/
	public static void showLongToastTop(String msg){
		if(MainApplication.getAppContext() != null) {
			if (toast == null) {
				toast = ToastCustom.makeText(MainApplication.getAppContext(), msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 0);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
	}
}
