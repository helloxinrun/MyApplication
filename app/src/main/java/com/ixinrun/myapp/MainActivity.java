package com.ixinrun.myapp;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ixinrun.base.activity.BaseActivity;
import com.ixinrun.base.activity.view.IBaseView;
import com.ixinrun.base.img.ImageLoaderMgr;
import com.ixinrun.base.permission.PermissionMgr;
import com.ixinrun.base.utils.DateUtil;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView iv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        ImageLoaderMgr.getInstance()
                .builder()
                .signature(DateUtil.getCurrent(DateUtil.FORMAT_YMD))
                .build()
                .load("https://api.kdcc.cn/img/", iv);

        permission();
    }

    private void permission() {
        PermissionMgr.with(mContext)
                .addRequestCode(300)
                .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS)
                .request(new PermissionMgr.PermissionCallback() {
                    @Override
                    public void permissionSuccess(int requestCode) {
                        Toast.makeText(mContext, "成功授予联系人权限，请求码： " + requestCode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionFail(int requestCode, List<String> deniedPermissions) {
                        for (String s : deniedPermissions) {
                            Toast.makeText(mContext, "权限失败，未通过权限： " + s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionMgr.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected IBaseView initBaseViewImpl(Context context, String tag) {
        return new BaseViewImpl(context, tag);
    }
}
