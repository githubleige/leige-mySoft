package threadpool;

import java.util.concurrent.FutureTask;

public class RepeatFutureTask {
    public static void main(String[] args) throws InterruptedException {
        //注意这个对象integerFutureTask不可能被两个线程同时复用，只可能有一个线程使用。原因分析：
        //在线程1已经执行完integerFutureTask的call会把FutureTask中的状态字段改成NORMAL。当第二个线程开始执行的时候会对自己要执行的FutureTask对象
        //进行状态检查：
        //if (state != NEW ||!UNSAFE.compareAndSwapObject(this, runnerOffset,null, Thread.currentThread())) return;
        // 因为贤臣1已经把integerFutureTask对象的状态字段改为NORMAL，所以上面的第一个条件state != NEW就不满足，直接返回
        FutureTask<Integer> integerFutureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "已经执行了");
            return 1;
        });
        new Thread(integerFutureTask,"线程1").start();
        Thread.sleep(1000);
        new Thread(integerFutureTask,"线程2").start();
    }
}
