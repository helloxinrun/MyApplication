package com.ixinrun.base.activity.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.ixinrun.base.activity.BaseLifecycle;
import com.ixinrun.base.activity.view.IBaseView;

/**
 * 描述: BasePresenter
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public abstract class BasePresenter<V extends IBaseView> extends BaseLifecycle {

    protected V mView;

    public BasePresenter(@NonNull V view) {
        this.mView = view;
        if (view != null && view instanceof LifecycleOwner) {
            addObserver((LifecycleOwner) view);
        }
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public void detachView() {
        if (mView != null) {
            mView.detachView();
        }
    }
}
