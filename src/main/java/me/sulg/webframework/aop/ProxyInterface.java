package me.sulg.webframework.aop;

import java.lang.reflect.Method;

/**
 *
 */
public interface ProxyInterface {
    Object doProxy(ProxyChain proxyChain) throws Throwable;

    Object doProxy_2(Class<?> cls, Method method, Object[] params) throws Throwable;

}
