package com.ixinrun.app_base.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 描述: SharedPreferences本地存储工具类
 * </p>
 *
 * @author : luoxinrun
 * @date : 2018/11/16
 */
public final class SPUtil {
    //默认值
    private static final String DEFAULT_VALUE_STRING = "";
    private static final int DEFAULT_VALUE_INTEGER = -1;
    private static final long DEFAULT_VALUE_LONG = -1L;
    private static final float DEFAULT_VALUE_FLOAT = -1f;
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;

    private static final Gson mGson = new Gson();

    private SPUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    private static Application getApp() {
        return AppUtil.getApp();
    }

    private static SharedPreferences getSP(String fileName) {
        return getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(String fileName) {
        SharedPreferences sp = getSP(fileName);
        return sp.edit();
    }

    /**
     * apply方式保存数据
     *
     * @param key
     * @param object
     */
    public static void applyPut(@NonNull String fileName, @NonNull String key, @NonNull Object object) {
        put(fileName, key, object).apply();
    }

    /**
     * commit方式保存数据
     *
     * @param fileName
     * @param key
     * @param object
     * @return
     */
    public static boolean commitPut(@NonNull String fileName, @NonNull String key, @NonNull Object object) {
        return put(fileName, key, object).commit();
    }

    private static SharedPreferences.Editor put(@NonNull String fileName, @NonNull String key, @NonNull Object object) {
        SharedPreferences.Editor editor = getEditor(fileName);
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else {
            editor.putString(key, Base64.encodeToString(mGson.toJson(object).getBytes(), Base64.DEFAULT));
        }
        return editor;
    }

    /**
     * 获取 String 类型数据
     * <p>
     * default value: {@link #DEFAULT_VALUE_STRING}
     */
    public static String getString(@NonNull String fileName, @NonNull String key) {
        return getString(fileName, key, DEFAULT_VALUE_STRING);
    }

    /**
     * 获取 String 类型数据
     */
    public static String getString(@NonNull String fileName, @NonNull String key, @Nullable String defaultValue) {
        return getSP(fileName).getString(key, defaultValue);
    }

    /**
     * 获取 Integer 类型数据
     * <p>
     * default value: {@link #DEFAULT_VALUE_INTEGER}
     */
    public static int getInt(@NonNull String fileName, @NonNull String key) {
        return getInt(fileName, key, DEFAULT_VALUE_INTEGER);
    }

    /**
     * 获取 Integer 类型数据
     */
    public static int getInt(@NonNull String fileName, @NonNull String key, int defaultValue) {
        return getSP(fileName).getInt(key, defaultValue);
    }

    /**
     * 获取 Long 类型数据
     * <p>
     * default value: {@link #DEFAULT_VALUE_LONG}
     */
    public static long getLong(@NonNull String fileName, @NonNull String key) {
        return getLong(fileName, key, DEFAULT_VALUE_LONG);
    }

    /**
     * 获取 Long 类型数据
     */
    public static long getLong(@NonNull String fileName, @NonNull String key, long defaultValue) {
        return getSP(fileName).getLong(key, defaultValue);
    }

    /**
     * 获取 Float 类型数据(default value: -1)
     * <p>
     * default value: {@link #DEFAULT_VALUE_FLOAT}
     */
    public static float getFloat(@NonNull String fileName, @NonNull String key) {
        return getFloat(fileName, key, DEFAULT_VALUE_FLOAT);
    }

    /**
     * 获取 Float 类型数据
     */
    public static float getFloat(@NonNull String fileName, @NonNull String key, float defaultValue) {
        return getSP(fileName).getFloat(key, defaultValue);
    }

    /**
     * 获取 Boolean 类型数据
     * <p>
     * default value: {@link #DEFAULT_VALUE_BOOLEAN}
     */
    public static boolean getBoolean(@NonNull String fileName, @NonNull String key) {
        return getBoolean(fileName, key, DEFAULT_VALUE_BOOLEAN);
    }

    /**
     * 获取 Boolean 类型数据
     */
    public static boolean getBoolean(@NonNull String fileName, @NonNull String key, boolean defaultValue) {
        return getSP(fileName).getBoolean(key, defaultValue);
    }

    /**
     * 获取实体类数据
     *
     * @param key
     * @param
     * @return
     */
    public static <T> T get(@NonNull String fileName, @NonNull String key, @NonNull Class<T> cls) {
        String value = getString(fileName, key);
        return mGson.fromJson(new String(Base64.decode(value, Base64.DEFAULT)), cls);
    }

    /**
     * 移除某个SP文件的键值对
     *
     * @param key
     */
    public static boolean remove(@NonNull String fileName, @NonNull String key) {
        SharedPreferences.Editor editor = getEditor(fileName);
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除某个SP文件的所有数据
     */
    public static boolean clear(@NonNull String fileName) {
        SharedPreferences.Editor editor = getEditor(fileName);
        editor.clear();
        return editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(@NonNull String fileName, @NonNull String key) {
        SharedPreferences sp = getSP(fileName);
        return sp.contains(key);
    }

    /**
     * 获取某个SP文件中的所有键值对
     *
     * @return
     */
    public static Map<String, ?> getAll(@NonNull String fileName) {
        SharedPreferences sp = getSP(fileName);
        return sp.getAll();
    }
}
