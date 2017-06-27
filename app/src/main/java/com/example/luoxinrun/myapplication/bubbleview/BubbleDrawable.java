package com.example.luoxinrun.myapplication.bubbleview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BubbleDrawable extends Drawable {
    private RectF mRect;
    private Path mPath = new Path();
    private BitmapShader mBitmapShader;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mArrowWidth;
    private float mArrowHeight;
    private BubbleViewAttrs.ArrowLocation mArrowLocation;
    private BubbleViewAttrs.ArrowRelative mArrowRelative;
    private float mArrowPosition;

    private float mBubbleLeftTopRadiu;
    private float mBubbleRightTopRadiu;
    private float mBubbleLeftBottomRadiu;
    private float mBubbleRightBottomRadiu;

    private int mBubbleColor;
    private Bitmap mBubbleBitmap;
    private BubbleViewAttrs.BubbleBgType mBubbleBgType;

    private BubbleDrawable(Builder builder) {
        this.mRect = builder.mRect;
        this.mArrowLocation = builder.mArrowLocation;
        this.mArrowRelative = builder.mArrowRelative;
        this.mArrowPosition = builder.mArrowPosition;
        this.mArrowWidth = limitWidthHeight(builder.mArrowWidth, mRect.width());
        this.mArrowHeight = limitWidthHeight(builder.mArrowHeight, mRect.height());
        this.mBubbleLeftTopRadiu = limitRadius(builder.mBubbleLeftTopRadiu, mRect.width(), mRect.height());
        this.mBubbleRightTopRadiu = limitRadius(builder.mBubbleRightTopRadiu, mRect.width(), mRect.height());
        this.mBubbleLeftBottomRadiu = limitRadius(builder.mBubbleLeftBottomRadiu, mRect.width(), mRect.height());
        this.mBubbleRightBottomRadiu = limitRadius(builder.mBubbleRightBottomRadiu, mRect.width(), mRect.height());
        this.mBubbleColor = builder.mBubbleColor;
        this.mBubbleBitmap = builder.mBubbleBitmap;
        this.mBubbleBgType = builder.mBubbleBgType;
    }

    private float limitWidthHeight(float num, float maxNum){
        if (num < 0){
            num = 0;
        }else if (num > maxNum){
            num = maxNum;
        }
        return num;
    }

    private float limitRadius(float radiu, float width, float height){
        Log.e("TAG","===radiu"+radiu+"===width"+width+"===height"+height+"===mArrowWidth"+mArrowWidth+"===mArrowHeight"+mArrowHeight);
        if (radiu < 0){
            radiu = 0;
        }else {
            float offset;
            switch (mArrowLocation) {
                case LEFT:
                case RIGHT:
                    offset = Math.min(width - mArrowWidth, height) / 2;
                    if (radiu > offset)
                        radiu = offset;
                    break;
                case TOP:
                case BOTTOM:
                    offset = Math.min(width , height - mArrowHeight) / 2;
                    if (radiu > offset)
                        radiu = offset;
                    break;
            }
        }
        return radiu;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void draw(Canvas canvas) {
        setUp(canvas);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;  //窗口支持透明度
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    private void setUp(Canvas canvas) {
        switch (mBubbleBgType) {
            case COLOR:
                mPaint.setColor(mBubbleColor);
                break;
            case BITMAP:
                if (mBubbleBitmap == null)
                    return;
                if (mBitmapShader == null) {
                    mBitmapShader = new BitmapShader(mBubbleBitmap, Shader.TileMode.CLAMP,
                            Shader.TileMode.CLAMP);  // CLAMP表示，当所画图形的尺寸大于Bitmap的尺寸的时候，会用Bitmap四边的颜色填充剩余空间
                }
                setUpShaderMatrix();
                mPaint.setShader(mBitmapShader);
                break;
        }
        setUpPath(mArrowLocation, mPath);
        canvas.drawPath(mPath, mPaint);
    }

    private void setUpShaderMatrix() {
        Matrix mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);
        int mBitmapWidth = mBubbleBitmap.getWidth();
        int mBitmapHeight = mBubbleBitmap.getHeight();
        float scaleX = getIntrinsicWidth() / (float) mBitmapWidth;
        float scaleY = getIntrinsicHeight() / (float) mBitmapHeight;
        mShaderMatrix.postScale(scaleX, scaleY);
        mShaderMatrix.postTranslate(mRect.left, mRect.top);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    private void setUpPath(BubbleViewAttrs.ArrowLocation mArrowLocation, Path path) {
        switch (mArrowLocation) {
            case LEFT:
                setLeftPath(mRect, path);
                break;
            case RIGHT:
                setRightPath(mRect, path);
                break;
            case TOP:
                setTopPath(mRect, path);
                break;
            case BOTTOM:
                setBottomPath(mRect, path);
                break;
        }
    }

    private void setLeftPath(RectF rect, Path path) {
        float position;
        if ((rect.height() - (mBubbleLeftTopRadiu + mBubbleLeftBottomRadiu)) < mArrowHeight){
            mArrowHeight = rect.height() - (mBubbleLeftTopRadiu + mBubbleLeftBottomRadiu);
            position = mBubbleLeftTopRadiu;
        }else {
            switch (mArrowRelative) {
                case BEGIN:
                    position = mArrowPosition;
                    break;
                case CENTER:
                    position = rect.height() / 2 - mArrowHeight / 2 + mArrowPosition;
                    break;
                case END:
                    position = rect.bottom - mArrowPosition - mArrowHeight;
                    break;
                default:
                    position = mBubbleLeftTopRadiu;
                    break;
            }
            if (position < mBubbleLeftTopRadiu)
                position = mBubbleLeftTopRadiu;
            else if (position > (rect.height() - mBubbleLeftBottomRadiu - mArrowHeight))
                position = rect.height() - mBubbleLeftBottomRadiu - mArrowHeight;
        }

        path.moveTo(rect.left + mArrowWidth + mBubbleLeftTopRadiu, rect.top);
        path.lineTo(rect.right - mBubbleRightTopRadiu, rect.top);
        path.arcTo(new RectF(rect.right - mBubbleRightTopRadiu * 2, rect.top, rect.right, rect.top + mBubbleRightTopRadiu * 2), 270, 90);
        path.lineTo(rect.right, rect.bottom - mBubbleRightBottomRadiu);
        path.arcTo(new RectF(rect.right - mBubbleRightBottomRadiu * 2, rect.bottom - mBubbleRightBottomRadiu * 2, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth + mBubbleLeftBottomRadiu, rect.bottom);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mBubbleLeftBottomRadiu * 2, rect.left + mArrowWidth + mBubbleLeftBottomRadiu * 2, rect.bottom), 90, 90);
        path.lineTo(rect.left + mArrowWidth,  position + mArrowHeight);
        path.lineTo(rect.left, position + mArrowHeight / 2);
        path.lineTo(rect.left + mArrowWidth, position);
        path.lineTo(rect.left + mArrowWidth, rect.top + mBubbleLeftTopRadiu);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, rect.left + mArrowWidth + mBubbleLeftTopRadiu * 2 , rect.top + mBubbleLeftTopRadiu * 2), 180, 90);
        path.close();
    }

    private void setTopPath(RectF rect, Path path) {
        float position;
        if ((rect.width() - (mBubbleLeftTopRadiu + mBubbleRightTopRadiu)) < mArrowWidth){
            mArrowWidth = rect.width() - (mBubbleLeftTopRadiu + mBubbleRightTopRadiu);
            position = mBubbleLeftTopRadiu;
        }else {
            switch (mArrowRelative) {
                case BEGIN:
                    position = mArrowPosition;
                    break;
                case CENTER:
                    position = rect.width() / 2 - mArrowWidth / 2 + mArrowPosition;
                    break;
                case END:
                    position = rect.right - mArrowPosition - mArrowWidth;
                    break;
                default:
                    position = mBubbleLeftTopRadiu;
                    break;
            }
            if (position < mBubbleLeftTopRadiu)
                position = mBubbleLeftTopRadiu;
            else if (position > (rect.width() - mBubbleRightTopRadiu - mArrowWidth))
                position = rect.width() - mBubbleRightTopRadiu - mArrowWidth;
        }

        path.moveTo(rect.left + mBubbleLeftTopRadiu, rect.top + mArrowHeight);
        path.lineTo(rect.left + position, rect.top + mArrowHeight);
        path.lineTo(rect.left  + position + mArrowWidth / 2, rect.top);
        path.lineTo(rect.left + position + mArrowWidth, rect.top + mArrowHeight);
        path.lineTo(rect.right - mBubbleRightTopRadiu, rect.top + mArrowHeight);
        path.arcTo(new RectF(rect.right - mBubbleRightTopRadiu * 2, rect.top + mArrowHeight, rect.right, rect.top + mArrowHeight + mBubbleRightTopRadiu * 2 ), 270, 90);
        path.lineTo(rect.right, rect.bottom - mBubbleRightBottomRadiu);
        path.arcTo(new RectF(rect.right - mBubbleRightBottomRadiu * 2, rect.bottom - mBubbleRightBottomRadiu * 2, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mBubbleLeftBottomRadiu, rect.bottom);
        path.arcTo(new RectF(rect.left, rect.bottom - mBubbleLeftBottomRadiu * 2, rect.left + mBubbleLeftBottomRadiu * 2, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mArrowHeight + mBubbleLeftTopRadiu);
        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight, rect.left + mBubbleLeftTopRadiu * 2, rect.top + mArrowHeight + mBubbleLeftTopRadiu * 2), 180, 90);
        path.close();
    }

    private void setRightPath(RectF rect, Path path) {
        float position;
        if ((rect.height() - (mBubbleRightTopRadiu + mBubbleRightBottomRadiu)) < mArrowHeight){
            mArrowHeight = rect.height() - (mBubbleRightTopRadiu + mBubbleRightBottomRadiu);
            position = mBubbleRightTopRadiu;
        }else {
            switch (mArrowRelative) {
                case BEGIN:
                    position = mArrowPosition;
                    break;
                case CENTER:
                    position = rect.height() / 2 - mArrowHeight / 2 + mArrowPosition;
                    break;
                case END:
                    position = rect.bottom - mArrowPosition - mArrowHeight;
                    break;
                default:
                    position = mBubbleRightTopRadiu;
                    break;
            }
            if (position < mBubbleRightTopRadiu)
                position = mBubbleRightTopRadiu;
            else if (position > (rect.height() - mBubbleRightBottomRadiu - mArrowHeight))
                position = rect.height() - mBubbleRightBottomRadiu - mArrowHeight;
        }

        path.moveTo(rect.left + mBubbleLeftTopRadiu, rect.top);
        path.lineTo(rect.right - mArrowWidth - mBubbleRightTopRadiu, rect.top);
        path.arcTo(new RectF(rect.right - mArrowWidth - mBubbleRightTopRadiu * 2, rect.top, rect.right - mArrowWidth, rect.top + mBubbleRightTopRadiu * 2), 270, 90);
        path.lineTo(rect.right - mArrowWidth, position);
        path.lineTo(rect.right, position + mArrowHeight / 2);
        path.lineTo(rect.right - mArrowWidth, position + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.bottom - mBubbleRightBottomRadiu);
        path.arcTo(new RectF(rect.right - mArrowWidth - mBubbleRightBottomRadiu * 2, rect.bottom - mBubbleRightBottomRadiu * 2, rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.left + mBubbleLeftBottomRadiu, rect.bottom);
        path.arcTo(new RectF(rect.left, rect.bottom - mBubbleLeftBottomRadiu * 2, rect.left + mBubbleLeftBottomRadiu * 2, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mBubbleLeftTopRadiu);
        path.arcTo(new RectF(rect.left, rect.top, rect.left + mBubbleLeftTopRadiu * 2, rect.top + mBubbleLeftTopRadiu * 2), 180, 90);
        path.close();
    }

    private void setBottomPath(RectF rect, Path path) {
        float position;
        if ((rect.width() - (mBubbleLeftBottomRadiu + mBubbleRightBottomRadiu)) < mArrowWidth){
            mArrowWidth = rect.width() - (mBubbleLeftBottomRadiu + mBubbleRightBottomRadiu);
            position = mBubbleLeftBottomRadiu;
        }else {
            switch (mArrowRelative) {
                case BEGIN:
                    position = mArrowPosition;
                    break;
                case CENTER:
                    position = rect.width() / 2 - mArrowWidth / 2 + mArrowPosition;
                    break;
                case END:
                    position = rect.right - mArrowPosition - mArrowWidth;
                    break;
                default:
                    position = mBubbleLeftBottomRadiu;
                    break;
            }
            if (position < mBubbleLeftBottomRadiu)
                position = mBubbleLeftBottomRadiu;
            else if (position > (rect.width() - mBubbleRightBottomRadiu - mArrowWidth))
                position = rect.width() - mBubbleRightBottomRadiu - mArrowWidth;
        }

        path.moveTo(rect.left + mBubbleLeftTopRadiu, rect.top);
        path.lineTo(rect.right - mBubbleRightTopRadiu, rect.top);
        path.arcTo(new RectF(rect.right - mBubbleRightTopRadiu * 2, rect.top, rect.right, rect.top + mBubbleRightTopRadiu * 2), 270, 90);
        path.lineTo(rect.right, rect.bottom - mArrowHeight - mBubbleRightBottomRadiu);
        path.arcTo(new RectF(rect.right - mBubbleRightBottomRadiu * 2, rect.bottom - mArrowHeight - mBubbleRightBottomRadiu * 2, rect.right, rect.bottom - mArrowHeight), 0, 90);
        path.lineTo(rect.left + position + mArrowWidth, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + position + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + position, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mBubbleLeftBottomRadiu, rect.bottom - mArrowHeight);
        path.arcTo(new RectF(rect.left, rect.bottom - mArrowHeight - mBubbleLeftBottomRadiu * 2, rect.left + mBubbleLeftBottomRadiu * 2, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mBubbleLeftTopRadiu);
        path.arcTo(new RectF(rect.left, rect.top, rect.left + mBubbleLeftTopRadiu * 2 , rect.top + mBubbleLeftTopRadiu * 2), 180, 90);
        path.close();
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mRect.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mRect.height();
    }

    public static class Builder {
        public static float DEFAULT_ARROW_WIDTH = 10;
        public static float DEFAULT_ARROW_HEIGHT = 10;
        public static float DEFAULT_ARROW_POSITION = 0;
        public static float DEFAULT_BUBBLE_RADIU = 0;
        public static int DEFAULT_BUBBLE_COLOR = Color.WHITE;

        private RectF mRect;
        private float mArrowWidth = DEFAULT_ARROW_WIDTH;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private BubbleViewAttrs.ArrowLocation mArrowLocation = BubbleViewAttrs.ArrowLocation.LEFT;
        private BubbleViewAttrs.ArrowRelative mArrowRelative = BubbleViewAttrs.ArrowRelative.BEGIN;
        private float mArrowPosition = DEFAULT_ARROW_POSITION;

        private float mBubbleLeftTopRadiu = DEFAULT_BUBBLE_RADIU;
        private float mBubbleRightTopRadiu = DEFAULT_BUBBLE_RADIU;
        private float mBubbleLeftBottomRadiu = DEFAULT_BUBBLE_RADIU;
        private float mBubbleRightBottomRadiu = DEFAULT_BUBBLE_RADIU;

        private int mBubbleColor = DEFAULT_BUBBLE_COLOR;
        private Bitmap mBubbleBitmap;
        private BubbleViewAttrs.BubbleBgType mBubbleBgType = BubbleViewAttrs.BubbleBgType.COLOR;

        public Builder rect(RectF rect) {
            this.mRect = rect;
            return this;
        }

        public Builder arrowWidth(float arrowWidth) {
            this.mArrowWidth = arrowWidth;
            return this;
        }

        public Builder arrowHeight(float arrowHeight) {
            this.mArrowHeight = arrowHeight;
            return this;
        }

        public Builder arrowLocation(BubbleViewAttrs.ArrowLocation arrowLocation) {
            this.mArrowLocation = arrowLocation;
            return this;
        }

        public Builder arrowRelative(BubbleViewAttrs.ArrowRelative arrowRelative) {
            this.mArrowRelative = arrowRelative;
            return this;
        }

        public Builder arrowPosition(float arrowPosition) {
            this.mArrowPosition = arrowPosition;
            return this;
        }

        public Builder bubbleRadius(float bubbleRadius) {
            this.mBubbleLeftTopRadiu = bubbleRadius;
            this.mBubbleRightTopRadiu = bubbleRadius;
            this.mBubbleLeftBottomRadiu = bubbleRadius;
            this.mBubbleRightBottomRadiu = bubbleRadius;
            return this;
        }

        public Builder bubbleRadius(float bubbleLeftTopRadiu, float bubbleRightTopRadiu, float bubbleLeftBottomRadiu, float bubbleRightBottomRadiu){
            this.mBubbleLeftTopRadiu = bubbleLeftTopRadiu;
            this.mBubbleRightTopRadiu = bubbleRightTopRadiu;
            this.mBubbleLeftBottomRadiu = bubbleLeftBottomRadiu;
            this.mBubbleRightBottomRadiu = bubbleRightBottomRadiu;
            return this;
        }

        public Builder bubbleColor(int bubbleColor) {
            this.mBubbleColor = bubbleColor;
            return this;
        }

        public Builder bubbleBitmap(Bitmap bubbleBitmap) {
            this.mBubbleBitmap = bubbleBitmap;
            return this;
        }

        public Builder bubbleType(BubbleViewAttrs.BubbleBgType bubbleType) {
            this.mBubbleBgType = bubbleType;
            return this;
        }

        public BubbleDrawable build() {
            if (mRect == null) {
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }
            return new BubbleDrawable(this);
        }
    }


}
