package threadpool;


import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * scheduleWithFixedDelay是以上一个周期性任务执行结束的时间+delay来决定下一个任务的exectuteTime
 * 但是scheduleAtFixedRate方法是通过上一个任务的exectuteTime（这是ScheduledFutureTask属性的一个字段）+period来决定下一个任务的exectuteTime
 */
public class ScheduledThreadPoolExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //再创建线程池的，这个定时任务的线程池的corePoolSize是5，但是maximumPoolSize是Integer.MAX_VALUE
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        Task task = new Task("任务1");
        System.out.println("Created : " + task.getName());

        ScheduledFuture<?>  singleScheduledFuture=executor.schedule(task, 2, TimeUnit.SECONDS);
//        System.out.println(scheduledFuture.get());


        executor.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS); //上一个执行任务+延迟
        //schedule(Runnable command,long delay,TimeUnit unit)和public <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay,TimeUnit unit)两个方法是可以通过
        //返回值的scheduledFuture来调用get方法的，因为他们底层调用的是run方法而不是runAndReset()方法，也就是这个任务只会执行一次，所以不会像下面的那样周期性的执行。但是schedule(Runnable command,long delay,TimeUnit unit)方法获取到的
        //返回值始终是null，因为Runnable的run方法本身就没有返回值，jdk底层自动给他设置了一个返回值null.public <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay,TimeUnit unit)和普通的调用FutureTask没什么区别

        ScheduledFuture<?>  scheduledFuture=executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);//任延迟取最大值 稳定定时器
        //注意我们不能在调用周期性的定时方法（scheduleWithFixedDelay和scheduleAtFixedRate）时同时调用它的返回值的scheduledFuture.get()方法,因为这两个方法在启用
        //线程的时候会去调用FutureTask的runAndReset()方法，这个方法在执行完（是正常结束），并不会改FutureTask的状态，也就是在正常情况下FutureTask的状态始终是NEW，
        //但是当我调用scheduledFuture.get()方法的时候首先会去判断：if (s <= COMPLETING) {s = awaitDone(false, 0L);}注意我们的NEW=0,COMPLETING = 1的常量。所以
        //会执行{s = awaitDone(false, 0L);}这个方法作用就是让线程等待阻塞（等待唤醒机制）,一般在调用FutureTask的run方法的时候，会在成功执行完的时候调用set(result);
        //set(result);里面会去修改FutureTask的状态同时去唤醒因get（）方法等待的线程。但是在runAndReset()方法里面并不会调用类似set(result)方法，也就是FutureTask的状态并不会改变，
        //并且也不会去唤醒因为get（）方法造成线程阻塞问题。所以这里的get方法会一直阻塞。但是如果定时任务线程池里面的执行的任务抛出异常了并且没有捕获，会导致因get方法而阻塞的
        //线程被唤醒，但是这个线程get方法获得的是一个异常。
        // 那么新的问题来了为什么定时任务线程去执行任务不用FutureTask的run()方法而是去调用runAndReset()方法？
        //注意我们的FutureTask的run方法对于线程来说是一次性的，不可能一个线程在执行完了一个FutureTask的run方法后，这个线程再次来执行或者换成别的线程来执行，这都是不行的。
        //因为我们的线程不管执行run方法还是执行runAndReset()方法都会首先去执行一个判断if (state != NEW ||
        //            !UNSAFE.compareAndSwapObject(this, runnerOffset,
        //                                         null, Thread.currentThread()))
        //要知道我们的run方法在执行的末尾会去修改FutureTask的状态，原来是new执行完可能就不是new了，所以我们上面的调用runAndReset()方法。这个方法不会去改变FutureTask的
        //状态，所以可以反复周期性的执行
        try {
            System.out.println(scheduledFuture.get());
        }catch (Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("主线程结束");
    }
}

/**
 * 这个任务的主要作用就是做一个原子自增操作，并打印对应的任务名和执行时间，然后执行该任务的线程睡眠 5000毫秒
 */
class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    AtomicInteger atomicInteger=new AtomicInteger();

    public void run() {
        atomicInteger.incrementAndGet();
        //一定要注意的是这里的异常一定要捕获打印，不然定时任务会周期性空跑。
        //原理就是上面说的在线程执行过程中抛出异常后setException(ex);会吃掉。并将异常设置到outcome字段里面去,同时把FutureTask的状态设置为 private static final int EXCEPTIONAL  = 3;。
        // 但是我们又不用get方法获取，所以一定要自己捕获打印。同时因为抛出异常改变了状态，在下次跑的时候在第一行检验不通过就会就会直接返回。最后每次都这样，每次都是第一行校验不通过，直接返回
        if(true){

            throw  new NullPointerException();
        }
        System.out.println("Executing : " + name + ", Current Seconds : " + new Date().getSeconds());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}