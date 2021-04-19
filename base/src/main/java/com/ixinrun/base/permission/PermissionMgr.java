package com.ixinrun.base.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：动态权限申请
 * <pre>
 * // 请求
 * PermissionMgr.with(mContext)
 *                 .addRequestCode(300)
 *                 .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS)
 *                 .request(new PermissionMgr.PermissionCallback() {
 *                     @Override
 *                     public void permissionSuccess(int requestCode) {
 *                         Toast.makeText(mContext, "权限通过，请求码： " + requestCode, Toast.LENGTH_SHORT).show();
 *                     }
 *
 *                     @Override
 *                     public void permissionFail(int requestCode, List<String> deniedPermissions) {
 *                         for (String s : deniedPermissions) {
 *                             Toast.makeText(mContext, "权限失败，未通过权限： " + s, Toast.LENGTH_SHORT).show();
 *                         }
 *                     }
 *                 });
 *
 * // 在Activity的onRequestPermissionsResult方法中复写
 * PermissionMgr.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * </pre>
 *
 *
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public class PermissionMgr {
    private String[] mPermissions;
    private int mRequestCode;
    private Object object;
    private static PermissionCallback permissionCallback;

    private PermissionMgr(Object object) {
        this.object = object;
    }

    public static PermissionMgr with(Activity activity) {
        return new PermissionMgr(activity);
    }

    public static PermissionMgr with(Fragment fragment) {
        return new PermissionMgr(fragment);
    }

    /**
     * 获取权限组集合
     *
     * @param permissions
     * @return
     */
    public PermissionMgr permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * 添加请求码
     *
     * @param requestCode
     * @return
     */
    public PermissionMgr addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 请求权限
     *
     * @param callback
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public void request(PermissionCallback callback) {
        if (callback != null) {
            permissionCallback = callback;
        }
        requestPermissions(object, mRequestCode, mPermissions);
    }

    /**
     * 请求权限
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private void requestPermissions(Object object, int requestCode, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (permissionCallback != null) {
                permissionCallback.permissionSuccess(requestCode);
            }
            return;
        }
        List<String> denieds = findDeniedPermissions(getActivity(object), permissions);

        if (denieds.size() > 0) {
            if (object instanceof Activity) {
                ((Activity) object).requestPermissions(denieds.toArray(new String[denieds.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(denieds.toArray(new String[denieds.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }

        } else {
            if (permissionCallback != null) {
                permissionCallback.permissionSuccess(requestCode);
            }
        }
    }

    /**
     * 从申请的权限中找出未授予的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    private Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * 回调接口不为空的话，先执行回调接口的方法，若为空，则寻找响应的注解方法。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.size() > 0) {
            if (permissionCallback != null) {
                permissionCallback.permissionFail(requestCode, deniedPermissions);
            }
        } else {
            if (permissionCallback != null) {
                permissionCallback.permissionSuccess(requestCode);
            }
        }
    }

    public interface PermissionCallback {
        /**
         * 请求权限成功
         *
         * @param requsetCode
         */
        void permissionSuccess(int requsetCode);

        /**
         * 请求权限失败
         *
         * @param requestCode
         */
        void permissionFail(int requestCode, List<String> deniedPermissions);
    }
}
