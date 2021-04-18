package com.ixinrun.base.activity.view;

import androidx.annotation.MainThread;

/**
 * 描述: 基础view接口，服务于项目中的Activity和Fragment
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/6
 */
@MainThread
public interface IBaseView extends ITipView, IProgressView {
    /**
     * 解绑视图
     */
    void detachView();
}
