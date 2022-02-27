package threadpool;

import java.util.concurrent.*;

//-1的补码（32位）是0xffffffff（8个f）
//线程池的方法：termination.signalAll(); //唤醒调用了等待线程池终止的线程 awaitTermination()
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService1 = Executors.newCachedThreadPool();//快
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);//慢
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();//最慢

        RejectedExecutionHandler rejectedExecutionHandler=  new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 写数据库的代码
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),new MonkeyRejectedExecutionHandler());//自定义线程

        for (int i = 1; i <= 100; i++) {
            threadPoolExecutor.execute(new MyTask(i));

        }
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
