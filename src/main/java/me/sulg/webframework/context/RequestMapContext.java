package me.sulg.webframework.context;

import me.sulg.webframework.annotation.ActionMapper;
import me.sulg.webframework.annotation.Controller;
import me.sulg.webframework.define.HandlerDefine;
import me.sulg.webframework.define.RequestDefine;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求映射处理方法容器
 */
public final class RequestMapContext {

    private final static Map<RequestDefine, HandlerDefine> REQUEST_MAP = new HashMap<>();

    //静态初始化
    static {
        //获取Controller类集合
        Set<Class<?>> controllerSet = ClassContext.getClassSetByAnnotation(Controller.class);
        for (Class<?> cls : controllerSet) {
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                //方法带有actionMapper注解
                if (method.isAnnotationPresent(ActionMapper.class)) {
                    ActionMapper actionMapper = method.getAnnotation(ActionMapper.class);
                    //请求路径
                    String path = actionMapper.path();
                    //请求方法
                    String requestMethod = actionMapper.method();
                    RequestDefine requestDefine = new RequestDefine(requestMethod, path);
                    HandlerDefine handlerDefine = new HandlerDefine(cls, method);
                    REQUEST_MAP.put(requestDefine, handlerDefine);
                }
            }
        }


    }

    /**
     * 获取requestMap
     */
    public static Map<RequestDefine, HandlerDefine> getRequestMap() {
        return REQUEST_MAP;
    }

    public static HandlerDefine getHandler(RequestDefine requestDefine) {
        return REQUEST_MAP.get(requestDefine);
    }


}
