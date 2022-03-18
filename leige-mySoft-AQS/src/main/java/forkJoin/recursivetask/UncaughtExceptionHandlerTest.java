package forkJoin.recursivetask;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtExceptionHandlerTest {
    public static void main(String[] args) throws Exception {
        //为所有线程设置默认的未捕捉异常处理器
//        Thread.setDefaultUncaughtExceptionHandler(new MyDefaultExceptionHandler());
//        Thread.currentThread().setName("Main Thread");

        Thread thread = new Thread(new MyTask("MyTask"), "Child Thread");
        //为某个线程单独设置异常处理器
//        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
        //主线程抛出异常，将会使用默认的异常处理器
//        throw new RuntimeException("IllegalArgumentException");
    }
}

class MyDefaultExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("MyDefaultExceptionHandler: Thread: " +
                t.getName() + ", Message: " + e.getMessage());
    }
}
class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("MyUncaughtExceptionHandler: Thread: " +
                t.getName() + ", Message: " + e.getMessage());
    }
}
class MyTask implements Runnable {
    private String name;
    public MyTask(String name) {
        this.name = name;
    }
    public MyTask(){}
    public String getName() {
        return name;
    }
    @Override
    public void run() {
        throw new RuntimeException(name + " gets a NullPointerException");
    }
}