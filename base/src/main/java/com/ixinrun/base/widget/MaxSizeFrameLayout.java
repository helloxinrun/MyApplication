package com.ixinrun.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ixinrun.base.R;


/**
 * 描述：可设置最大宽、高的FrameLayout
 * 两个维度（最大值、最大比率）,如果从资源文件中加载，则优先采用最大宽，最大高，然后才考虑比率
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/8/27
 */
public class MaxSizeFrameLayout extends FrameLayout {
    private int mMaxWidth, mMaxHeight;

    public MaxSizeFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public MaxSizeFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxSizeFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.MaxSizeFrameLayout));
    }

    private void parseAttributes(TypedArray a) {
        mMaxWidth = (int) a.getDimension(R.styleable.MaxSizeFrameLayout_maxWidthDimen, 0);
        mMaxHeight = (int) a.getDimension(R.styleable.MaxSizeFrameLayout_maxHeightDimen, 0);
        if (mMaxWidth <= 0) {
            setMaxWidthRatio(a.getFloat(R.styleable.MaxSizeFrameLayout_maxWidthRatio, 0));
        }
        if (mMaxHeight <= 0) {
            setMaxHeightRatio(a.getFloat(R.styleable.MaxSizeFrameLayout_maxHeightRatio, 0));
        }
        a.recycle();
    }

    /**
     * 按比例设置最大宽
     *
     * @param ratio
     * @return
     */
    public MaxSizeFrameLayout setMaxWidthRatio(float ratio) {
        if (ratio > 0) {
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            this.mMaxWidth = (int) (screenWidth * ratio);
        }
        return this;
    }

    /**
     * 设置最大宽
     *
     * @param width
     * @return
     */
    public MaxSizeFrameLayout setMaxWidth(int width) {
        this.mMaxWidth = width;
        return this;
    }

    /**
     * 按比例设置最大高
     *
     * @param ratio
     * @return
     */
    public MaxSizeFrameLayout setMaxHeightRatio(float ratio) {
        if (ratio > 0) {
            int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            this.mMaxHeight = (int) (screenHeight * ratio);
        }
        return this;
    }

    /**
     * 设置最大高
     *
     * @param height
     * @return
     */
    public MaxSizeFrameLayout setMaxHeight(int height) {
        this.mMaxHeight = height;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (mMaxWidth > 0) {
            widthSize = Math.min(widthSize, mMaxWidth);
        }
        if (mMaxHeight > 0) {
            heightSize = Math.min(heightSize, mMaxHeight);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode),
                MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }
}
