package me.sulg.webframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 代理对象生成类
 */
public class ProxyGenerator {

    /**
     * 递归方式生成切面类
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<ProxyInterface> proxyInterfaceList){

        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                return new ProxyChain(targetClass, o, method, methodProxy ,objects, proxyInterfaceList).doProxyChain();
            }
        });
    }



    /**
     * 迭代方式生成切面类
     *  //todo 异常切面如何切入?
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy_3(final Class<?> targetClass, final List<AspectProxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                Object result = null;
                try{
                    //执行前置方法
                    for(AspectProxy proxy: proxyList){
                        proxy.before(o.getClass(),method,objects);
                    }
                    //执行目标方法
                    result = methodProxy.invokeSuper(o,objects);
                    //执行后置方法(首先翻转代理链)
                    Collections.reverse(proxyList);
                    for(AspectProxy proxy: proxyList){
                        proxy.after(o.getClass(),method,objects);
                    }
                }catch (Exception e){

                }finally {

                }

                return result;
            }
        });
    }
}
