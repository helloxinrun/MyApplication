package com.ixinrun.app_base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;


/**
 * 描述：获取设备相关信息的工具类
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public class DeviceUtil {
    private static final String TAG = "DeviceUtil";
    private static final String CONSTANT_MAC = "02:00:00:00:00:00";

    private DeviceUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    private static Application getApp() {
        return AppUtil.getApp();
    }

    /**
     * 手机系统版本
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 手机系统API level
     */
    public static int getOSAPILevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * DeviceID，IMEI。
     * 1.手机制式为GSM时，返回手机的IMEI；手机制式为CDMA时，返回手机的MEID或ESN。
     * 2.需要申请权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * 3.非电话设备或者DeviceID不可用时，可能会返回null，需注意。
     *
     * @return
     */
    public static String getDeviceID() {
        final boolean phone = ContextCompat.checkSelfPermission(getApp(), Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED;
        if (!phone) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * ICCID，SIM卡识别码（20位数字组成）
     *
     * @return
     */
    public static String getICCID() {
        final boolean phone = ContextCompat.checkSelfPermission(getApp(), Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED;
        if (!phone) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * IMSI，SIM卡移动用户识别码，可用于判断网络运营商（移动，联通，电信）
     */
    public static String getIMSI() {
        final boolean phone = ContextCompat.checkSelfPermission(getApp(), Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED;
        if (!phone) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 获取手机号(只能够读取写入到SIM卡的手机号，因此有拿不到的可能)
     */
    public static String getPhoneNo() {
        final boolean phone = ContextCompat.checkSelfPermission(getApp(), Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED;
        if (!phone) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 获取SIM卡运营商
     *
     * @return
     */
    public static String getSimOperator() {
        TelephonyManager tm = (TelephonyManager) getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String operatorNum = tm.getSimOperator();
        String operator = "";
        if (!TextUtils.isEmpty(operatorNum)) {
            if ("46000".equals(operatorNum) || "46002".equals(operatorNum) || "46007".equals(operatorNum)) {
                // 中国移动
                operator = "中国移动";
            } else if ("46001".equals(operatorNum) || "46006".equals(operatorNum)) {
                // 中国联通
                operator = "中国联通";
            } else if ("46003".equals(operatorNum) || "46005".equals(operatorNum)) {
                // 中国电信
                operator = "中国电信";
            }
        }

        return operator;
    }

    /**
     * androidId，系统第一次开机启动时生成的序列号
     *
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        return android.provider.Settings.Secure.getString(
                getApp().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    /**
     * Serial Number，设备序列号
     *
     * @return
     */
    public static String getSerialNumber() {
        return android.os.Build.SERIAL;
    }

    /**
     * WifiMac地址
     * 1.没有WiFi硬件或者WiFi不可用的设备可能返回null或空，注意判空。
     * 2.Android6.0开始，谷歌为保护用户数据，用此方法获取到的Wi-Fi mac地址都为“02:00:00:00:00:00”。
     * 3.需要申请权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getWifiMac() {
        String wifiMac = "";
        try {
            WifiManager wifimanger = (WifiManager) getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiinfo = wifimanger.getConnectionInfo();
            wifiMac = wifiinfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(wifiMac) && CONSTANT_MAC.equals(wifiMac)) {
            wifiMac = "";
        }
        return wifiMac;
    }

    /**
     * 设备制造商
     *
     * @return
     */
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 设备型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设备品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 设备系统版本
     */
    public static String getDeviceVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 打印所有信息
     *
     * @return
     */
    public static String allToString() {
        return "OSVersion:" + getOSVersion() + "\n"
                + "OSAPILevel:" + getOSAPILevel() + "\n"
                + "DeviceId:" + getDeviceID() + "\n"
                + "IMSI:" + getIMSI() + "\n"
                + "ICCID:" + getICCID() + "\n"
                + "AndroidID:" + getAndroidId() + "\n"
                + "SerialNumber:" + getSerialNumber() + "\n"
                + "WifiMac:" + getWifiMac() + "\n"
                + "DeviceManufacturer:" + getDeviceManufacturer() + "\n"
                + "DeviceModel:" + getDeviceModel() + "\n"
                + "DeviceBrand:" + getDeviceBrand() + "\n"
                + "DeviceVersion:" + getDeviceVersion();
    }
}
