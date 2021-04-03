package com.ixinrun.app_base.widget.dialog;

import android.app.Dialog;
import android.content.Context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 功能描述: dialog辅助工具类
 * 执行show和dismiss操作可通过工具类中show方法进行安全处理。
 * </p>
 * 创建人: luoxinrun
 * 创建时间: 2018/8/4
 */
public class DialogUtil {

    /**
     * dialog队列，方便dialog统一销毁
     */
    private static Map<Integer, Set<Dialog>> sDialogMap = new HashMap<>();

    /**
     * dialog入队
     *
     * @param context
     * @param dialog
     */
    public static void enqueue(Context context, Dialog dialog) {
        if (context == null || dialog == null) {
            return;
        }
        //此处的context仅用于key的生成，不可采用dialog内部的context.
        int key = context.hashCode();
        Set<Dialog> dialogs = sDialogMap.get(key);
        if (dialogs == null) {
            dialogs = new HashSet<>();
        }
        dialogs.add(dialog);
        sDialogMap.put(key, dialogs);
    }

    /**
     * dialog出队
     *
     * @param context
     * @param dialog
     */
    public static void dequeue(Context context, Dialog dialog) {
        if (context == null || dialog == null) {
            return;
        }
        int key = context.hashCode();
        Set<Dialog> dialogs = sDialogMap.get(key);
        if (dialogs == null) {
            return;
        }
        if (dialogs.contains(dialog)) {
            dialogs.remove(dialog);
        }
    }

    /**
     * 移除context上所有dialog
     */
    public static void dismissAll(Context context) {
        if (context == null) {
            return;
        }
        int key = context.hashCode();
        Set<Dialog> dialogs = sDialogMap.get(key);
        if (dialogs == null) {
            return;
        }
        try {
            for (Dialog dialog : dialogs) {
                if (!dialog.isShowing()) {
                    continue;
                }
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sDialogMap.remove(key);
    }

    /**
     * 弹框显示或取消
     *
     * @param show 显示或取消
     */
    public static void show(Dialog dialog, boolean show) {
        if (dialog == null) {
            return;
        }
        if (show) {
            if (dialog.getOwnerActivity() == null || dialog.getOwnerActivity().isFinishing()) {
                return;
            }
            //显示弹框
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } else {
            //关闭弹框
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
