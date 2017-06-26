package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
    private BubbleBgType mBubbleBgType;

    private Context mContext;
    private View mView;

    public void init(View view, Context context, AttributeSet attrs){
        this.mView = view;
        this.mContext = context;
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
            mBubbleLeftTopRadiu = array.getDimension(R.styleable.BubbleView_bubbleLeftTopRadiu, mBubbleRadius);
            mBubbleRightTopRadiu = array.getDimension(R.styleable.BubbleView_bubbleRightTopRadiu, mBubbleRadius);
            mBubbleLeftBottomRadiu = array.getDimension(R.styleable.BubbleView_bubbleLeftBottomRadiu, mBubbleRadius);
            mBubbleRightBottomRadiu = array.getDimension(R.styleable.BubbleView_bubbleRightBottomRadiu, mBubbleRadius);
            mBubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            int type = array.getInt(R.styleable.BubbleView_bubbleBgType, 0);
            mBubbleBgType = BubbleBgType.mapIntToValue(type);
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

    @Override
    public void setBubbleColor(int color) {
        this.mBubbleColor = color;
    }

    @Override
    public int getBubbleColor() {
        return mBubbleColor;
    }

    @Override
    public void setBubbleBgType(BubbleBgType bubbleBgType) {
        this.mBubbleBgType = bubbleBgType;
    }

    @Override
    public BubbleBgType getBubbleBgType() {
        return mBubbleBgType;
    }

    public BubbleDrawable buildBubbleDrawable(int width, int height) {
        RectF rectF = new RectF(0, 0, width, height);
        Log.e("TAG","==============" + mView.getWidth()+ "==========="+mView.getHeight());
        Bitmap bitmap = getBitmapFromDrawable(mContext, mView.getBackground(), mView.getWidth(), mView.getHeight(), 20);
        BubbleDrawable bubbleDrawable = new BubbleDrawable.Builder()
                    .rect(rectF)
                    .bubbleType(mBubbleBgType)
                    .arrowWidth(mArrowWidth)
                    .arrowHeight(mArrowHeight)
                    .arrowLocation(mArrowLocation)
                    .arrowRelative(mArrowRelative)
                    .arrowPosition(mArrowPosition)
                    .bubbleRadius(mBubbleRadius)
                    .bubbleRadius(mBubbleLeftTopRadiu, mBubbleRightTopRadiu, mBubbleLeftBottomRadiu, mBubbleRightBottomRadiu)
                    .bubbleColor(mBubbleColor)
                    .bubbleBitmap(bitmap)
                    .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView.setBackground(bubbleDrawable);
        } else {
            // noinspection deprecation
            mView.setBackgroundDrawable(bubbleDrawable);
        }
        return bubbleDrawable;
    }

    private Bitmap getBitmapFromDrawable(Context mContext, Drawable drawable, int width, int height, int defaultSize) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (width > 0 && height > 0){
                bitmap = Bitmap.createBitmap(width,
                        height, Bitmap.Config.ARGB_8888);
            }else{
                bitmap = Bitmap.createBitmap(dp2px(mContext, defaultSize),
                        dp2px(mContext, defaultSize), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
