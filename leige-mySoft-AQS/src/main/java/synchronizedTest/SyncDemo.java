package synchronizedTest;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fox
 */

public class SyncDemo {

    private static volatile int counter = 0;
    private Lock lock=new ReentrantLock();
    Condition con1=lock.newCondition();
    Condition con2=lock.newCondition();

    public static void increment() {
        counter++;
    }

    public static void decrement() {
        counter--;
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock=new ReentrantLock();
        Condition con1=lock.newCondition();
        Condition con2=lock.newCondition();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        //思考： counter=？
        System.out.println("counter{}"+ counter);
        System.out.println(con1==con2);

    }
}
