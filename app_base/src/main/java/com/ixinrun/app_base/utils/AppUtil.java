package com.ixinrun.app_base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * app基础工具，中间件角色
 */
public class AppUtil {
    private static Application sApp;

    private AppUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 初始化
     *
     * @param app
     */
    public static void init(final Application app) {
        if (app == null) {
            return;
        }
        if (sApp != null) {
            LifecycleCallbacks.INSTANCE.unRegister(sApp);
        }
        sApp = app;
        LifecycleCallbacks.INSTANCE.register(sApp);
    }

    /**
     * 获取主进程
     *
     * @return
     */
    public static Application getApp() {
        if (sApp == null) {
            throw new NullPointerException("reflect failed.");
        }
        return sApp;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getApp().getPackageName(), 0);
            return pi.applicationInfo.loadLabel(pm).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用程序版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getApp().getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取应用程序版本code
     */
    public static int getVersionCode() {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getApp().getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 退出应用程序
     */
    public static void exit() {
        LifecycleCallbacks.INSTANCE.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 注册前后台切换监听
     *
     * @param callback
     */
    public static void addForegroundCallback(LifecycleCallbacks.OnForegroundCallback callback) {
        LifecycleCallbacks.INSTANCE.addForegroundCallback(callback);
    }

    public static final class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        private Handler mHandler = new Handler();
        private int mActivityVisibleCount = 0;
        private List<Activity> mActivitys = new ArrayList<>();
        private List<OnForegroundCallback> mOnForegroundCallbacks = new ArrayList<>();

        static final LifecycleCallbacks INSTANCE = new LifecycleCallbacks();

        /**
         * 注册生命周期回调
         *
         * @param app
         */
        void register(Application app) {
            app.registerActivityLifecycleCallbacks(INSTANCE);
        }

        /**
         * 解注册生命周期回调
         *
         * @param app
         */
        void unRegister(Application app) {
            app.unregisterActivityLifecycleCallbacks(INSTANCE);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mActivitys.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
            mActivityVisibleCount++;
            onForegroundCallback();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            mActivityVisibleCount--;
            onForegroundCallback();
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivitys.remove(activity);
        }

        private void onForegroundCallback() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (OnForegroundCallback c : mOnForegroundCallbacks) {
                        if (c != null) {
                            c.callback(mActivityVisibleCount > 0);
                        }
                    }
                }
            }, 200);
        }

        /**
         * 注册前后台切换监听
         *
         * @param callback
         */
        void addForegroundCallback(OnForegroundCallback callback) {
            mOnForegroundCallbacks.add(callback);
        }

        /**
         * 前后台切换回调监听器
         */
        public interface OnForegroundCallback {
            /**
             * 前后台切换回调
             *
             * @param isForeground
             */
            void callback(boolean isForeground);
        }

        /**
         * 清除所有Activity
         */
        void finishAll() {
            for (Activity activity : mActivitys) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            mActivityVisibleCount = 0;
            mActivitys.clear();
            mOnForegroundCallbacks.clear();
        }
    }
}
