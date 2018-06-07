package me.sulg.webframework.define;

import java.lang.reflect.Method;

/**
 * 请求处理方法类
 */
public class HandlerDefine {

    private Class<?> cls;

    private Method method;

    public HandlerDefine(Class<?> cls, Method method) {
        this.cls = cls;
        this.method = method;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
