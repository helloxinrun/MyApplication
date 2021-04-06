package com.ixinrun.base.activity.view;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * 描述: 提示接口，用于将来实现UI层上的统一提示
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
@MainThread
public interface ITipView {

    /**
     * 提示类型
     */
    enum TipEnum {
        // 提示的类型：
        // Normal：普通的提示
        // Error：错误的类型
        // MsgBox 弹框的类型
        Normal, Error, MsgBox
    }

    /**
     * 默认提示
     *
     * @param msg 提示文本
     */
    void tip(@NonNull String msg);

    /**
     * 默认提示
     *
     * @param resId 资源的ID
     */
    void tip(@StringRes int resId);

    /**
     * 提示
     *
     * @param msg     提示文本
     * @param tipEnum 提示类型
     */
    void tip(@NonNull String msg, @NonNull TipEnum tipEnum);

    /**
     * 提示
     *
     * @param resId   资源ID
     * @param tipEnum 提示类型
     */
    void tip(@StringRes int resId, @NonNull TipEnum tipEnum);
}
