package com.example.luoxinrun.myapplication.bubbleview;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
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
}
