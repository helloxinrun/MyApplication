package com.ixinrun.base.activity.view;

import androidx.annotation.MainThread;

/**
 * 描述: 进度条接口，用于实现UI层上的统一进度加载展示
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
@MainThread
public interface IProgressView {

    /**
     * 弹出加载框
     */
    void showProgress();

    /**
     * 弹出加载框
     *
     * @param cancelable 加载框是否可以取消
     */
    void showProgress(boolean cancelable);

    /**
     * 关闭对话框
     */
    void closeProgress();
}
