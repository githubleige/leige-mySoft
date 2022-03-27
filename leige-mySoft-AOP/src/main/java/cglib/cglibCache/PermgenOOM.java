package cglib.cglibCache;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 首先将本机的jvm配置为 -XX:PermSize=64M -XX:MaxPermSize=64M ，给到PermSize最大为64M的内存
 */
public class PermgenOOM {
    public   static   void  main(String[] args)  throws  InterruptedException {

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        int  i = 0 ;
        while ( true ){
            Enhancer enhancer  =   new Enhancer();
            enhancer.setSuperclass(Product. class );
            enhancer.setUseCache( false ); //  关闭CGLib缓存，否则总是生成同一个类

//            enhancer.setUseCache( true );//  或者不写，默认值就是true

            enhancer.setCallback( new  MethodInterceptor() {
                @Override
                public  Object intercept(Object obj, Method method, Object[] args,
                                         MethodProxy methodproxy)  throws  Throwable {
                    //  TODO Auto-generated method stub
                    return  methodproxy.invokeSuper(obj,args);
                }
            });
            Product product=(Product)enhancer.create();
            System.out.println(product.getClass());
            Thread.sleep( 100 );
        }
    }
}
