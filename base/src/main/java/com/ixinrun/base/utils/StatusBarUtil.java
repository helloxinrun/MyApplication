package com.ixinrun.base.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

/**
 * 描述：状态栏工具
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class StatusBarUtil {

    private StatusBarUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 上下文
     */
    public static void immersive(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = window.getDecorView().getSystemUiVisibility()
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 上下文
     * @param color    颜色
     */
    public static void immersive(Activity activity, int color) {
        immersive(activity, color, 0);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 上下文
     * @param color    颜色
     * @param alpha    透明度
     */
    public static void immersive(Activity activity, int color, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(calculateStatusColor(color, alpha));
    }

    /**
     * 设置状态栏主题
     *
     * @param activity    上下文
     * @param isLightMode true 白色主题深色文字；false 深色主题白色问题
     */
    public static void lightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        Window window = activity.getWindow();
        int option = window.getDecorView().getSystemUiVisibility();
        if (isLightMode) {
            option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            option &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        window.getDecorView().setSystemUiVisibility(option);
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
