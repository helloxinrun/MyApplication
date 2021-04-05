package com.ixinrun.base.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.ixinrun.base.R;

/**
 * 描述：dialog基类
 * 基本方法：
 * 1.setDialogWidth
 * 2.setDialogHeight
 * 3.setGravity
 * 4.setWindowAnimations
 * 5.setAutoDismiss
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/9/2
 */
public class BaseDialog<T extends BaseDialog> extends Dialog {
    private int mDialogWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mDialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mDialogGravity = Gravity.CENTER;
    private int mAnimationResId;

    private boolean isAutoDismiss = true;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialogStyle);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getWindow() != null) {
            getWindow().setLayout(mDialogWidth, mDialogHeight);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setGravity(mDialogGravity);
            if (mAnimationResId != 0) {
                getWindow().setWindowAnimations(mAnimationResId);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    getWindow().setElevation(4);
                }
                getWindow().getDecorView().setElevation(4);
            }
        }
    }

    /**
     * 设定dialog的宽
     *
     * @param width
     * @return
     */
    public T setDialogWidth(int width) {
        this.mDialogWidth = width <= 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : width;
        return (T) this;
    }

    /**
     * 设定dialog的高
     *
     * @param height
     * @return
     */
    public T setDialogHeight(int height) {
        this.mDialogHeight = height <= 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : height;
        return (T) this;
    }

    /**
     * 设定dialog的位置
     *
     * @param gravity
     * @return
     */
    public T setGravity(int gravity) {
        this.mDialogGravity = gravity;
        return (T) this;
    }

    /**
     * 设置dialog动画
     *
     * @param resId
     * @return
     */
    public T setWindowAnimations(@StyleRes int resId) {
        mAnimationResId = resId;
        return (T) this;
    }

    /**
     * 设定点击dialog按钮，dialog是否自动消失(供按钮使用)
     *
     * @param isAutoDismiss
     * @return
     */
    public T setClickAutoDismiss(boolean isAutoDismiss) {
        this.isAutoDismiss = isAutoDismiss;
        return (T) this;
    }

    /**
     * 点击dialog按钮是否自动取消
     *
     * @return
     */
    public boolean isClickAutoDismiss() {
        return isAutoDismiss;
    }

    @Override
    public void show() {
        if (getOwnerActivity() == null || getOwnerActivity().isFinishing()) {
            return;
        }
        if (!isShowing()) {
            super.show();
        }
    }
}
