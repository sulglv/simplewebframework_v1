package me.sulg.webframework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 */
public final class ReflectionUtil {

    /**
     * 执行反射方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result = null;
        try {
            result = method.invoke(obj,args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
