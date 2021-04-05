package com.ixinrun.base.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * 描述：屏幕工具类
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class ScreenUtil {

    private static Application getApp() {
        return AppUtil.getApp();
    }

    /**
     * 获取屏幕宽
     *
     * @return
     */
    public static int getScreenWidth() {
        try {
            WindowManager wm = (WindowManager) getApp().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取屏幕高
     *
     * @return
     */
    public static int getScreenHeight() {
        try {
            WindowManager wm = (WindowManager) getApp().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
