package JDKAop;

import sun.reflect.Reflection;
import sun.reflect.CallerSensitive;

public class CalleeApp {

    @CallerSensitive
    public static void print(){
        Class<?> clazz = Reflection.getCallerClass();
        System.out.println("Hello " + clazz);
    }

    @CallerSensitive
    public void call() {
        //获取调用者所在方法的所在类
        //找到调用public void call()方法的地方，然后找到这个调用的地方所在的类（这个类的class对象就是返回这个类的calss对象）
        Class<?> clazz = Reflection.getCallerClass();
        System.out.println("Hello " + clazz);
    }
}

