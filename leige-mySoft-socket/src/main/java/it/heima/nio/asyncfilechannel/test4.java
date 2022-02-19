package it.heima.nio.asyncfilechannel;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * 需要加上虚拟机参数：
 * -Xbootclasspath/a:D:\java\git\gitHubleige\leige-mySoft\leige-mySoft-socket\target\classes
 */
public class test4 {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(test4.class.getClassLoader());
        test3.print3();
//        print4();
    }

    @CallerSensitive
    public static void print4(){
        Class<?> caller = Reflection.getCallerClass();
        System.out.println(caller);
        System.out.println("hello world");
    }
}
