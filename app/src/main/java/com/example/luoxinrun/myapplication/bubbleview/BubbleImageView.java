//package com.example.luoxinrun.myapplication.bubbleview;
//
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.widget.ImageView;
//
//import com.example.luoxinrun.myapplication.R;
//
//
//
//public class BubbleImageView extends ImageView {
//    private BubbleDrawable bubbleDrawable;
//    private Drawable sourceDrawable;
//    private float mArrowWidth;
//    private float mArrowHeight;
//    private float mBubbleRadius;
//    private Bitmap mBitmap;
//    private float mArrowPosition;
//    private BubbleDrawable.ArrowLocation mArrowLocation;
//    private BubbleDrawable.ArrowRelative mArrowRelative;
//    public BubbleImageView(Context context) {
//        super(context);
//        initView(null);
//    }
//
//    public BubbleImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(attrs);
//    }
//
//    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(attrs);
//    }
//
//    private void initView(AttributeSet attrs){
//        if (attrs != null) {
//            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
//            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
//                    BubbleDrawable.Builder.DEFAULT_ARROW_WIDTH);
//            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
//                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
//            mBubbleRadius = array.getDimension(R.styleable.BubbleView_bubbleRadius,
//                    BubbleDrawable.Builder.DEFAULT_BUBBLE_RADIUS);
//            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
//                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
//            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
//            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
//            int relative = array.getInt(R.styleable.BubbleView_arrowRelative, 0);
//            mArrowRelative = BubbleDrawable.ArrowRelative.mapIntToValue(relative);
//            array.recycle();
//        }
//        setUpPadding();
//    }
//
//    private void setUpPadding() {
//        int left = getPaddingLeft();
//        int right = getPaddingRight();
//        int top = getPaddingTop();
//        int bottom = getPaddingBottom();
//        switch (mArrowLocation) {
//            case LEFT:
//                left += mArrowWidth;
//                break;
//            case RIGHT:
//                right += mArrowWidth;
//                break;
//            case TOP:
//                top += mArrowHeight;
//                break;
//            case BOTTOM:
//                bottom += mArrowHeight;
//                break;
//        }
//        setPadding(left, top, right, bottom);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        if (w > 0 && h > 0){
//            setUp(w, h);
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        setUp();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (bubbleDrawable != null)
//            bubbleDrawable.draw(canvas);
//        super.onDraw(canvas);
//    }
//
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
//
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
//
//    @Override
//    public void setImageBitmap(Bitmap mBitmap) {
//        if (mBitmap == null)
//            return;
//        sourceDrawable = new BitmapDrawable(getResources(), mBitmap);
//        setUp();
//        super.setImageDrawable(bubbleDrawable);
//    }
//
//    @Override
//    public void setImageDrawable(Drawable drawable){
//        if (drawable == null )
//            return;
//        sourceDrawable = drawable;
//    }
//
//    @Override
//    public void setBackground(Drawable background) {
//        if (background == null )
//            return;
//        sourceDrawable = background;
//    }
//
//    @Override
//    public void setImageResource(int res){
//        setImageDrawable(getDrawable(res));
//    }
//
//    private Drawable getDrawable(int res){
//        if (res == 0){
//            throw new IllegalArgumentException("getDrawable res can not be zero");
//        }
//        return getContext().getResources().getDrawable(res);
//    }
//
//    private Bitmap getBitmapFromDrawable(Drawable drawable) {
//        return getBitmapFromDrawable(getContext(), drawable, getWidth(), getHeight(), 25);
//    }
//
//    public static Bitmap getBitmapFromDrawable(Context mContext, Drawable drawable, int width, int height, int defaultSize) {
//        if (drawable == null) {
//            return null;
//        }
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
//        try {
//            Bitmap bitmap;
//            if (width > 0 && height > 0){
//                bitmap = Bitmap.createBitmap(width,
//                        height, Bitmap.Config.ARGB_8888);
//            }else{
//                bitmap = Bitmap.createBitmap(dp2px(mContext, defaultSize),
//                        dp2px(mContext, defaultSize), Bitmap.Config.ARGB_8888);
//            }
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
//            return bitmap;
//        } catch (OutOfMemoryError e) {
//            return null;
//        }
//    }
//
//    public static int dp2px(Context context, int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                context.getResources().getDisplayMetrics());
//    }
//}
