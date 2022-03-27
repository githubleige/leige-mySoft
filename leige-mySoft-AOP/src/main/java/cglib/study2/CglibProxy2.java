package cglib.study2;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy2 implements MethodInterceptor {

    //实现MethodInterceptor接口，定义方法的拦截器
    //method：是父类中被调用的方法（被代理对象的方法）
    @Override
    public Object intercept(Object o, Method method, Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("pre1");
        //通过代理类调用父类中的方法,即实体类方法
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("after1");
        return result;
    }
}

