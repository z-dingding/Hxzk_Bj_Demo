package com.hxzk_bj_demo.widget.xrecyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxzk_bj_demo.R;

import androidx.core.content.ContextCompat;

/**
 * 作者：created by ${zjt} on 2019/3/6
 * 描述:
 */
public class WRecyclerViewFooter extends LinearLayout {
    private Context mContext;
    /**正常状态*/
    public final static int STATE_NORMAL = 0;
    /**准备状态*/
    public final static int STATE_READY = 1;
    /**加载状态*/
    public final static int STATE_LOADING = 2;

    /**根节点*/
    private View mContentView;
    /**含有进度条的布局区域*/
    private View mProgressBarLayout;
    /**提示文字View*/
    private TextView mHintView;

    public WRecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }
    public WRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**初始化*/
    private void initView(Context context) {
        mContext = context;

        //添加底部上拉加载区域布局文件
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.wrecyclerview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        //实例化组件
        mContentView = moreView.findViewById(R.id.wrecyclerview_footer_content);
        mProgressBarLayout = moreView.findViewById(R.id.wrecyclerview_footer_progressbar_layout);
        mHintView = (TextView)moreView.findViewById(R.id.wrecyclerview_footer_hint_textview);
    }

    /**更改加载状态：
     * @param state - STATE_NORMAL(0),STATE_READY(1),STATE_LOADING(2)*/
    public void setState(int state) {
        //首先，将提示文字和进度条区域初始化隐藏
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBarLayout.setVisibility(View.INVISIBLE);
        //然后，根据状态值进行显示，隐藏这两个区域
        if (state == STATE_READY) {
            //准备状态
            mHintView.setVisibility(View.VISIBLE);
            Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.wrecyclerview_icon_pull);
            //setCompoundDrawables 画的drawable的宽高是按drawable.setBound()设置的宽高
            //而setCompoundDrawablesWithIntrinsicBounds是画的drawable的宽高是按drawable固定的宽高，即通过getIntrinsicWidth()与getIntrinsicHeight()自动获得
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mHintView.setCompoundDrawables(null, drawable, null, null);
            mHintView.setText(R.string.wrecyclerview_footer_hint_ready);
        } else if (state == STATE_LOADING) {
            //加载状态
            mProgressBarLayout.setVisibility(View.VISIBLE);
        } else {
            //正常状态
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setCompoundDrawables(null, null, null, null);
            mHintView.setText(R.string.wrecyclerview_footer_hint_normal);
        }
    }

    /**
     * 当禁用上拉加载功能的时候隐藏底部区域
     */
    public void hide() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.height = 0;//这里设为0，那么虽然是显示，但是看不到
        mContentView.setLayoutParams(lp);
    }
    /**
     * 显示底部上拉加载区域
     */
    public void show() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    /**设置布局的底外边距【暂时没有用到】*/
    public void setBottomMargin(int height) {
        if (height < 0) return ;
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }
    /**获取布局的底外边距【暂时没有用到】*/
    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
        return lp.bottomMargin;
    }
}
