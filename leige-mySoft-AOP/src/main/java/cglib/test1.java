package cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import cglib.sadg.SampleClass;

import java.lang.reflect.Method;

public class test1 {
    public static void main(String[] args) {
        Sample cc=new Sample("fjb");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Sample.class);
//        enhancer.setCallback(new FixedValue(){
//            @Override
//            public Object loadObject() throws Exception {
//                return "en";
//            }
//        });
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        Sample aa=(Sample)enhancer.create(new Class[]{String.class},new String[]{"gelei"});
        Sample bb=(Sample)enhancer.create(new Class[]{String.class},new String[]{"gelei"});
        System.out.println(aa);
        System.out.println(bb);

        System.out.println(aa.getClass());
        System.out.println(cc.getClass());
    }

    public void testInvocationHandler() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "hello cglib";
                } else {
                    throw new RuntimeException("Do not know what to do");
                }
            }
        });
        SampleClass proxy = (SampleClass) enhancer.create();
//        Assert.assertEquals("hello cglib", );
//        Assert.assertNotEquals("Hello cglib", proxy.toString());

    }

}

class Sample {
    private String name;

    public Sample(String name) {
        this.name = name;
    }

    public String test(String input) {
        return name;
    }
}

interface interface1{

}
interface interface2{

}
interface interface3 extends interface2,interface1{

}

class class1{

}
class class2{

}
class class3 extends class1{

}
