package AQS.ReentrantLockTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fox
 * 公平锁
 */
public class ReentrantLockDemo5 {

    public static void main(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock(true); //公平锁
        ReentrantLock lock = new ReentrantLock(); //非公平锁
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                //即使线程1在线程2之前启动，也不代表线程1的lock.lock();比线程2的lock.lock();先执行要看线程调度策略和时间片
                lock.lock();
                try {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "t" + i).start();
        }
        // 1s 之后去争抢锁，保证上面500个线程都执行lock.lock();代码
        Thread.sleep(1000);

        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " running...");
                } finally {
                    lock.unlock();
                }
            }, "强行插入" + i).start();

        }

    }

}
