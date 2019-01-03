package com.hxzk_bj_demo.widget;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

/**
 * Created by ${赵江涛} on 2017-12-28.
 * 作用:
 */

public class WidgetDrawerLayout extends DrawerLayout {


    public WidgetDrawerLayout(Context context) {
        super(context);
    }

    public WidgetDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidgetDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}