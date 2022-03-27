package com.zhouyu;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 设置参数：
 * -Dcglib.debugLocation=D:\图灵课堂\【动态代理源码分析-周瑜】\cglib\cglib\target\classes
 */
public class MainFactory {

    public static void main(String[] args) {
        final UserService target = new UserService();

       Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(false);
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * o：cglib生成的代理对象(子类的对象)  class cglib.study1.HelloService$$EnhancerByCGLIB$$775a7bc7
             * method：是父类中被调用的方法（被代理对象的方法）  public void cglib.study1.HelloService.sayHello()
             * objects：方法入参
             * methodProxy: 代理方法(这个很复杂，需要看底层代码) 是这样一个public void test()方法的代理对象，
             * 具体是代理什么对象的public void test()对象，需要由传入的代理对象决定。现在只知道有这么个public void test()方法，具体实现还要看代理对象
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                if (method.getName().equals("test")) {
                    System.out.println("before...");

//                    methodProxy.invoke(o, objects);  // test()  Method
                    // 下面这行代码执⾏UserService代理对象的CGLIB$test$0()⽅法，也就是执⾏UserService代理对象的⽗类的test()
//                    methodProxy.invokeSuper(o, objects);  // CGLIB$test$4() Method
//                    method.invoke(target, objects);
                    methodProxy.invoke(target, objects);
//                    method.invoke(o, objects);
                    System.out.println("after...");
                    return null;
                }

//                return method.invoke(target, objects);
                return "hello";
            }
        });

//        Factory userService = (Factory) enhancer.create();
//
//        userService.newInstance(new MyMethodInterceptor());

        UserService userService = (UserService) enhancer.create();
        userService.test();
        System.out.println("==================");
        Factory userServiceFactory=(Factory)userService;
        //实现implements Factory,唯一作用就是可以动态创建很多个UserService$$EnhancerByCGLIB$$4d890297实例
        //这些实例的区别就是private MethodInterceptor CGLIB$CALLBACK_0;这个属性不一样。
        //也就是说这个方法拦截器不一样。
        //userService对象的拦截器就是上面设置的那个MethodInterceptor
        //userService2的拦截器是new MyMethodInterceptor()
        UserService userService2=(UserService)userServiceFactory.newInstance(new MyMethodInterceptor());
        userService2.test();
        System.out.println("======================");
        userService=(UserService)userService;
        userService.test();

        // FastClass
        // UserService FastClass代理类
        // UserService代理类 FastClass代理类

    }
}
