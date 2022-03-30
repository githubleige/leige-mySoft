package cglib;

import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;

public class test2 {
    public static void main(String[] args) throws Exception{
        Object bean=new Object();
        Class targetClass = AopProxyUtils.ultimateTargetClass(bean);
        String aa="gee";
        Method m=aa.getClass().getMethod("equals",Object.class);
        Method m1=String.class.getMethod("equals",Object.class);
        System.out.println(m.toString());
        System.out.println(m1.toString());
    }
}
