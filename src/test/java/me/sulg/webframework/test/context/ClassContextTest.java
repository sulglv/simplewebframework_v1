package me.sulg.webframework.test.context;

import me.sulg.webframework.context.ClassContext;

import java.util.Set;

public class ClassContextTest {
    public static void main(String[] args) {
        Set<Class<?>> classSet = ClassContext.getAllClassSet();

        for(Class<?> shit: classSet){
            System.out.println(shit);
        }


    }
}
