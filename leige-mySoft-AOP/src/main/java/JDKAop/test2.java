package JDKAop;

import sun.reflect.Reflection;

public class test2 {
    public static void main(String[] args) {
        Class<?> aa=Reflection.getCallerClass();
        System.out.println(aa);
    }
}
