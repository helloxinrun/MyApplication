package com.ixinrun.base.permission;


import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 动态权限申请工具类
 * idea from RxPermissions:<a href="https://github.com/tbruyelle/RxPermissions">RxPermissions</a>
 *
 * <pre>
 * PermissionsUtil.with(mContext)
 *                 .request(Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS)
 *                 .execute(new PermissionsCallback() {
 *                     @Override
 *                     public void onResult(boolean granted) {
 *                         if (granted) {
 *                             // todo... 权限通过
 *                         } else {
 *                             // todo... 权限不通过
 *                         }
 *                     }
 *                 });
 * </pre>
 *
 * @author xinrun
 * @date 2021/4/20
 */
public class PermissionsUtil {
    static final String TAG = "PermissionsUtil";

    private final PermissionsFragment mPermissionsFragment;
    private String[] mPermissions;
    private boolean mIsShowDeniedDialog = true;

    /**
     * U can get an instance of PermissionsUtil from here.
     */
    public static PermissionsUtil with(Context context) {
        if (context instanceof FragmentActivity) {
            return new PermissionsUtil((FragmentActivity) context);
        } else if (context instanceof ContextWrapper) {
            return with(((ContextWrapper) context).getBaseContext());
        } else {
            throw new IllegalArgumentException("PermissionsUtil.create -> Context can not cast to FragmentActivity");
        }
    }

    private PermissionsUtil(@NonNull FragmentActivity activity) {
        mPermissionsFragment = getPermissionsFragment(activity);
    }

    /**
     * 添加需要请求的权限
     */
    public PermissionsUtil request(@NonNull String... permissions) {
        return requestArray(permissions);
    }

    /**
     * 添加需要请求的权限(Kotlin 不支持从不定长参数转为 Array)
     */
    public PermissionsUtil requestArray(@NonNull String[] permissions) {
        ensure(permissions);
        mPermissions = permissions;
        return this;
    }

    /**
     * 控制权限被拒绝时, 是否展示 AlertDialog
     *
     * @param isShowDeniedDialog true is show, false will call {@link PermissionsCallback#onResult(boolean)} immediate.
     */
    public PermissionsUtil isShowDeniedDialog(boolean isShowDeniedDialog) {
        mIsShowDeniedDialog = isShowDeniedDialog;
        return this;
    }

    /**
     * 执行权限请求
     */
    public void execute(@NonNull PermissionsCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("PermissionsUtil.execute -> parameter permissionsCallback must not be null");
        }
        requestInternal(mPermissions, callback);
    }

    /**
     * 判断权限是否被授权
     */
    public boolean isGranted(@NonNull String permission) {
        return !isMarshmallow() || (mPermissionsFragment != null && mPermissionsFragment.isGranted(permission));
    }

    /**
     * 判断权限是否被撤回
     * <p>
     * Always false if SDK &lt; 23.
     */
    public boolean isRevoked(@NonNull String permission) {
        return isMarshmallow() && (mPermissionsFragment != null && mPermissionsFragment.isRevoked(permission));
    }

    /**
     * 获取 PermissionsFragment
     */
    private PermissionsFragment getPermissionsFragment(FragmentActivity activity) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }

        PermissionsFragment frag = findPermissionsFragment(activity);
        if (frag == null) {
            frag = PermissionsFragment.getInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(frag, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return frag;
    }

    /**
     * 在 Activity 中通过 TAG 去寻找我们添加的 Fragment
     */
    @Nullable
    private PermissionsFragment findPermissionsFragment(FragmentActivity activity) {
        return (PermissionsFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    /**
     * 验证发起请求的权限是否有效
     */
    private void ensure(String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("PermissionsUtil.request -> requestEach requires at least one input permission");
        }
    }

    /**
     * 执行权限请求
     */
    private void requestInternal(@NonNull String[] permissions, @NonNull PermissionsCallback callback) {
        if (mPermissionsFragment == null) {
            return;
        }
        List<String> unrequestedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            mPermissionsFragment.log("Requesting permission -> " + permission);
            if (isGranted(permission)) {
                // Already granted, or not Android M
                // Return a granted Permission object.
                continue;
            }
            if (isRevoked(permission)) {
                // Revoked by a policy, return a denied Permission object.
                continue;
            }
            unrequestedPermissions.add(permission);
        }
        if (!unrequestedPermissions.isEmpty()) {
            String[] unrequestedPermissionsArray = unrequestedPermissions.toArray(
                    new String[unrequestedPermissions.size()]);
            mPermissionsFragment.requestPermissions(unrequestedPermissionsArray, mIsShowDeniedDialog, callback);
        } else {
            callback.onResult(true);
        }
    }

    private boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
