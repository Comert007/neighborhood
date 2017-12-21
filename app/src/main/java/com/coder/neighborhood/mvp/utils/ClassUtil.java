package com.coder.neighborhood.mvp.utils;

import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author feng
 * @Date 2017/12/21.
 */
public class ClassUtil {
    private ClassUtil() {
        throw new RuntimeException();
    }

    /**
     * 获取class类泛型参数的class 类型
     *
     * @param clz    要获取的 class 类
     * @param _index 参数的下标
     * @return
     */
    @Nullable
    public static final Class getParameterizedClass(Class<?> clz, int _index) {
        Type superClass = clz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) superClass;
            Type[] types = type.getActualTypeArguments();
            if (_index >= types.length) {
                throw new ArrayIndexOutOfBoundsException(_index);
            }
            return (Class) types[_index];
        } else {
            return null;
        }
    }
}
