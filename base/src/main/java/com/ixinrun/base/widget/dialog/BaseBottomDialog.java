package com.ixinrun.base.widget.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.ixinrun.base.R;


/**
 * 描述：底部弹框
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/9/2
 */
public class BaseBottomDialog<T extends BaseBottomDialog> extends BaseDialog<T> {
    public BaseBottomDialog(@NonNull Context context) {
        super(context);
        setDialogWidth(getScreenWidth());
        setGravity(Gravity.BOTTOM);
        setWindowAnimations(R.style.BaseBottomDialogStyle);
    }

    /**
     * 获取屏幕宽
     *
     * @return
     */
    private int getScreenWidth() {
        try {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
