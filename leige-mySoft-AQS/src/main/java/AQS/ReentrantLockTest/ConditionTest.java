package AQS.ReentrantLockTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fox
 * 等待唤醒机制 await/signal测试
 * ReentrantLock和Condition配合使用
 * synchronized和wait/notify/notifyAll配合使用
 */
public class ConditionTest {

    public static void main(String[] args) {
        //默认是非公平锁
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Object obj=new Object();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 开始处理任务");
                //会释放当前持有的锁，然后阻塞当前线程
                condition.await();
                //调用obj.wait();或者obj.notifyAll();必须先持有obj的锁才可以。也就是要和synchronized ()配合使用，否则会抛出：Exception in thread "thread1" java.lang.IllegalMonitorStateException
//                obj.wait();
//                lock.wait();
                System.out.println(Thread.currentThread().getName() + " 结束处理任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"thread1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 开始处理任务");

                Thread.sleep(2000);
                //唤醒因调用Condition#await方法而阻塞的线程
                condition.signal();
//                obj.notifyAll();
//                lock.notify();
                System.out.println(Thread.currentThread().getName() + " 结束处理任务");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"thread2").start();


    }
}
