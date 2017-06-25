package com.example.luoxinrun.myapplication.bubbleview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;



public class BubbleImageView extends ImageView {
    private BubbleViewImpl mBubbleImpl = new BubbleViewImpl();

    public BubbleImageView(Context context) {
        super(context);
        initView(context, null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs){
        mBubbleImpl.init(this, context ,attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mBubbleImpl.buildBubbleDrawable(getWidth(), getHeight());
    }

//    private void setUp(){
//        int width = getWidth();
//        int height = getHeight();
//        int scale;
//
//        if (width > 0 && height <= 0 && sourceDrawable != null){
//            if (sourceDrawable.getIntrinsicWidth() >= 0){
//                scale = width / sourceDrawable.getIntrinsicWidth();
//                height = scale * sourceDrawable.getIntrinsicHeight();
//            }
//        }
//
//        if (height > 0 &&  width <= 0 && sourceDrawable != null){
//            if (sourceDrawable.getIntrinsicHeight() >= 0){
//                scale = height / sourceDrawable.getIntrinsicHeight();
//                width = scale * sourceDrawable.getIntrinsicWidth();
//            }
//        }
//        setUp(width, height);
//    }

//    private void setUp(int width, int height){
//        setUp(0, 0, width, height);
//    }
//
//    private void setUp(int left,  int top, int right, int bottom){
//        RectF rectF = new RectF(left, top, right, bottom);
//        if (sourceDrawable != null)
//            mBitmap = getBitmapFromDrawable(sourceDrawable);
//        bubbleDrawable = new BubbleDrawable.Builder()
//                .rect(rectF)
//                .bubbleType(BubbleDrawable.BubbleType.BITMAP)
//                .arrowWidth(mArrowWidth)
//                .arrowHeight(mArrowHeight)
//                .arrowLocation(mArrowLocation)
//                .arrowRelative(mArrowRelative)
//                .arrowPosition(mArrowPosition)
//                .bubbleRadius(mBubbleRadius)
//                .bubbleBitmap(mBitmap)
//                .build();
//    }

//    @Override
//    public void setImageBitmap(Bitmap mBitmap) {
//        if (mBitmap == null)
//            return;
//        sourceDrawable = new BitmapDrawable(getResources(), mBitmap);
//        setUp();
//        super.setImageDrawable(bubbleDrawable);
//    }

//    @Override
//    public void setImageDrawable(Drawable drawable){
//        this.mBackground = drawable;
//    }
//
//    @Override
//    public void setBackground(Drawable background) {
//        this.mBackground = background;
//    }
//
//    @Override
//    public void setImageResource(int res){
//        this.mBackground = getDrawable(res);
//    }
//
//    private Drawable getDrawable(int res){
//        if (res == 0){
//            throw new IllegalArgumentException("getDrawable res can not be zero");
//        }
//        return getContext().getResources().getDrawable(res);
//    }
}
