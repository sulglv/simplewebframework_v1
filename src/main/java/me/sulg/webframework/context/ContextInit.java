package me.sulg.webframework.context;

import me.sulg.webframework.aop.AopLoader;
import me.sulg.webframework.define.HandlerDefine;
import me.sulg.webframework.define.RequestDefine;
import me.sulg.webframework.util.IocUtil;

import java.util.Map;

/**
 * Context初始化
 */
public final class ContextInit {

    static{
        //获取beanMap
        Map<Class<?>, Object> beanMap = BeanContext.getBeanMap();
        //生成代理对象
        AopLoader.createProxyBean();
        //注入
        IocUtil.doIoc(beanMap);
    }

    public static Map<RequestDefine, HandlerDefine> getRequestMap(){
        return RequestMapContext.getRequestMap();
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return BeanContext.getBeanMap();
    }

}
