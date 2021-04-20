package com.ixinrun.base.permission;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

/**
 * 描述: 权限请求桥接类
 * </p>
 *
 * @author xinrun
 * @date 2021/4/20
 */
public class PermissionsFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSIONS = 121;
    private static final int REQUEST_CODE_SETTING = 70;
    private static final String ARGS_REQUESTING_PERMISSIONS = "args_requesting_permissions";

    private Context mContext;
    private PermissionsCallback mCallback;
    private String[] mPermissions;
    private boolean mIsShowDeniedDialog;

    public static PermissionsFragment getInstance() {
        PermissionsFragment fragment = new PermissionsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CODE_PERMISSIONS) {
            return;
        }
        boolean isAllGranted = true;
        for (int i = 0, size = permissions.length; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                log("onRequestPermissionsResult: " + permissions[i] + " is Granted");
            } else {
                log("onRequestPermissionsResult: " + permissions[i] + " is Denied");
                isAllGranted = false;
            }
        }
        if (isAllGranted) {
            mCallback.onResult(true);
        } else if (mIsShowDeniedDialog) {
            showPermissionDeniedDialog();
        } else {
            mCallback.onResult(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_SETTING) {
            return;
        }

        // 防止小米等设备, 从设置页面返回之后, Fragment 重建导致的数据丢失
        if (mPermissions == null) {
            if (getArguments() != null) {
                mPermissions = getArguments().getStringArray(ARGS_REQUESTING_PERMISSIONS);
            }
        }

        if (mCallback == null) {
            if (mContext instanceof PermissionsCallback) {
                mCallback = (PermissionsCallback) mContext;
            } else if (getParentFragment() instanceof PermissionsCallback) {
                mCallback = (PermissionsCallback) getParentFragment();
            } else {
                // do nothing.
            }
        }

        // 从设置页面回来时, 重新检测一次申请的权限是否都已经授权
        if (mCallback != null && mPermissions != null && mPermissions.length > 0) {
            boolean isAllGranted = true;
            for (String permission : mPermissions) {
                if (!isGranted(permission)) {
                    isAllGranted = false;
                    break;
                }
            }
            mCallback.onResult(isAllGranted);
        }

        // 移除上一次请求的缓存
        if (getArguments() != null) {
            getArguments().remove(ARGS_REQUESTING_PERMISSIONS);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions,
                                   boolean isShowDeniedDialog,
                                   PermissionsCallback callback) {
        // 缓存权限到 Bundle
        if (getArguments() != null) {
            getArguments().putStringArray(ARGS_REQUESTING_PERMISSIONS, permissions);
        }
        this.mPermissions = permissions;
        this.mIsShowDeniedDialog = isShowDeniedDialog;
        this.mCallback = callback;
        requestPermissions(mPermissions, REQUEST_CODE_PERMISSIONS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isGranted(@NonNull String permission) {
        return mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isRevoked(@NonNull String permission) {
        return mContext.getPackageManager().isPermissionRevokedByPolicy(permission, mContext.getPackageName());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showPermissionDeniedDialog() {
        Activity activity = getActivity();
        if (activity == null || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && activity.isDestroyed()) || activity.isFinishing()) {
            return;
        }
        //启动当前App的系统设置界面
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("帮助")
                .setMessage("当前应用缺少必要权限")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.onResult(false);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 启动当前App的系统设置界面
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                        startActivityForResult(intent, REQUEST_CODE_SETTING);
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    void log(String message) {
        Log.i(PermissionsUtil.TAG, message);
    }
}
