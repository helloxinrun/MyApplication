package com.ixinrun.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ixinrun.base.activity.view.IBaseView;

/**
 * 描述：BaseFragment
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    /**
     * 唯一标识
     */
    protected final String mTag = initTag();

    /**
     * 当前fragment的Context
     */
    protected Activity mContext;

    /**
     * IBaseView的包装实现类
     */
    private IBaseView mWrapView;

    /**
     * 初始化唯一标识
     *
     * @return
     */
    protected String initTag() {
        return getClass().getName();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getActivity();
        mWrapView = initBaseViewImpl(mContext, mTag);
        if (mWrapView == null) {
            throw new NullPointerException("BaseFragment's BaseViewImpl is NULL and you must initialize it.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = initView(inflater, container);
        initEvent();
        loadData(savedInstanceState);

        return view;
    }

    /**
     * 初始化View视图
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化View事件
     */
    private void initEvent() {
    }

    /**
     * 加载数据
     */
    protected abstract void loadData(@Nullable Bundle savedInstanceState);

    /**
     * ---------------------------------------view操作代理------------------------------------
     */

    @Override
    public void showProgress() {
        mWrapView.showProgress();
    }

    @Override
    public void showProgress(boolean cancelable) {
        mWrapView.showProgress(cancelable);
    }

    @Override
    public void closeProgress() {
        mWrapView.closeProgress();
    }

    @Override
    public void tip(@NonNull String msg) {
        mWrapView.tip(msg);
    }

    @Override
    public void tip(int resId) {
        mWrapView.tip(resId);
    }

    @Override
    public void tip(@NonNull String msg, @NonNull TipEnum tipEnum) {
        mWrapView.tip(msg, tipEnum);
    }

    @Override
    public void tip(int resId, @NonNull TipEnum tipEnum) {
        mWrapView.tip(resId, tipEnum);
    }

    @Override
    public void onViewDestroy() {
        mWrapView.onViewDestroy();
    }

    /**
     * IBaseView包装实现
     *
     * @return BaseViewImpl
     */
    protected abstract IBaseView initBaseViewImpl(Context context, String tag);
}
