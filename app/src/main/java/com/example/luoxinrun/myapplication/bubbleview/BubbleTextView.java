package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
    
//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//
//        // 获取宽-测量规则的模式和大小
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        // 获取高-测量规则的模式和大小
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        // 设置wrap_content的默认宽 / 高值
//        // 默认宽/高的设定并无固定依据,根据需要灵活设置
//        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
//        int mWidth = 400;
//        int mHeight = 400;
//
//        // 当布局参数设置为wrap_content时，设置默认值
//        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(mWidth, mHeight);
//            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
//        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(mWidth, heightSize);
//        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(widthSize, mHeight);
//        }
//    }
}
