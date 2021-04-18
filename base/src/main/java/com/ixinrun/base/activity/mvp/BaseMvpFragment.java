package com.ixinrun.base.activity.mvp;

import com.ixinrun.base.activity.BaseFragment;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    protected void init() {
        super.init();
        mPresenter = creatPresenter();
    }

    public abstract P creatPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
