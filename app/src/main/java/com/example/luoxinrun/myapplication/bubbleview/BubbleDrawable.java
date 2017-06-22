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

/**
 * Created by lgp on 2015/3/24.
 */
public class BubbleDrawable extends Drawable {
    private RectF mRect;
    private Path mPath = new Path();
    private BitmapShader mBitmapShader;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mArrowWidth;
    private float mBubbleRadius;
    private float mArrowHeight;
    private float mArrowPosition;
    private int mBubbleColor;
    private Bitmap mBubbleBitmap;
    private ArrowLocation mArrowLocation;
    private ArrowRelative mArrowRelative;
    private BubbleType mBubbleType;

    private BubbleDrawable(Builder builder) {
        this.mRect = builder.mRect;
        this.mArrowLocation = builder.mArrowLocation;
        this.mArrowRelative = builder.mArrowRelative;
        this.mArrowPosition = builder.mArrowPosition;
        this.mArrowWidth = builder.mArrowWidth;
        this.mArrowHeight = builder.mArrowHeight;
        this.mBubbleRadius = builder.mBubbleRadius;
        this.mBubbleColor = builder.mBubbleColor;
        this.mBubbleBitmap = builder.mBubbleBitmap;
        this.mBubbleType = builder.mBubbleType;
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
        return PixelFormat.TRANSLUCENT;
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
        switch (mBubbleType) {
            case COLOR:
                mPaint.setColor(mBubbleColor);
                break;
            case BITMAP:
                if (mBubbleBitmap == null)
                    return;
                if (mBitmapShader == null) {
                    mBitmapShader = new BitmapShader(mBubbleBitmap, Shader.TileMode.CLAMP,
                            Shader.TileMode.CLAMP);
                }
                mPaint.setShader(mBitmapShader);
                setUpShaderMatrix();
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

    private void setUpPath(ArrowLocation mArrowLocation, Path path) {
        switch (mArrowLocation) {
            case LEFT:
                setUpLeftPath(mRect, path);
                break;
            case RIGHT:
                setUpRightPath(mRect, path);
                break;
            case TOP:
                setUpTopPath(mRect, path);
                break;
            case BOTTOM:
                setUpBottomPath(mRect, path);
                break;
        }
    }

    private void setUpLeftPath(RectF rect, Path path) {
        float position;
        if (mArrowRelative == ArrowRelative.BEGIN) {
            position = mArrowPosition;
        } else if (mArrowRelative == ArrowRelative.CENTER) {
            position = (rect.bottom - rect.top) / 2 - mArrowHeight / 2;
        } else if (mArrowRelative == ArrowRelative.END) {
            position = rect.bottom - mArrowPosition - mArrowHeight;
        }

        path.moveTo(rect.left + mArrowWidth + mBubbleRadius, rect.top);
        Log.e("++++++TAG", "+++++++++++++++width" + rect.width() + "++++++++++:" + rect.right);
        path.lineTo(rect.right - mBubbleRadius, rect.top);
        path.arcTo(new RectF(rect.right - mBubbleRadius, rect.top, rect.right, mBubbleRadius + rect.top), 270, 90);
        path.lineTo(rect.right, rect.bottom - mBubbleRadius);
        path.arcTo(new RectF(rect.right - mBubbleRadius, rect.bottom - mBubbleRadius, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth + mBubbleRadius, rect.bottom);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mBubbleRadius, rect.left + mArrowWidth + mBubbleRadius, rect.bottom), 90, 90);
        path.lineTo(rect.left + mArrowWidth, mArrowHeight + mArrowPosition);
        path.lineTo(rect.left, mArrowPosition + mArrowHeight / 2);
        path.lineTo(rect.left + mArrowWidth, mArrowPosition);
        path.lineTo(rect.left + mArrowWidth, rect.top + mBubbleRadius);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, mBubbleRadius + rect.left + mArrowWidth, mBubbleRadius + rect.top), 180, 90);
        path.close();
    }

    private void setUpTopPath(RectF rect, Path path) {
        float position;
        if ((rect.width() - 2 * mBubbleRadius) < mArrowWidth){
            mArrowWidth = rect.width() - 2 * mBubbleRadius;
            position = mBubbleRadius;
            Log.e("++++++TAG", "+++++++++1++++++position" + position + "++++++++++mBubbleRadius:" + mBubbleRadius);
        }else {
            switch (mArrowRelative) {
                case BEGIN:
                    position = mArrowPosition;
                    break;
                case CENTER:
                    position = rect.width() / 2 - mArrowWidth / 2;
                    break;
                case END:
                    position = rect.right - mArrowPosition - mArrowWidth;
                    break;
                default:
                    position = mBubbleRadius;
                    break;
            }
            if (position < mBubbleRadius)
                position = mBubbleRadius;
            else if (position > (rect.width() - mBubbleRadius - mArrowWidth))
                position = rect.width() - mBubbleRadius - mArrowWidth;

        }
        path.moveTo(rect.left + mBubbleRadius, rect.top + mArrowHeight);
        path.lineTo(rect.left + position, rect.top + mArrowHeight);
        path.lineTo(rect.left  + position + mArrowWidth / 2, rect.top);
        path.lineTo(rect.left + position + mArrowWidth, rect.top + mArrowHeight);
        path.lineTo(rect.right - mBubbleRadius, rect.top + mArrowHeight);
        path.arcTo(new RectF(rect.right - mBubbleRadius * 2, rect.top + mArrowHeight, rect.right, rect.top + mArrowHeight + mBubbleRadius * 2 ), 270, 90);
        path.lineTo(rect.right, rect.bottom - mBubbleRadius);
        path.arcTo(new RectF(rect.right - mBubbleRadius * 2, rect.bottom - mBubbleRadius * 2, rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mBubbleRadius, rect.bottom);
        path.arcTo(new RectF(rect.left, rect.bottom - mBubbleRadius * 2, rect.left + mBubbleRadius * 2, rect.bottom), 90, 90);
        path.lineTo(rect.left, rect.top + mArrowHeight + mBubbleRadius);
        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight, rect.left + mBubbleRadius * 2, rect.top + mArrowHeight + mBubbleRadius * 2), 180, 90);
        path.close();
    }

    private void setUpRightPath(RectF rect, Path path) {
        float position;
        if (mArrowRelative == ArrowRelative.BEGIN) {
            position = mArrowPosition;
        } else if (mArrowRelative == ArrowRelative.CENTER) {
            position = (rect.bottom - rect.top) / 2 - mArrowHeight / 2;
        } else if (mArrowRelative == ArrowRelative.END) {
            position = rect.bottom - mArrowPosition - mArrowHeight;
        }

        path.moveTo(rect.left + mBubbleRadius, rect.top);
        path.lineTo(rect.width() - mBubbleRadius - mArrowWidth, rect.top);
        path.arcTo(new RectF(rect.right - mBubbleRadius - mArrowWidth, rect.top,
                rect.right - mArrowWidth, mBubbleRadius + rect.top), 270, 90);
        path.lineTo(rect.right - mArrowWidth, mArrowPosition);
        path.lineTo(rect.right, mArrowPosition + mArrowHeight / 2);
        path.lineTo(rect.right - mArrowWidth, mArrowPosition + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.bottom - mBubbleRadius);

        path.arcTo(new RectF(rect.right - mBubbleRadius - mArrowWidth, rect.bottom - mBubbleRadius,
                rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth, rect.bottom);

        path.arcTo(
                new RectF(rect.left, rect.bottom - mBubbleRadius, mBubbleRadius + rect.left, rect.bottom),
                90, 90);

        path.arcTo(new RectF(rect.left, rect.top, mBubbleRadius + rect.left, mBubbleRadius + rect.top),
                180, 90);
        path.close();
    }

    private void setUpBottomPath(RectF rect, Path path) {
        float position;
        if (mArrowRelative == ArrowRelative.BEGIN) {
            position = mArrowPosition;
        } else if (mArrowRelative == ArrowRelative.CENTER) {
            position = (rect.right - rect.left) / 2 - mArrowWidth / 2;
        } else if (mArrowRelative == ArrowRelative.END) {
            position = rect.right - mArrowPosition - mArrowWidth;
        }

        path.moveTo(rect.left + mBubbleRadius, rect.top);
        path.lineTo(rect.width() - mBubbleRadius, rect.top);
        path.arcTo(
                new RectF(rect.right - mBubbleRadius, rect.top, rect.right, mBubbleRadius + rect.top), 270,
                90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mBubbleRadius);
        path.arcTo(new RectF(rect.right - mBubbleRadius, rect.bottom - mBubbleRadius - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mBubbleRadius, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mBubbleRadius - mArrowHeight,
                mBubbleRadius + rect.left, rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mBubbleRadius);
        path.arcTo(new RectF(rect.left, rect.top, mBubbleRadius + rect.left, mBubbleRadius + rect.top),
                180, 90);
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
        public static float DEFAULT_BUBBLE_RADIUS = 0;
        public static int DEFAULT_BUBBLE_COLOR = Color.RED;

        private RectF mRect;
        private float mArrowWidth = DEFAULT_ARROW_WIDTH;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private float mArrowPosition = DEFAULT_ARROW_POSITION;
        private float mBubbleRadius = DEFAULT_BUBBLE_RADIUS;
        private int mBubbleColor = DEFAULT_BUBBLE_COLOR;
        private Bitmap mBubbleBitmap;
        private ArrowLocation mArrowLocation = ArrowLocation.LEFT;
        private ArrowRelative mArrowRelative = ArrowRelative.BEGIN;
        private BubbleType mBubbleType = BubbleType.COLOR;


        public Builder rect(RectF rect) {
            this.mRect = rect;
            return this;
        }

        public Builder arrowLocation(ArrowLocation arrowLocation) {
            this.mArrowLocation = arrowLocation;
            return this;
        }

        public Builder arrowRelative(ArrowRelative arrowRelative) {
            this.mArrowRelative = arrowRelative;
            return this;
        }

        public Builder arrowPosition(float arrowPosition) {
            this.mArrowPosition = arrowPosition;
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

        public Builder bubbleRadius(float bubbleRadius) {
            this.mBubbleRadius = bubbleRadius;
            return this;
        }

        public Builder bubbleColor(int bubbleColor) {
            this.mBubbleColor = bubbleColor;
            bubbleType(BubbleType.COLOR);
            return this;
        }

        public Builder bubbleBitmap(Bitmap bubbleBitmap) {
            this.mBubbleBitmap = bubbleBitmap;
            bubbleType(BubbleType.BITMAP);
            return this;
        }

        public Builder bubbleType(BubbleType bubbleType) {
            this.mBubbleType = bubbleType;
            return this;
        }

        public BubbleDrawable build() {
            if (mRect == null) {
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }
            return new BubbleDrawable(this);
        }
    }

    public enum ArrowLocation {
        LEFT(0x00), RIGHT(0x01), TOP(0x02), BOTTOM(0x03);

        private int mValue;

        ArrowLocation(int value) {
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int stateInt) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (stateInt == value.getValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowLocation getDefault() {
            return LEFT;
        }

        public int getValue() {
            return mValue;
        }
    }

    public enum ArrowRelative {
        BEGIN(0x00), CENTER(0x01), END(0x02);

        private int mValue;

        ArrowRelative(int value) {
            this.mValue = value;
        }

        public static ArrowRelative mapIntToValue(int stateInt) {
            for (ArrowRelative value : ArrowRelative.values()) {
                if (stateInt == value.getValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowRelative getDefault() {
            return BEGIN;
        }

        public int getValue() {
            return mValue;
        }
    }

    public enum BubbleType {
        COLOR, BITMAP
    }
}
