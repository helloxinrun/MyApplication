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
    protected final String mTag = getClass().getName();

    /**
     * 当前fragment的Context
     */
    protected Activity mContext;

    /**
     * IBaseView的包装实现类
     */
    private IBaseView mWrapView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getActivity();
        mWrapView = initBaseViewImpl(mContext, mTag);
        if (mWrapView == null) {
            throw new NullPointerException("BaseFragment's BaseViewImpl is NULL and you must initialize it.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return initView(inflater, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent();
        loadData(savedInstanceState);
    }

    /**
     * 初始化view之前的相关操作
     */
    protected void init() {
    }

    /**
     * 初始化View视图
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化View事件
     */
    protected void initEvent() {
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
    public void detachView() {
        mWrapView.detachView();
    }

    /**
     * IBaseView包装实现
     *
     * @return BaseViewImpl
     */
    protected abstract IBaseView initBaseViewImpl(Context context, String tag);
}
