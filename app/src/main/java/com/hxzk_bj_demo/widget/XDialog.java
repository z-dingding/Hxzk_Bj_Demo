package com.hxzk_bj_demo.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * 加载中Dialog
 * 
 * @author xm
 */
public class XDialog extends AlertDialog {

	private TextView    tips_loading_msg;
	private ProgressBar tips_loading_img;

	Context mContext;

	private String message = null;
	private AVLoadingIndicatorView avi;

	public XDialog(Context context) {
		super(context);
		message = "加载中...";
		mContext = context;
	}

	public XDialog(Context context, String message) {
		super(context);
		this.message = message;
		mContext = context;
		this.setCancelable(true);
	}

	public XDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
		mContext = context;
		this.setCancelable(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xdg_loading);
		tips_loading_img = (ProgressBar) findViewById(R.id.progressbar);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
		avi.show();
		if (TextUtils.isEmpty(message)) {
			tips_loading_msg.setVisibility(View.GONE);
		} else {
			tips_loading_msg.setText(this.message);
		}
		// // 加载动画
		// Animation loadingAnim = AnimationUtils.loadAnimation(mContext,
		// R.anim.xdg_loading);
		// // 使用ImageView显示动画
		// tips_loading_img.startAnimation(loadingAnim);

	}

	public void setText(String message) {
		this.message = message;
		tips_loading_msg.setText(this.message);
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

	@Override
	public void dismiss() {
		try {
			avi.hide();
			super.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
