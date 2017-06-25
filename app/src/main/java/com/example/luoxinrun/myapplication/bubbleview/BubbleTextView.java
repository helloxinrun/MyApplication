package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class BubbleTextView extends TextView {
    private BubbleViewImpl mBubbleImpl = new BubbleViewImpl();

    public BubbleTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mBubbleImpl.init(this, context, attrs);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mBubbleImpl.buildBubbleDrawable(getWidth(), getHeight());
    }
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        super.onMeasure(width,height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case View.MeasureSpec.AT_MOST:
                defaultWidth = (int) getMeasuredWidth();
                break;
            case View.MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case View.MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
                break;
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case View.MeasureSpec.AT_MOST:
                defaultHeight = getMeasuredHeight();
                break;
            case View.MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case View.MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;

    }

}
