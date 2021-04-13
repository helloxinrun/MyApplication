package com.ixinrun.base;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;

import androidx.multidex.MultiDexApplication;

import com.ixinrun.base.utils.AppUtil;

/**
 * 描述：BaseApplication
 * 所有Application继承于BaseApplication
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/8/27
 */
public class BaseApplication extends MultiDexApplication {
    private static Application sApp;
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sApp = this;
        AppUtil.init(this);
    }

    public static Application getInstance() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        acquireWakeLock();
    }

    /**
     * 保持持久唤醒状态
     */
    private void acquireWakeLock() {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
            mWakeLock.acquire();
        }
    }

    /**
     * 释放持久唤醒锁(退出应用释放锁)
     */
    public void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        releaseWakeLock();
    }

}
