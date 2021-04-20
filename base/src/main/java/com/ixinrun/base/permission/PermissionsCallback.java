package com.ixinrun.base.permission;

/**
 * 描述: 权限请求回调
 * </p>
 *
 * @author xinrun
 * @date 2021/4/20
 */
public interface PermissionsCallback {
    /**
     * 权限请求回调结果
     *
     * @param granted true 通过； false 不通过
     */
    void onResult(boolean granted);
}
