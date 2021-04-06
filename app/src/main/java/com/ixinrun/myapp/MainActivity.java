package com.ixinrun.myapp;

import android.content.Context;
import android.os.Bundle;

import com.ixinrun.base.activity.BaseActivity;
import com.ixinrun.base.activity.view.IBaseView;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected IBaseView initBaseViewImpl(Context context, String tag) {
        return new BaseViewImpl(context, tag);
    }
}
