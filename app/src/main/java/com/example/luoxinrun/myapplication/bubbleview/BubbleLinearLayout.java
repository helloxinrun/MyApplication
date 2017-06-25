package com.example.luoxinrun.myapplication.bubbleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class BubbleLinearLayout extends LinearLayout {
    private BubbleViewImpl mBubbleImpl = new BubbleViewImpl();

    public BubbleLinearLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mBubbleImpl.init(this, context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mBubbleImpl.buildBubbleDrawable(getWidth(), getHeight());
    }

}
