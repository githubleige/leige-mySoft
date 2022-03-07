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
        //这里的lock.newCondition()获得其实是一个内部类的对象， 这个类就是：
        // public class ConditionObject implements Condition, java.io.Serializable
        //ConditionObject是在AQS中的一个内部类。所以要想获取ConditionObject对象必须先要有外部类的AQS对象才可以
        //ReentrantLock中的内部类：NonfairSync的继承关系是：NonfairSync->Sync->AQS.
        //所以一开始在创建new ReentrantLock()的时候：
        //    public ReentrantLock() {
        //        sync = new NonfairSync();
        //    }
        //调用上面的构造器。把ReentrantLock实例对象中的private final Sync sync属性进行填充。
        //所以ConditionObject对象寄生的AQS对象，在一开始就产生了。就是在创建ReentrantLock对象时，创建的对象，sync属性值得填充。
        //创建ConditionObject对象：sync.new ConditionObject();
        //要知道内部类是可以直接访问外部类的属性，所以ConditionObject对象和创建他时用的AQS对象是公用一个private volatile int state;属性的。
        //所以这就是把condition和上面的lock紧紧联系在一起的纽带。
        // 因为从本质上来说他们公用一个互斥量state，这个state既可以被AQS对象直接访问也可以被ConditionObject对象直接访问
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
