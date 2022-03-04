package AQS.ReentrantLockTest;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 主要是用来限流的
 * 为什么一个线程在在被唤醒的时候，还要进行传播唤醒？
 * 注意这里的共享锁的传播唤醒是因为并发问题的存在，场景举例，共享资源是3：
 * 已经获取锁的的3个线程（t1,t2,t3）.注意在第一次就获取到锁的时候直接对state进行cas这操作后就到了业务逻辑的代码中。
 * 但是现在t4,t5,t6.来现在资源数不够先进入MESA的同步阻塞队列（调用lockSupport的park阻塞线程）
 * 现在假如同时t1,t2同时结束进入到release方法进行释放锁，那么资源数就会被改成2，这里都没问题。
 * 但是在释放资源的时候后还要做两件事：1、把头结点h.waitStatus进行compareAndSetWaitStatus(head, -1, 0)
 * 现在问题来了，两个线程过来都想把h.waitStatus的字段从-1->0.因为这是compareandswap,所以这里只会有一个线程修改成功，比如说t1修改成功。
 * 另一个线程t2现在怎么办？难道去等待.难道说等t1唤醒的线程去修改头结点后，我t2才能了成功释放，t2在不断的进行空循环，浪费资源么？
 * 这样肯定不行的，所以我们设计了waitStatus的-3状态。比如说这个时候把头结点的waitStatus状态改成-3了
 * 注意此时虽然t2把head节点的waitStatus改成了-3，但是t2并没有去唤醒线程。只有t1去唤醒线程了，假设唤醒的是t4
 * t4开始执行，首先进行资源数state-1,并获得剩余资源数，然后根据剩余资源数>0或者原来的头结点的waitStatus字段是否小于0（其实是-3）来继续调用唤醒线程。
 * 进行传播唤醒线程，虽然会导致重复唤醒线程，但是唤醒都是同一个线程啊，就相当于在同一个线程上调用了两个unpark方法，这个是没有事的(例子LockSupportTest)
 * 现在假设
 *
 */
public class SemaphoneTest2 {

    /**
     * 实现一个同时只能处理5个请求的限流器
     */
    private static Semaphore semaphore = new Semaphore(5);

    /**
     * 定义一个线程池
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor
            (10, 50, 60,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    /**
     * 模拟执行方法
     */
    public static void exec() {
        try {
            //占用1个资源
            semaphore.acquire(1);
            //TODO  模拟业务执行
            System.out.println(Thread.currentThread()+"执行exec方法");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放一个资源
            semaphore.release(1);
        }
        System.out.println(Thread.currentThread()+"做后续处理");
    }

    public static void main(String[] args) throws InterruptedException {
        {
            for (; ; ) {
//                Thread.sleep(10);
                // 模拟请求以10个/s的速度
                executor.execute(() -> exec());
            }
        }
    }
}