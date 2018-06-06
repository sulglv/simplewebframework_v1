package me.sulg.webframework.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean容器
 */
public class BeanContext {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    //静态初始化块
    static{
        //实例化所有类
        Set<Class<?>> classSet = ClassContext.getAllClassSet();
        for(Class<?> cls: classSet){
            Object obj = null;
            try{
                obj = cls.newInstance();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            BEAN_MAP.put(cls,obj);
        }
    }

    /**
     * 获取beanMap
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 根据类类型获取bean
     */
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class" + cls.getName());
        }
        return (T)BEAN_MAP.get(cls);
    }

    /**
     * 添加Bean实例
     */
    public static void setBean(Class<?> cls, Object obj){
        BEAN_MAP.put(cls,obj);
    }

}
