package me.sulg.webframework.aop;

import me.sulg.webframework.annotation.Aspect;
import me.sulg.webframework.annotation.Aspects;
import me.sulg.webframework.context.BeanContext;
import me.sulg.webframework.context.ClassContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 加载并生成代理对象
 */
public final  class AopLoader {

    public static void createProxyBean(){

        //1.获取所有带有Aspects注解的类集合
        Set<Class<?>> aspectClassSet = ClassContext.getClassSetByAnnotation(Aspects.class);
        //2.获取所有父类为AspectProxy的类集合
        Set<Class<?>> proxyClassSet = ClassContext.getClassSetBySuper(AspectProxy.class);
        //3.为每个目标类生成代理类
        for(Class<?> cls : aspectClassSet){
            //生成代理链表
            List<ProxyInterface> proxyList = new ArrayList<>();
            Aspects aspects = cls.getAnnotation(Aspects.class);//获取重复注解中每个值
            for(Aspect aspect: aspects.value()){
                try {
                    ProxyInterface temp = (AspectProxy)aspect.value().newInstance();
                    proxyList.add(temp);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
            Object proxyObj = ProxyGenerator.createProxy(cls, proxyList);
            BeanContext.setBean(cls,proxyObj);
        }
    }
}
