package me.sulg.webframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求与方法映射注解,这里限定注解在方法上
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMapper {
    //请求路径
    String path();
    //请求方法 get/post等
    String method();
}
