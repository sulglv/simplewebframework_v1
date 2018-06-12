package me.sulg.webframework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Aspects.class)
public @interface Aspect {
    Class<?> value();
}
