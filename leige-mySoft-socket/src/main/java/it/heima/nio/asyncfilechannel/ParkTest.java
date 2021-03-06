package it.heima.nio.asyncfilechannel;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * park的参数，表示挂起的到期时间，第一个如果是true，表示绝对时间，则var2为绝对时间值，单位是毫秒。
 * 第一个参数如果是false，表示相对时间，则var2为相对时间值，单位是纳秒。
 */
public class ParkTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        //null是一切对象的实例
        Unsafe unsafe = (Unsafe) f.get(null);

        //线程1必须等待唤醒
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1:执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("线程1:挂起，等待唤醒才能继续执行任务");
            //
            unsafe.park(false, 0);
            System.out.println("线程1:执行完毕");
        });
        thread1.start();

        //线程2必须等待唤醒
        Thread thread2 = new Thread(() -> {
            System.out.println("线程2:执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("线程2:挂起，等待唤醒才能继续执行任务");
            unsafe.park(false, 0);
            System.out.println("线程2:执行完毕");
        });
        thread2.start();

        Thread.sleep(5000);
        System.out.println("唤醒线程2");
        unsafe.unpark(thread2);
        Thread.sleep(1000);
        System.out.println("唤醒线程1");
        unsafe.unpark(thread1);

        //线程3自动唤醒
        Thread thread3 = new Thread(() -> {
            System.out.println("线程3:执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("线程3:挂起，等待时间到自动唤醒");
            unsafe.park(false, 3000000000L);
            System.out.println("线程3:执行完毕");
        });
        thread3.start();

        //线程4自动唤醒
        Thread thread4 = new Thread(() -> {
            System.out.println("线程4:执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("线程4:挂起，等待时间到自动唤醒");
            long time = System.currentTimeMillis() + 3000;
            unsafe.park(true, time);
            System.out.println("线程4:执行完毕");
        });
        thread4.start();
    }


}
