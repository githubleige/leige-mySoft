package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockReason {
    private static final BlockingQueue<Integer> blockingQueue=new ArrayBlockingQueue<>(10);
    public static void main(String[] args) {
        Thread t1=new Thread(()->{
            try {
                //自己给自己打上标记
                Thread.currentThread().interrupt();
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"线程结束");
            }
        });
        t1.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t1.interrupt();
        System.out.println(Thread.currentThread().getName()+"线程结束");
    }
}
