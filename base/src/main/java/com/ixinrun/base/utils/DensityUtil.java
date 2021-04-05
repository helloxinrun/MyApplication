package com.ixinrun.base.utils;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;


/**
 * 描述：屏幕密度工具类
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 根据应用自身dpi，dp转为px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpi2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据应用自身dpi，px转为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dpi(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据应用自身dpi，sp转为px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * scale + 0.5f);
    }

    /**
     * 根据应用自身dpi，px转为sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2sp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * 根据手机系统默认的dpi，dp转成px
     *
     * @param dpVal
     * @return
     */
    public static int sysDp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机系统默认的dpi，px转成dp
     *
     * @param pxVal
     * @return
     */
    public static float sysPx2dp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * 根据手机系统默认的dpi，sp转成px
     *
     * @param spVal
     * @return
     */
    public static int sysSp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 根据手机系统默认的dpi，px转成sp
     *
     * @param pxVal
     * @return
     */
    public static float sysPx2sp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (pxVal / scale);
    }

    /**
     * 重置屏幕密度，必须在View重绘前处理。
     * 此方法需要在以下两个地方注册：
     * 1. Application的onCreat、onConfigurationChanged；
     * 2. Activity中的onCreat(setContentView之前)、onConfigurationChanged；
     * <p>
     * Activity的情况推荐在BaseActivity中注册，能够更好的根据生命周期走，
     * 也可以在ActivityLifecycleCallbacks中处理，来应对所有类型的activity。
     * <p>
     * 如果应用有多种屏幕密度兼容，需要在onResume中也要注册，
     * 避免受返回到当前页面时需要重新绘制页面（此时沿用上个页面的屏幕密度）而影响此页面的布局。
     *
     * @param context           上下文
     * @param DESIGN_WIDTH      参照的设计图宽
     * @param DESIGN_HEIGHT     参照的设计图高
     * @param DESTGN_INCH       参照的设计图对应设备尺寸（例如4.7寸、5.0寸）
     * @param BIG_SCREEN_FACTOR 大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度。
     */
    public static void resetDensity(Context context,
                                    final float DESIGN_WIDTH,
                                    final float DESIGN_HEIGHT,
                                    final float DESTGN_INCH,
                                    final float BIG_SCREEN_FACTOR) {

        if (context == null) {
            return;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        //确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //确定参照屏幕密度比率
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / DisplayMetrics.DENSITY_DEFAULT;
        //确定相对屏幕密度比率
        float relativeDensity = referenceDensity * rate;
        //确定最终屏幕密度比率，相对屏幕密度比率和系统屏幕密度比率作对比取值
        float systemDensity = Resources.getSystem().getDisplayMetrics().density;
        if (relativeDensity > systemDensity) {
            relativeDensity = systemDensity + (relativeDensity - systemDensity) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
        dm.scaledDensity = relativeDensity;
        dm.densityDpi = (int) (relativeDensity * DisplayMetrics.DENSITY_DEFAULT);
    }
}