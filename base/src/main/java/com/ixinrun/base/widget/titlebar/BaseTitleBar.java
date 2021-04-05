package com.ixinrun.base.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ixinrun.base.R;
import com.ixinrun.base.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：标题栏基类，需要二次扩展
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/9/2
 */
public class BaseTitleBar extends FrameLayout {
    public final String TAG = this.getClass().getSimpleName();

    private RelativeLayout mTitleBarRootRl;
    private ImageView mTitleBarBackIv;
    private FrameLayout mTitleBarContentFloatFl;
    private FrameLayout mTitleBarContentIncludeFl;
    private LinearLayout mTitleBarActionsLl;

    private int mTitleBarColor = initTitleBarColor();
    private boolean mBackIconVisible = true;
    private int mBackIcon = R.drawable.titlebar_back_ic;
    private int mActionPadding = 10;
    private int mActionSpacing = 5;
    private int mActionTextSize = 16;
    private int mActionTextColor = Color.parseColor("#333333");
    private int mActionIconWidth = 40;
    private int mActionIconHeight = 40;
    private final List<View> mActions = new ArrayList<>();
    private IOnBackListener mListener;

    public BaseTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public BaseTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
        initView(context);
    }

    private void parseAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        // default values from DIP to pixels.
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mActionPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mActionPadding, metrics);
        mActionSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mActionSpacing, metrics);
        mActionIconWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mActionIconWidth, metrics);
        mActionIconHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mActionIconHeight, metrics);

        // parse attribute.
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
        mTitleBarColor = a.getColor(R.styleable.BaseTitleBar_tb_root_bg, mTitleBarColor);
        mBackIconVisible = a.getBoolean(R.styleable.BaseTitleBar_tb_back_icon_visible, mBackIconVisible);
        mBackIcon = a.getResourceId(R.styleable.BaseTitleBar_tb_back_icon, mBackIcon);
        mActionPadding = (int) a.getDimension(R.styleable.BaseTitleBar_tb_action_padding, mActionPadding);
        mActionSpacing = (int) a.getDimension(R.styleable.BaseTitleBar_tb_action_spacing, mActionSpacing);
        mActionTextSize = (int) a.getDimension(R.styleable.BaseTitleBar_tb_action_text_size, mActionTextSize);
        mActionTextColor = a.getColor(R.styleable.BaseTitleBar_tb_action_text_color, mActionTextColor);
        mActionIconWidth = (int) a.getDimension(R.styleable.BaseTitleBar_tb_action_icon_width, mActionIconWidth);
        mActionIconHeight = (int) a.getDimension(R.styleable.BaseTitleBar_tb_action_icon_height, mActionIconHeight);

        // recycle.
        a.recycle();
    }

    private void initView(@NonNull Context context) {
        //init titlebar.
        LayoutInflater.from(context).inflate(R.layout.titlebar_base_layout, this);
        mTitleBarRootRl = findViewById(R.id.titlebar_root_rl);
        mTitleBarBackIv = findViewById(R.id.titlebar_back_iv);
        mTitleBarContentFloatFl = findViewById(R.id.titlebar_content_float_fl);
        mTitleBarContentIncludeFl = findViewById(R.id.titlebar_content_inclued_fl);
        mTitleBarActionsLl = findViewById(R.id.titlebar_actions_ll);

        //set background color.
        invalidateTitleBarColor(mTitleBarColor);

        //set back icon.
        mTitleBarBackIv.setVisibility(VISIBLE);
        mTitleBarBackIv.setImageResource(mBackIcon);
        mTitleBarBackIv.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onBack();
            } else if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });
        setBackIconVisible(mBackIconVisible);
    }

    protected int initTitleBarColor() {
        return Color.WHITE;
    }

    private void invalidateTitleBarColor(@ColorInt int color) {
        //set statusbar.
        if (getContext() instanceof Activity) {
            StatusBarUtil.immersive((Activity) getContext(), color);
        }
        //set titlebar
        mTitleBarRootRl.setBackgroundColor(color);
    }

    /**
     * set center view
     *
     * @param view
     * @param isFloat if true，It's not constrained by the left or right view(actions)，
     *                the default gravity is Absolute center.
     */
    public void setCenterView(View view, boolean isFloat) {
        if (isFloat) {
            mTitleBarContentFloatFl.addView(view);
        } else {
            mTitleBarContentIncludeFl.addView(view);
        }
    }

    /**
     * set titlebar color.
     *
     * @param color
     * @return
     */
    public BaseTitleBar setTitleBarColor(@ColorInt int color) {
        this.mTitleBarColor = color;
        invalidateTitleBarColor(color);
        return this;
    }

    public BaseTitleBar setBackIconVisible(boolean visible) {
        mTitleBarBackIv.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * add action with textview.
     *
     * @param s
     * @return
     */
    public BaseTitleBar addAction(@NonNull String s, OnClickListener l) {
        TextView textView = new TextView(getContext());
        textView.setText(s);
        textView.setTextSize(mActionTextSize);
        textView.setTextColor(mActionTextColor);
        addActionView(textView, l);
        return this;
    }

    /**
     * add action with drawable.
     *
     * @param resId
     * @return
     */
    public BaseTitleBar addAction(@DrawableRes int resId, OnClickListener l) {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(mActionIconWidth, mActionIconHeight));
        imageView.setImageResource(resId);
        addActionView(imageView, l);
        return this;
    }

    private void addActionView(View view, OnClickListener l) {
        view.setPadding(mActionPadding, mActionPadding, mActionPadding, mActionPadding);
        mActions.add(view);
        mTitleBarActionsLl.addView(view);
        // set spacing between actions.
        if (mActions.size() > 1) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.leftMargin = mActionSpacing;
            view.setLayoutParams(lp);
        }
        view.setOnClickListener(l);
    }

    /**
     * get right action with index.
     *
     * @param index
     * @return
     */
    public View getActionView(int index) {
        return mActions.get(index);
    }

    /**
     * left back action's callback.
     *
     * @param listener
     * @return
     */
    public BaseTitleBar setOnBackListener(IOnBackListener listener) {
        this.mListener = listener;
        return this;
    }

    public interface IOnBackListener {
        void onBack();
    }
}
