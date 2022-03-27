package cglib.study2;



import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * 静态方法不会被代理
 */
public class CglibLearn {

//    private static final Callback NOOP = new NoOpImpl();

    //定义委托类，可以不是接口
    static class serviceImpl {
//        当代理类没有无参构造器用下面的方法就会报错，可以用calltype,即study3
//        public serviceImpl(String str){
//            System.out.println(str);
//        }

        void say()
        {
            System.out.println("say");
        }

        void hello()
        {
            System.out.println("hello");
        }

    }

    static interface service{
        public void interfaceTest();
    }

    public static Object getProxyInstance(Object realSubject) {
        Enhancer enhancer = new Enhancer();
        //需要创建子类的类,即定义委托类
        enhancer.setSuperclass(realSubject.getClass());
//        enhancer.setInterfaces();
        //设置两个CallBack以及CallbackFilter
        Callback[] callbacks=new Callback[2];
        callbacks[0]=new CglibProxy();
        callbacks[1]=new CglibProxy2();
////        callbacks[1]=NOOP;
//        callbacks[1]= NoOp.INSTANCE;
        enhancer.setCallbacks(callbacks);
        enhancer.setInterfaces(new Class[]{service.class});
        enhancer.setCallbackFilter(new filter());
        //通过字节码技术动态创建子类实例
//        return enhancer.create(new Class[] {String.class}, new Object[] {"hello world"});
        return enhancer.create();
    }

    public static void main(String[] args) {
        //将sam,class文件写到硬盘
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".//");
        //通过生成子类的方式创建代理类

        serviceImpl impl = (serviceImpl)getProxyInstance(new serviceImpl());
//        impl.say();
//        impl.hashCode();
//        impl.hello();
        impl.hello();
    }
}

