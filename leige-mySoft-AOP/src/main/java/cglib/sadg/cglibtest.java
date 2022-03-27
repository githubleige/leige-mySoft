package cglib.sadg;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class cglibtest {
    public void test() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cglibtest.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//                System.out.println("before method run..");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run..");
                return result;
            }
        });
        cglibtest sample = (cglibtest) enhancer.create();
//        sample.test();
//        System.out.println(sample.getClass());
////        cglibtest aa=new cglibtest();
//        System.out.println(aa.getClass());
        String str=sample.toString();
        System.out.println(str);
    }

    @Override
    public String toString(){
        return "test";
    }
}
