package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoxinrun.myapplication.R;

/**
 * Created by luoxinrun on 2017/6/23.
 */

public class BubbleViewImpl implements BubbleViewAttrs{
    private float mArrowWidth;
    private float mArrowHeight;
    private ArrowLocation mArrowLocation;
    private ArrowRelative mArrowRelative;
    private float mArrowPosition;
    private float mBubbleRadius;
    private float mBubbleLeftTopRadiu;
    private float mBubbleRightTopRadiu;
    private float mBubbleLeftBottomRadiu;
    private float mBubbleRightBottomRadiu;
    private int mBubbleColor;

    public void init(View view, Context context, AttributeSet attrs){
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WIDTH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = ArrowLocation.mapIntToValue(location);
            int relative = array.getInt(R.styleable.BubbleView_arrowRelative, 0);
            mArrowRelative = ArrowRelative.mapIntToValue(relative);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            mBubbleRadius = array.getDimension(R.styleable.BubbleView_bubbleRadius,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIU);
            mBubbleLeftTopRadiu = array.getDimension(R.styleable.BubbleView_bubbleLeftTopRadiu,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIU);
            mBubbleRightTopRadiu = array.getDimension(R.styleable.BubbleView_bubbleRightTopRadiu,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIU);
            mBubbleLeftBottomRadiu = array.getDimension(R.styleable.BubbleView_bubbleLeftBottomRadiu,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIU);
            mBubbleRightBottomRadiu = array.getDimension(R.styleable.BubbleView_bubbleRightBottomRadiu,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIU);
            mBubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            array.recycle();
        }
        setUpPadding(view);
    }

    private void setUpPadding(View view) {
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
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
        view.setPadding(left, top, right, bottom);
    }

    @Override
    public void setArrowWidthHeight(float arrowWidth, float arrowHeight) {
        this.mArrowWidth = arrowWidth;
        this.mArrowHeight = arrowHeight;
    }

    @Override
    public float getArrowWidth() {
        return mArrowWidth;
    }

    @Override
    public float getArrowHeight() {
        return mArrowHeight;
    }

    @Override
    public void setArrowPos(ArrowLocation arrowLocation, ArrowRelative arrowRelative, float arrowPosition) {
        this.mArrowLocation = arrowLocation;
        this.mArrowRelative = arrowRelative;
        this.mArrowPosition = arrowPosition;
    }

    @Override
    public ArrowLocation getArrowLocation() {
        return mArrowLocation;
    }

    @Override
    public ArrowRelative arrowRelative() {
        return mArrowRelative;
    }

    @Override
    public float getArrowPosition() {
        return mArrowPosition;
    }

    @Override
    public void setBubbleRadius(float bubbleRadius) {
        this.mBubbleLeftTopRadiu = bubbleRadius;
        this.mBubbleRightTopRadiu = bubbleRadius;
        this.mBubbleLeftBottomRadiu = bubbleRadius;
        this.mBubbleLeftBottomRadiu = bubbleRadius;
    }

    @Override
    public void setBubbleRadius(float leftTopRadiu, float rightTopRadiu, float leftBottomRadiu, float rightBottomRadiu) {
        this.mBubbleLeftTopRadiu = leftTopRadiu;
        this.mBubbleRightTopRadiu = rightTopRadiu;
        this.mBubbleLeftBottomRadiu = leftBottomRadiu;
        this.mBubbleLeftBottomRadiu = rightBottomRadiu;
    }

    @Override
    public float getBubbleLeftTopRadiu() {
        return mBubbleLeftTopRadiu;
    }

    @Override
    public float getBubbleRightTopRadiu() {
        return mBubbleRightTopRadiu;
    }

    @Override
    public float getBubbleLeftBottomRadiu() {
        return mBubbleLeftBottomRadiu;
    }

    @Override
    public float getBubbleRightBottomRadiu() {
        return mBubbleLeftBottomRadiu;
    }

}
