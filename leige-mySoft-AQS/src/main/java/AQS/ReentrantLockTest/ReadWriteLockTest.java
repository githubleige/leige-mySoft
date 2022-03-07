package AQS.ReentrantLockTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Fox
 */

public class ReadWriteLockTest {

    public static void main(String[] args) throws InterruptedException {
        final Queue2 queue = new Queue2();






//        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        queue.get();
                }
            }).start();
//        }
        Thread.sleep(100);
        //        for (int i = 0; i < 3; i++) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                queue.put(new Random().nextInt(10000));
                queue.put("huangrui");
            }
        }).start();
//        }

    }
}

/**
 * ReentrantReadWriteLock整个类的结构解释：还是遵循了jdk的设计习惯，核心类（包含核心数据结构以及核心属性）成为外部工具类的内部类，
 * 外部类提供对内部类的数据结构等的操作访问修改方法，
 * 外部类的主要作用就是提供操作内类的工具的借口、API.就以ReentrantReadWriteLock类来说明。
 * ReentrantReadWriteLock里面有实现AQS的内部类abstract static class Sync extends AbstractQueuedSynchronizer
 * 这个Sync是一个抽象类，最终实现还是FairSync和NonfairSync。
 * 同时ReentrantReadWriteLock里面还提供了public static class ReadLock implements Lock, java.io.Serializable
 * 以及对应写锁public static class WriteLock implements Lock, java.io.Serializable
 * 其实这两个ReadLock和WriteLock就是前面说的工具类。
 * 为什么不直接使用ReentrantReadWriteLock作为工具类？
 * 因为这是一个读写锁的类，读锁和写锁的逻辑是不同的。所以这里提供了两个工具类。
 * （ReentrantLock是独占锁，只有一种逻辑，所及就提供了ReentrantLock本身作为工具类）
 * 但是其内部逻辑是：ReadLock和WriteLock里面都是有一个： private final Sync sync;
 * 这个属性在ReadLock和WriteLock以及外部类ReentrantReadWriteLock都是公用的sync
 * 外部类ReentrantReadWriteLock提供ReadLock和WriteLock的公共逻辑方法
 * 同时AQS的实现Sync还有自己的内部类：主要是
 *  static final class ThreadLocalHoldCounter
 *             extends ThreadLocal<HoldCounter> {
 *             public HoldCounter initialValue() {
 *                 return new HoldCounter();
 *             }
 *        }
 *  还有一个内部类是HoldCounter，提供给ThreadLocalHoldCounter作为ThreadLocal对象使用的
 *  注意在构造器中就已经初始化好了private transient ThreadLocalHoldCounter readHolds;
 *  Sync() {
 *             readHolds = new ThreadLocalHoldCounter();
 *             setState(getState()); // ensures visibility of readHolds 保证可见性
 *         }
 *  下面就是ReentrantReadWriteLock的原理了：
 *  用一个AQS的private volatile int state;属性表示两种状态：高16位表示读锁加的次数（包括重入在内），低16位表示写锁重入的次数（因为写锁是独占式的）
 *  在高16位无法辨别出某一线程的重入次数。所以使用firstReaderHoldCount或者ThreadLocalHoldCounter来表示
 *
 *
 */
class Queue {
    //共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
    private Object data = "gelei";
    private int i=0;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    public void get() {
        System.out.println(Thread.currentThread().getName() + " be ready to read data!");
        //上读锁，其他线程只能读不能写：
        //读锁在一下情况下会假如成功：
        //1、没有独占锁
        //2、有独占锁但是独占锁和读锁都是同一个线程
        //3、有独占锁已经在阻塞队列里面等待了，并且位于h.next，下一个即将唤醒的线程，但是加锁的线程已经在这把锁上加了读锁，现在是重入，能成功
        //在加多所的时候会有一个判断如果已经有
        readLock.lock();
        i++;
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " have read data :" + data+i);
            if(i<3){
                get();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            readLock.unlock();
        }
    }

    public void put(Object data) {
        System.out.println(Thread.currentThread().getName() + " be ready to write data!");
        //上写锁，不允许其他线程读也不允许写
        writeLock.lock();
        try {
//            Thread.sleep(5000);
            Thread.sleep(50);
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " have write data: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            writeLock.unlock();
        }
    }
}