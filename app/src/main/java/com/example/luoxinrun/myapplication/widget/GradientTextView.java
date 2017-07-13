package com.example.luoxinrun.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by luoxinrun on 2017/7/11.
 * 颜色渐变TextView，属性太少，可直接在代码中设置，后期若需扩展再添加attrs。
 */

public class GradientTextView extends TextView {
    private TextPaint mPaint;
    private String mTipText;
    private LinearGradient mLinearGradient;
    private StaticLayout myStaticLayout;
    private int[] mColors = new int[]{};
    private int mStartColor = getCurrentTextColor(), mEndColor = getCurrentTextColor();

    public GradientTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        updateView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        int width = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
        if (mColors.length < 2) {
            mLinearGradient = new LinearGradient(0, 0, width, 0, mStartColor, mEndColor, Shader.TileMode.REPEAT);
        } else {
            mLinearGradient = new LinearGradient(0, 0, width, 0, mColors, null, Shader.TileMode.REPEAT);
        }
        mPaint.setShader(mLinearGradient);
        myStaticLayout = new StaticLayout(mTipText, mPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
    }

    public GradientTextView setColors(int[] colors) {
        this.mColors = colors;
        return this;
    }

    public GradientTextView setStartColor(int startColor) {
        this.mStartColor = startColor;
        return this;
    }

    public GradientTextView setEndColor(int endColor) {
        this.mEndColor = endColor;
        return this;
    }

    public void updateView() {
        mPaint = getPaint();
        mPaint.setAntiAlias(true);
        mTipText = getText().toString();
        invalidate();
    }
}
