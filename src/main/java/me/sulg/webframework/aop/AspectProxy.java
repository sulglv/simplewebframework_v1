package me.sulg.webframework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类基类
 */
public class AspectProxy implements ProxyInterface {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method  = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try{
            before(cls,method,params);
            result = proxyChain.doProxyChain();
            after(cls,method,params);
        }catch (Exception e){
            error(cls,method,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    @Override
    public Object doProxy_2(Class<?> cls, Method method, Object[] params) throws Throwable{
        Object result = null;

        begin();
        try{
            before(cls,method,params);
            result = method.invoke(cls,method,params);
            after(cls,method,params);
        }catch (Exception e){
            error(cls, method, e);
        }finally{
            end();
        }
        return result;
    }


    public void begin(){
    }

    public void end(){
    }

    public void before(Class<?> cls, Method method, Object[] params){
    }

    public void after(Class<?> cls, Method method, Object[] params){
    }

    public void error(Class<?> cls, Method method, Exception e){
    }

}
