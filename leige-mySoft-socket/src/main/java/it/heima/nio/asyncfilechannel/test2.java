package it.heima.nio.asyncfilechannel;

import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test2 {
    private int a;

    public volatile int value;


    public static final long valueOffset;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            //null是一切对象的实例
            Unsafe unsafe = (Unsafe) f.get(null);

            valueOffset=unsafe.objectFieldOffset(test2.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    public static void print(){
        System.out.println("hello world");
    }
    @CallerSensitive
    public static void print2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<test1> clazz=test1.class;
        Method method=clazz.getMethod("print1");
        method.invoke(null);
    }
}
