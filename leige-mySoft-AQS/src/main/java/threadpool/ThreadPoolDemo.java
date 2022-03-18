package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//-1的补码（32位）是0xffffffff（8个f）
//线程池的方法：termination.signalAll(); //唤醒调用了等待线程池终止的线程 awaitTermination()
public class ThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService1 = Executors.newCachedThreadPool();//快
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);//慢
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();//最慢

        ExecutorService executorService4=new ThreadPoolExecutor(5,
        5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
        new myThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        Future<?> future=executorService4.submit(()->{
            int i=1/0;
            System.out.println(Thread.currentThread().getName());
            System.out.println("hello world");
        });

        executorService4.submit(()->{
            try {
                System.out.println("ssndskdsd");
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
//                e.printStackTrace();
                System.out.println(e.getMessage());
                throw new CancellationException();
//                throw new Throwable("he");
            }
        });
        System.out.println(future.get());




//        RejectedExecutionHandler rejectedExecutionHandler=  new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                // 写数据库的代码
//            }
//        };
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
//                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),new MonkeyRejectedExecutionHandler());//自定义线程
//
//        for (int i = 1; i <= 100; i++) {
//            threadPoolExecutor.execute(new MyTask(i));
//
//        }
    }
}

/***
 * 项目
 */
class MyTask implements Runnable {
    int i = 0;

    public MyTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        //接收到项目
        System.out.println(Thread.currentThread().getName() + "程序员做第" + i + "个项目");
        try {
            //下面这段的睡眠逻辑是表示开始做项目
            //如果不设置睡眠时间，就会出现后面的任务提交过来，前面的任务已经完成，这个执行完任务的线程已经返回给了线程池，
            //这个时候新的任务提交过来，不用创建新的线程，直接把前面执行结束的线程拿过来用就可以了。
            Thread.sleep(3000L);//业务逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class myThreadFactory implements ThreadFactory{
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    myThreadFactory() {
        group = Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        //下面是一段没有意义的代码，永远不会等到执行
        //原因是：1、提交的一个任务抛出异常会被直接设置到setException(ex);
        //2、即使用线程池去获取outcome,但是get方法必须捕获异常
        t.setUncaughtExceptionHandler((thread,e)-> System.out.println(thread.getName()+":"+e.getMessage()));
        return t;
    }
}
//class Thread1 extends Thread{
//    @Override
//    public void run() throws Exception{
//
//    }
//}
