package threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RepeatFutureTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //注意这个对象integerFutureTask不可能被两个线程同时复用，只可能有一个线程使用。原因分析：
        //在线程1已经执行完integerFutureTask的call会把FutureTask中的状态字段改成NORMAL。当第二个线程开始执行的时候会对自己要执行的FutureTask对象
        //进行状态检查：
        //if (state != NEW ||!UNSAFE.compareAndSwapObject(this, runnerOffset,null, Thread.currentThread())) return;
        // 因为线程1已经把integerFutureTask对象的状态字段改为NORMAL，所以上面的第一个条件state != NEW就不满足，直接返回
        FutureTask<Integer> integerFutureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "已经执行了");
            Thread.sleep(1000);
            return 1;
        });
        new Thread(integerFutureTask,"线程1").start();

        System.out.println("主线程结束");
        Thread.sleep(300);
        //这样做会抛出Exception in thread "main" java.util.concurrent.CancellationException
        //	at java.util.concurrent.FutureTask.report(FutureTask.java:121)
        //	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
        //	at threadpool.RepeatFutureTask.main(RepeatFutureTask.java:22)
        //原因是在线程1还在执行integerFutureTask的里面的callable的call方法的时候。我们这边给这个FutureTask的状态变了，
        // 由原来的NEW->CANCELLED.
        //改变状态之后，唤醒了因为调用这个FutureTask的get()方法而阻塞的线程。
        //线程在返回后会判断FutureTask的状态:
        //if (s >= CANCELLED)
        //   throw new CancellationException();
        //因为状态被改成了CANCELLED，所以上面的if判断成功，我们调用get()的线程就会抛出异常
        integerFutureTask.cancel(false);
        System.out.println("线程1的返回值是："+integerFutureTask.get());
//        Thread.sleep(1000);
//        new Thread(integerFutureTask,"线程2").start();
    }
}
