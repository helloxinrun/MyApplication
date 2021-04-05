package com.ixinrun.base.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ixinrun.base.R;

/**
 * 描述：标题栏
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/9/2
 */
public class TitleBar extends BaseTitleBar {
    private TextView mTitleTv;
    private TextView mSubtitleTv;

    private String mTitleText;
    private int mTitleColor = Color.parseColor("#333333");
    private String mSubtitleText;
    private int mSubtitleColor = mTitleColor;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
        initView(context);
    }

    private void parseAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitleText = a.getString(R.styleable.TitleBar_title_text);
        mTitleColor = a.getColor(R.styleable.TitleBar_title_color, mTitleColor);
        mSubtitleText = a.getString(R.styleable.TitleBar_subtitle_text);
        mSubtitleColor = a.getColor(R.styleable.TitleBar_subtitle_color, mSubtitleColor);

        // recycle.
        a.recycle();
    }

    private void initView(@NonNull Context context) {
        View view = View.inflate(context, R.layout.titlebar_center_layout, null);
        setCenterView(view, true);
        mTitleTv = view.findViewById(R.id.title_tv);
        mSubtitleTv = view.findViewById(R.id.subtitle_tv);

        //set title.
        mTitleTv.setText(mTitleText);
        mTitleTv.setTextColor(mTitleColor);
        if (!TextUtils.isEmpty(mSubtitleText)) {
            mSubtitleTv.setVisibility(VISIBLE);
            mSubtitleTv.setText(mSubtitleText);
            mSubtitleTv.setTextColor(mSubtitleColor);
        } else {
            mSubtitleTv.setVisibility(GONE);
        }
    }

    /**
     * set title.
     *
     * @param s
     * @return
     */
    public TitleBar setTitle(@NonNull String s) {
        this.mTitleText = s;
        mTitleTv.setText(mTitleText);
        return this;
    }

    /**
     * set subtitle.
     *
     * @param s
     * @return
     */
    public TitleBar setSubTitle(@NonNull String s) {
        this.mSubtitleText = s;
        mSubtitleTv.setVisibility(VISIBLE);
        mSubtitleTv.setText(mSubtitleText);
        return this;
    }

    /**
     * get title's view.
     *
     * @return
     */
    public TextView getTitleTextView() {
        return mTitleTv;
    }

    /**
     * get subtitle's view.
     *
     * @return
     */
    public TextView getSubtitleTextView() {
        return mSubtitleTv;
    }
}
