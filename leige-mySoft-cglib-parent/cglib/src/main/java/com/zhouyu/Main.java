package com.zhouyu;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * D:\github\leige-mySoft\leige-mySoft-cglib-parent\cglib\target\classes
 * 设置参数：
 * -Dcglib.debugLocation=D:\图灵课堂\【动态代理源码分析-周瑜】\cglib\cglib\target\classes
 * -Dcglib.debugLocation=D:\github\leige-mySoft\leige-mySoft-cglib-parent\cglib\target\classes
 */
public class Main {

    public static void main(String[] args) {
        final UserService target = new UserService();

       Enhancer enhancer = new Enhancer();
       //是否缓存生成的代理类的class对象（UserService$$EnhancerByCGLIB$$4d890297.class），并缓存在一个map中。这样在下次再次create的时候直接拿到这个class对象创建对应对象就可以了
        //
//        enhancer.setUseCache(false);
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

//                    methodProxy.invoke(target, objects);  // test()  Method
                    // 下面这行代码执⾏UserService代理对象的CGLIB$test$0()⽅法，也就是执⾏UserService代理对象的⽗类的test()
                    methodProxy.invokeSuper(o, objects);  // CGLIB$test$4() Method
                    method.invoke(target, objects);
                    methodProxy.invoke(target, objects);
//                    method.invoke(o, objects);
                    System.out.println("after...");
                    return null;
                }

//                return method.invoke(target, objects);
                return "hello";
            }
        });

        UserService userService = (UserService) enhancer.create();
        userService.test();


        // FastClass
        // UserService FastClass代理类
        // UserService代理类 FastClass代理类

    }
}
