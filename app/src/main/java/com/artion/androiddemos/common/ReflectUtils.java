package com.artion.androiddemos.common;

import java.lang.reflect.Method;

/**
 * Created by caijinsheng on 17/11/1.
 */

public class ReflectUtils {

    public static Object createObject(String clazzName) throws Exception {
        Class<?> clazz = Class.forName(clazzName);
        //使用clazz默认构造器创建对象
        return clazz.newInstance();
    }

    public static Method getMethod(Class cls, String methodName) {
        if(cls == null || methodName == null) {
            return null;
        }

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if(method != null && methodName.equals(method.getName())) {
                return method;
            }
        }

        return null;
    }
}
