package JDKAop;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

//-Xbootclasspath:bootclasspath ：让jvm从指定的路径中加载bootclass，用来替换jdk的rt.jar。一般不会用到。
//-Xbootclasspath/a: path ： 被指定的文件追加到默认的bootstrap路径中。
//-Xbootclasspath/p: path ： 让jvm优先于默认的bootstrap去加载path中指定的class。
//下面这个类运行需要指定指定虚拟机参数：
//-Xbootclasspath/a:C:\Users\20013129\IdeaProjects\test1\target\classes
public class CallerApp {
    @CallerSensitive
    public static void main(String[] args) {

//        Class<?> clazz = Reflection.getCallerClass();
//        System.out.println("Hello " + clazz);
//
//        CalleeApp.print();

        CalleeApp app = new CalleeApp();
        Caller1 c1 = new Caller1();
        c1.run(app);
//        System.out.println();
//        test();
    }

    @CallerSensitive
    public static void test(){
        Class<?> clazz = Reflection.getCallerClass();
        System.out.println("Hello " + clazz);
    }

    static class Caller1 {
        void run(CalleeApp calleeApp) {
            if (calleeApp == null) {
                throw new IllegalArgumentException("callee can not be null");
            }
            calleeApp.call();
        }
    }
}

