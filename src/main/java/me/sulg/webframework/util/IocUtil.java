package me.sulg.webframework.util;

import me.sulg.webframework.annotation.Inject;
import me.sulg.webframework.context.BeanContext;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * IOC工具类 注入BeanContext中的对象
 */
public final class IocUtil {

    /**
     * 注入
     */
    public static void doIoc(Map<Class<?>, Object> beanMap) {
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> cls = beanEntry.getKey();
            Object obj = beanEntry.getValue();

            Field[] fields = cls.getDeclaredFields();
            if (fields.length > 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> fieldClass = field.getType();
                        Object bean = beanMap.get(fieldClass);
                        if (bean != null) {
                            try {
                                field.setAccessible(true);
                                field.set(obj, bean);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }

    }

}
