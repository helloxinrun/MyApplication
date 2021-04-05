package com.ixinrun.base.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：拷贝工具类
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class CopyUtil {

    private CopyUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    private static Gson sGson = new Gson();

    /**
     * 拷贝单个实体
     *
     * @param source
     * @param targetType
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> T copy(S source, Class<T> targetType) {
        try {
            return sGson.fromJson(sGson.toJson(source), targetType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拷贝列表
     *
     * @param source
     * @param clazz
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> copyList(S source, Class<T> clazz) {
        try {
            Type type = new ParameterizedTypeImpl(clazz);
            return sGson.fromJson(sGson.toJson(source), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private Class clazz;

        ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
