package com.jingna.checklistapp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author zwl
 * @date on 2019-12-23
 */
public class MyViewPager extends ViewPager {

    private boolean mEnableScroll = false;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnableScroll(boolean enableScroll) {
        this.mEnableScroll = enableScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mEnableScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mEnableScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}