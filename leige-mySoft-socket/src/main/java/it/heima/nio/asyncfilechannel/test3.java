package it.heima.nio.asyncfilechannel;

import sun.reflect.CallerSensitive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test3 {
    public static void main(String[] args) {
        System.out.println(test2.valueOffset);
    }
    @CallerSensitive
    public static void print3() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<test2> clazz=test2.class;
        Method method=clazz.getMethod("print2");
        method.invoke(null);
    }


}
