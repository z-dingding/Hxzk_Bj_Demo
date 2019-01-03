package com.hxzk_bj_demo.utils.toastutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hxzk_bj_demo.R;


/**
 * Create By HaiyuKing
 * Used 自定义Toast显示风格，基于系统Toast【可以控制显示样式、位置，不可以控制显示时间、动画，不可触发】
 * 注意 Toast布局在源码中的布局是采用LinearLayout
 */
public class ToastCustom {

	private static ToastCustom toastCustom;
	private Toast toast;

	public static ToastCustom makeText(Context context, CharSequence text , int duration){
		LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflate.inflate(R.layout.toast_custom_view, null);
		TextView tv = (TextView)view.findViewById(R.id.tv_toast);
		tv.setText(text);
		if (toastCustom == null) {
			toastCustom = new ToastCustom();
		}
		toastCustom.toast = new Toast(context);
		toastCustom.toast.setView(view);
		toastCustom.toast.setDuration(duration);

		return toastCustom;
	}



	public static ToastCustom makeText(Context context, int resId, int duration){
		return ToastCustom.makeText(context,context.getResources().getString(resId),duration);
	}

	public void show(){
		toast.show();
	}

	/**
	 * 1、gravity是输入Toast需要显示的位置，例如CENTER_VERTICAL（垂直居中）、CENTER_HORIZONTAL（水平居中）、TOP（顶部）等等。
	 * 2、xOffset则是决定Toast在水平方向（x轴）的偏移量，偏移量单位为，大于0向右偏移，小于0向左偏移
	 * 3、yOffset决定Toast在垂直方向（y轴）的偏移量，大于0向下偏移，小于0向上偏移，想设大值也没关系，反正Toast不会跑出屏幕。*/
	public void setGravity(int gravity, int xOffset, int yOffset) {
		toast.setGravity(gravity, xOffset, yOffset);
	}

	public void setText(CharSequence s){
		TextView tv = (TextView) toast.getView().findViewById(R.id.tv_toast);
		tv.setText(s);
	}

	public void setText(int resId){
		TextView tv = (TextView) toast.getView().findViewById(R.id.tv_toast);
		tv.setText(resId);
	}

}
