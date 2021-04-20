package com.ixinrun.myapp;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.ixinrun.base.activity.BaseActivity;
import com.ixinrun.base.activity.view.IBaseView;
import com.ixinrun.base.img.ImageLoaderMgr;
import com.ixinrun.base.permission.PermissionsCallback;
import com.ixinrun.base.permission.PermissionsUtil;
import com.ixinrun.base.utils.DateUtil;

public class MainActivity extends BaseActivity {

    private ImageView iv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        permission();

        ImageLoaderMgr.getInstance()
                .builder()
                .signature(DateUtil.getCurrent(DateUtil.FORMAT_YMD))
                .build()
                .load("https://api.kdcc.cn/img/", iv);
    }

    private void permission() {
        PermissionsUtil.with(mContext)
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS)
                .execute(new PermissionsCallback() {
                    @Override
                    public void onResult(boolean granted) {
                        if (granted) {
                            // todo... 权限通过
                        } else {
                            // todo... 权限不通过
                        }
                    }
                });
    }

    @Override
    protected IBaseView initBaseViewImpl(Context context, String tag) {
        return new BaseViewImpl(context, tag);
    }
}
