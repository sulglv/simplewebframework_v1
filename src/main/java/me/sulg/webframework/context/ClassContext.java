package me.sulg.webframework.context;

import me.sulg.webframework.aop.AspectProxy;
import me.sulg.webframework.constant.ConfigConstant;
import me.sulg.webframework.util.ConfigReader;
import sun.reflect.generics.scope.ClassScope;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * 类容器类 扫描指定包下的所有类
 */
public class ClassContext {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigReader.getProperty(ConfigConstant.BASE_PACKAGE);
        CLASS_SET = loadClassSet(basePackage);
    }

    /**
     * 获取所有类的集合
     */
    public static Set<Class<?>> getAllClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取包含指定注解的类的集合
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> resultSet = new HashSet<>();
        for (Class<?> temp : CLASS_SET) {
            if (temp.isAnnotationPresent(annotationClass)) {
                resultSet.add(temp);
            }
        }
        return resultSet;
    }


    /**
     * 扫描指定包下的所有类
     */
    private static Set<Class<?>> loadClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        String packageDir = ClassContext.class.getResource("/").getPath() + packageName.replace(".", "/");
        Path path = new File(packageDir).toPath();

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".class")) {

                        Path base = new File(ClassContext.class.getResource("/").getPath()).toPath();
                        StringBuilder classNameBuilder = new StringBuilder();
                        for (int i = base.getNameCount(); i < file.getNameCount(); i++) {
                            classNameBuilder.append(file.getName(i)).append(".");
                        }
                        String className = classNameBuilder.substring(0, classNameBuilder.length() - ".class.".length());
                        Class<?> cls;
                        try {
                            cls = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        classSet.add(cls);
                    } else if (file.toString().endsWith(".jar")) {
                        //todo 加载jar包中的class
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classSet;
    }

    public static Set<Class<?>> getClassSetBySuper(Class<AspectProxy> superClass) {
        Set<Class<?>> resultSet = new HashSet<>();
        for(Class<?> cls: CLASS_SET){
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                resultSet.add(cls);
            }
        }
        return resultSet;
    }
}
