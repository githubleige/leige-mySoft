package cglib.study3;

import cglib.study2.CglibProxy;
import cglib.study2.CglibProxy2;
import cglib.study2.filter;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.Enhancer;


public class CallbackTypesTest {

    static class CallbackTypesInner {
        private int a;
        private int b;

        public CallbackTypesInner(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        void say()
        {
            System.out.println("say");
        }

        void hello(){
            System.out.println("hello");
        }
    }

    public static Class<?> getProxyClass(Class<?> clazz) {
        Enhancer e = new Enhancer();
        if (!clazz.isInterface()) {
            e.setSuperclass(clazz);
        } else {
            e.setSuperclass(clazz);
            e.setInterfaces(clazz.getInterfaces());
        }

        e.setCallbackTypes(new Class[]{
                CglibProxy.class,
                CglibProxy2.class,
        });
        e.setCallbackFilter(new filter());
        e.setUseFactory(true);
        e.setUseCache(true);
        return e.createClass();
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".//");
        Class<?> impl = getProxyClass(CallbackTypesInner.class);
        System.out.println(impl);
        CallbackTypesInner aa=(CallbackTypesInner) ReflectUtils.newInstance(impl, new Class[]{ Integer.class,Integer.class }, new Object[]{ 2,3 });
        aa.say();
    }

}
