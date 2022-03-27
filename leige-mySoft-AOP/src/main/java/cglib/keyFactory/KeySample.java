package cglib.keyFactory;


import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.proxy.CallbackFilter;
import org.objectweb.asm.Type;

public class KeySample {
    private interface MyFactory {
        public Object newInstance(int a, int b);
    }


    public interface EnhancerKey {
        public Object newInstance(String type,
                                  String[] interfaces,
                                  CallbackFilter filter,
                                  Type[] callbackTypes,
                                  boolean useFactory,
                                  boolean interceptDuringConstruction,
                                  Long serialVersionUID);
    }
    public static void main(String[] args) {
        //这一句是把class文件输出在指定目录
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".//");
        MyFactory f = (MyFactory) KeyFactory.create(MyFactory.class);
        MyFactory f1 = (MyFactory)KeyFactory.create(EnhancerKey.class);
        System.out.println(f.getClass());
        System.out.println(f == f1);//false
        System.out.println(f.equals(f1));//true
        Object key1 = f.newInstance(20, 30);
        System.out.println(key1.getClass());
        Object key2 = f.newInstance(20, 30);
        Object key3 = f.newInstance(20, 40);

        System.out.println(key1.equals(key2));//true
        System.out.println(key2.equals(key3));//false
    }
}

