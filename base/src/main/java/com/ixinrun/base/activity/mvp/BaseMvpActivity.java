package com.ixinrun.base.activity.mvp;

import com.ixinrun.base.activity.BaseActivity;

/**
 * 描述:
 * </p>
 *
 * @author xinrun
 * @date 2021/4/16
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void init() {
        super.init();
        mPresenter = creatPresenter();
    }

    public abstract P creatPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}