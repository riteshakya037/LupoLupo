package com.lupolupo.android.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.lupolupo.android.R;

import java.lang.reflect.Field;

/**
 * @author Ritesh Shakya
 */

public class ViewPagerCustomDuration extends ViewPager {
    private FixedSpeedScroller mScroller = null;

    public ViewPagerCustomDuration(Context context) {
        super(context);
        init();
    }

    public ViewPagerCustomDuration(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.ViewPagerCustomDuration);
        setScrollDuration(typedArray.getInt(R.styleable.ViewPagerCustomDuration_scrollSpeed, 0));
    }

    private void init() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            mScroller = new FixedSpeedScroller(getContext(),
                    new DecelerateInterpolator());
            scroller.set(this, mScroller);
        } catch (Exception ignored) {
        }

    }

    public void setScrollDuration(int duration) {
        mScroller.setScrollDuration(duration);
    }

    private class FixedSpeedScroller extends Scroller {

        private int mDuration = 500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setScrollDuration(int duration) {
            mDuration = duration;
        }
    }
}