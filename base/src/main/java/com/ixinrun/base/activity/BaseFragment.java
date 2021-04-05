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

/**
 * 描述：BaseFragment
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Fragment唯一标识
     */
    protected final String TAG = this.getClass().getName();

    /**
     * 当前fragment的Context
     */
    protected Activity mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getActivity();
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
}
