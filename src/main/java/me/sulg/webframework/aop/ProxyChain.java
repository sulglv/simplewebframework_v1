package me.sulg.webframework.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class ProxyChain {

    //代理类各属性

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<ProxyInterface> proxyInterfaceList = new ArrayList<>();

    private int proxyIndex = 0;//执行链计数器


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<ProxyInterface> proxyInterfaceList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyInterfaceList = proxyInterfaceList;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if(proxyIndex < proxyInterfaceList.size()){//代理链未执行完
            methodResult = proxyInterfaceList.get(proxyIndex++).doProxy(this);
        }else{//代理类执行完，执行目标对象逻辑
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }


}
