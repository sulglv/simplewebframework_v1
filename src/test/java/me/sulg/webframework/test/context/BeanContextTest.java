package me.sulg.webframework.test.context;

import me.sulg.webframework.context.BeanContext;
import me.sulg.webframework.test.entity.Teacher;
import me.sulg.webframework.util.IocUtil;

import java.util.Map;

public class BeanContextTest {
    public static void main(String[] args) {
        //获取beanMap
        Map<Class<?>,Object> beanMap = BeanContext.getBeanMap();

        //注入
        IocUtil.doIoc(beanMap);

        //验证是否注入
        Teacher teacher = (Teacher) beanMap.get(Teacher.class);
        if(teacher.getStudent()!=null){
            System.out.println("inject success");
        }

    }
}
