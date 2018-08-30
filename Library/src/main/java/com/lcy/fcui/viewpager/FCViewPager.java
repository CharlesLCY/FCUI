package com.lcy.fcui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author FanCoder.LCY
 * @date 2018/8/23 11:24
 * @email 15708478830@163.com
 * @desc 可禁止滑动
 **/
public class FCViewPager extends ViewPager {
    // 是否可滑动
    public boolean isSlide = true;

    public boolean isSlide() {
        return isSlide;
    }

    public void setSlideEnable(boolean slide) {
        isSlide = slide;
    }

    public FCViewPager(Context context) {
        super(context);
    }

    public FCViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}
