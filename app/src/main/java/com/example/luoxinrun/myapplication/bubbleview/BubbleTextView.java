package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.luoxinrun.myapplication.R;

/**
 * Created by lgp on 2015/3/24.
 */
public class BubbleTextView extends TextView {
    private BubbleDrawable mBubbleDrawable;
    private float mArrowWidth;
    private float mArrowHeight;
    private float mBubbleRadius;
    private int mBubbleColor;
    private float mArrowPosition;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private BubbleDrawable.ArrowRelative mArrowRelative;

    public BubbleTextView(Context context) {
        super(context);
        initView(null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WIDTH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mBubbleRadius = array.getDimension(R.styleable.BubbleView_bubbleRadius,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIUS);
            mBubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            int relative = array.getInt(R.styleable.BubbleView_arrowRelative, 0);
            mArrowRelative = BubbleDrawable.ArrowRelative.mapIntToValue(relative);
            array.recycle();
        }
        setUpPadding();
    }

    private void setUpPadding() {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        switch (mArrowLocation) {
            case LEFT:
                left += mArrowWidth;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
                break;
            case BOTTOM:
                bottom += mArrowHeight;
                break;
        }
        setPadding(left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setUp(w, h);
        }
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        setUp(getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBubbleDrawable != null)
            mBubbleDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    private void setUp(int width, int height) {
        setUp(0, 0, width, height);
    }

    private void setUp(int left, int top, int right, int bottom) {
        RectF rectF = new RectF(left, top, right, bottom);
        mBubbleDrawable = new BubbleDrawable.Builder()
                .rect(rectF)
                .bubbleType(BubbleDrawable.BubbleType.COLOR)
                .arrowWidth(mArrowWidth)
                .arrowHeight(mArrowHeight)
                .arrowLocation(mArrowLocation)
                .arrowRelative(mArrowRelative)
                .arrowPosition(mArrowPosition)
                .bubbleRadius(mBubbleRadius)
                .bubbleColor(mBubbleColor)
                .build();
    }

}
