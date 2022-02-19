package it.heima.nio.asyncfilechannel;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Unsafe这个类中的方法具有原子性,通过总线锁的形式实现
 */
public class test1 {
    public static void main(String[] args) {
        final Thread thread = new Thread();
        //FutureTask<Integer>实现了(Runnable, Future<Integer>)两个接口
        //if (state != NEW || !UNSAFE.compareAndSwapObject(this, runnerOffset,null, Thread.currentThread()))
        //注意这个方法的作用UNSAFE.compareAndSwapObject(this, runnerOffset,null, Thread.currentThread())是：
        //FutureTask里面有一个实例成员变量：private volatile Thread runner;
        //runnerOffset表示的就是runner这个实例成员变量相对于这个实例在堆中首地址的偏移量
        //this表示的就是这个实例
        //这个UNSAFE.compareAndSwapObject方法是进行compare and swap的典型方法
        // (所依靠的便是Unsafe这个类中调用的该方法具有原子性，这个原子性的保证并不是靠java本身保证，而是靠一个更底层的与操作系统相关的特性实现。)
        //先拿到实例的runnerOffset属性的值，然后看是不是null，是null把这个runner设置为Thread.currentThread()，并返回true
        //如果不是的话，不进行设置，返回false

        FutureTask<Integer> futureTask=new FutureTask<>(()->{
            int i=0;
            for(;i<100;i++){
                System.out.println(Thread.currentThread().getName()+" 循环变量i的值是："+i);
            }
            return i;
        });
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" 循环变量i的值是："+i);
            if(i==20){
                new Thread(futureTask,"有返回值的线程").start();
            }
        }
        try {
            System.out.println("子线程的返回值是："+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @CallerSensitive
    public static void print1(){
        Class<?> caller = Reflection.getCallerClass();
        System.out.println(caller);
        Class<?> caller2 = Reflection.getCallerClass(4);
        System.out.println(caller2);
        System.out.println("hello world");
    }
}
