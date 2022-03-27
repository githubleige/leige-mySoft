package com.zhouyu;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {

    /**
     * o：cglib生成的代理对象(子类的对象)  class cglib.study1.HelloService$$EnhancerByCGLIB$$775a7bc7
     * method：是父类中被调用的方法（被代理对象的方法）  public void cglib.study1.HelloService.sayHello()
     * objects：方法入参
     * methodProxy: 代理方法(这个很复杂，需要看底层代码)
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("======插入前置通知======");
//        System.out.println(method.getName());
//        System.out.println(method.getDeclaringClass());
//        System.out.println(method.toString());
//        System.out.println(o.getClass());
//        System.out.println(methodProxy.getClass());
//        Object object = methodProxy.invokeSuper(o, objects);
        Object object=null;
        System.out.println("======插入后者通知======");
        return "hello world";
    }
}
