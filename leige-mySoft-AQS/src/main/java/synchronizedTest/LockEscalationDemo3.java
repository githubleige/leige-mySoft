package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Fox
 * 测试 偏向锁，轻量级锁，重量级锁标记变化
 * 关闭延迟开启偏向锁： -XX:BiasedLockingStartupDelay=0
 * 无锁 001
 * 偏向锁 101
 * 轻量级锁 00
 * 重量级锁 10
 * 演示：偏向锁->轻量级锁（cas次数过多->重量级锁）
 * 重量级锁释放锁，变成无锁（001），再次获取直接轻量级锁
 */
public class LockEscalationDemo3 {

    public static void main(String[] args) throws InterruptedException {

//        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(5000);
        Object obj = new Object();
        // 思考： 如果对象调用了hashCode,还会开启偏向锁模式吗
        //obj.hashCode();
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){
                    // 思考：偏向锁执行过程中，调用hashcode会发生什么？
                    obj.hashCode();
//                    //obj.notify();
////                    try {
////                        obj.wait(50);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }

                    System.out.println(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
                System.out.println(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread1");
        t1.start();
        t1.join();

//        obj.hashCode();

        System.out.println(Thread.currentThread().getName()+"的hashcode后"+ClassLayout.parseInstance(obj).toPrintable());


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){

                    System.out.println(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread2").start();*/

//        Thread.sleep(2000);
//        System.out.println("调用hashCode()方法前");
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
//        obj.hashCode();
//        System.out.println("调用hashCode()方法后");
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){

                    System.out.println(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread3").start();*/
//
//
//        Thread.sleep(5000);
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());



    }
}