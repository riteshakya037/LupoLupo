package com.blues.lupolupo.views.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.blues.lupolupo.R;

/**
 * @author Ritesh Shakya
 */

public class RatioRelativeLayout extends RelativeLayout {
    private float verticalRatio = 1;
    private float horizontalRatio = 1;
    private FixedAttribute fixedAttribute = FixedAttribute.WIDTH;

    public RatioRelativeLayout(Context context) {
        super(context);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.RatioRelativeLayout);
        fixedAttribute = FixedAttribute.fromId(typedArray.getInt(R.styleable.RatioRelativeLayout_fixed_attribute, 0));
        horizontalRatio = typedArray.getFloat(R.styleable.RatioRelativeLayout_horizontal_ratio, 1);
        verticalRatio = typedArray.getFloat(R.styleable.RatioRelativeLayout_vertical_ratio, 1);
        typedArray.recycle();
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        System.out.println("widthMeasureSpec = " + widthMeasureSpec);
        if (fixedAttribute == FixedAttribute.WIDTH) {
            int calculatedHeight = (int) (originalWidth * (verticalRatio / horizontalRatio));
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
        } else {
            int calculatedWidth = (int) (originalHeight * (horizontalRatio / verticalRatio));
            super.onMeasure(MeasureSpec.makeMeasureSpec(calculatedWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }

    public enum FixedAttribute {
        HEIGHT(0), WIDTH(1);
        final int id;

        FixedAttribute(int id) {
            this.id = id;
        }

        static FixedAttribute fromId(int id) {
            for (FixedAttribute f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }
}
