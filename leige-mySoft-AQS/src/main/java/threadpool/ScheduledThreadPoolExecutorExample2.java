package threadpool;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledThreadPoolExecutorExample2 {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        Task2 task = new Task2("任务");
        System.out.println("Created : " + task.getName());
       // executor.schedule(task, 2, TimeUnit.SECONDS);
//        scheduleWithFixedDelay是以上一个周期性任务执行结束的时间+delay来决定下一个任务的exectuteTime
        executor.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS); //任务+延迟
//        scheduleAtFixedRate方法是通过上一个任务的exectuteTime（这是ScheduledFutureTask属性的一个字段）+period来决定下一个任务的exectuteTime
        executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);//任延迟取最大值 稳定定时器

    }
}

class Task2 implements Runnable {
    private String name;

    public Task2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    AtomicInteger atomicInteger=new AtomicInteger();

    public void run() {
        atomicInteger.incrementAndGet();
//        if(true){
//
//            throw  new NullPointerException();
//        }
        System.out.println("Executing : " + name + ", Current Seconds : " + new Date().getSeconds());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}