package threadactiveness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Fox
 * 死锁
 */

public class DeadLockTest {
    public static final Logger log = LoggerFactory.getLogger(DeadLockTest.class);

    private static String a = "a";
    private static String b = "b";

    public static void main(String[] args) {
        Thread threadA = new Thread(()->{
            synchronized (a) {
                log.debug("threadA进入a同步块，执行中...");
                try {
                    //Thread.sleep(2000);   条件队列作用： 打破死锁的循环
                    a.wait(5000);
                    synchronized (b) {
                        log.debug("threadA进入b同步块，执行中...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"threadA");

        Thread threadB = new Thread(()->{
            synchronized (b) {
                log.debug("threadB进入b同步块，执行中...");
                try {
                    //b.wait(5000);
                    Thread.sleep(2000);
                    synchronized (a) {
                        log.debug("threadB进入a同步块，执行中...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"threadB");

        threadA.start();
        threadB.start();

    }

}
