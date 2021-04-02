package com.ixinrun.app_base.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 描述：软键盘工具类
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class KeyBoardUtil {

    private KeyBoardUtil() {
    }

    /**
     * 显示或取消软键盘
     *
     * @param view   上下文
     * @param isShow 是否显示软键盘
     */
    public static void keyShow(View view, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (isShow) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 强制显示软键盘
                    if (!imm.isActive()) {
                        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    }
                }
            }, 500);
        } else {
            // 强制取消软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

