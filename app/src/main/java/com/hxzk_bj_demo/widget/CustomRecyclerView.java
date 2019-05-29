package com.hxzk_bj_demo.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.adapter.CollectionAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by tangyangkai on 16/6/12.
 */

public class CustomRecyclerView extends RecyclerView {




    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove, mTouchSlop, xUp, yUp;
    private Scroller mScroller;
    private TextView textView;
    private ImageView imageView;
    private boolean isFirst = true;
    private onGetListener listener;



    public interface onGetListener {
        void getPosition(int position);
    }

    public void setListener(onGetListener listener) {
        this.listener = listener;
    }

    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //滑动到最小距离
        //getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //滑动的最大距离
        maxLength = ((int) (180 * context.getResources().getDisplayMetrics().density + 0.5f));
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }


    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {  //按下
                xDown = x;
                yDown = y;
                //通过点击的坐标计算当前的position
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                //rect这个对象是用来存储成对出现的参数，比如，一个矩形框的左上角坐标、宽度和高度
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);//getHitRect()找到控件占据的矩形区域的矩形坐标
                        if (frame.contains(x, y)) {//否点击到该控件上
                            pos = mFirstPosition + i;
                        }
                    }
                }
                //通过position得到item的viewHolder,viewHolder是存放视图与数据的地方
                View view = getChildAt(pos - mFirstPosition);
                CollectionAdapter.MyViewHolder viewHolder = (CollectionAdapter.MyViewHolder) getChildViewHolder(view);
                itemLayout = viewHolder.layout;
                //变动二
                //textView = (TextView) itemLayout.findViewById(R.id.item_delete_txt);
                //imageView = (ImageView) itemLayout.findViewById(R.id.item_delete_img);
            }
            break;

            case MotionEvent.ACTION_MOVE: {//移动，在不停的变化中
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {//y轴小于最小距离，x轴大于最小距离
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;//变化x的距离
                    if (newScrollX < 0 && scrollX <= 0) {//往右滑动
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {//往左滑动
                        newScrollX = 0;
                    }
                    if (scrollX > maxLength / 2) {//移动距离超1/2
                        textView.setVisibility(GONE);
                        imageView.setVisibility(VISIBLE);


                        if (isFirst) {
                            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.2f, 1f);
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.2f, 1f);
                            AnimatorSet animSet = new AnimatorSet();
                            animSet.play(animatorX).with(animatorY);
                            animSet.setDuration(800);
                            animSet.start();
                            isFirst = false;
                        }
                    } else {
                        textView.setVisibility(VISIBLE);
                        imageView.setVisibility(GONE);
                    }

                    itemLayout.scrollBy(newScrollX, 0);

                }
            }
            break;
            case MotionEvent.ACTION_UP: {//抬起


                xUp = x;
                yUp = y;
                int dx = xUp - xDown;
                int dy = yUp - yDown;
                if (Math.abs(dy) < mTouchSlop && Math.abs(dx) < mTouchSlop) {//坐标移动小于最小距离
                    listener.getPosition(pos);
                } else {
                    int scrollX = itemLayout.getScrollX();
                    if (scrollX > maxLength / 2) {//移动距离大于1/2
                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //变动一
                              //  ((CollectionAdapter) getAdapter()).removeRecycle(pos);
                            }
                        });

                    } else {
                        //startScroll()四个参数依次为：开始移动时的X坐标；开始移动时的Y坐标；沿X轴移动距离，为负时，子控件向右移动；沿Y轴移动
                        mScroller.startScroll(scrollX, 0, -scrollX, 0);
                        //invalidate()是用来刷新View的，必须是在UI线程中进行工作。
                        invalidate();
                    }
                    isFirst = true;
                }
            }


            break;
        }
        mStartX = x;
        return super.onTouchEvent(event);
    }


    /**
     * 主要功能是计算拖动的位移量、更新背景、设置要显示的屏
     * true说明滚动尚未完成，false说明滚动已经完成。
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
