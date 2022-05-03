package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Fox
 *
 * 偏向锁批量重偏向测试
 *
 * 以class为单位，为每个class维护一个偏向锁撤销计数器，每一次该class的对象发生偏向锁撤销
 * 操作时，该计数器+1，当这个值达到重偏向阈值（默认20）时，JVM就认为该class的偏向锁有问
 * 题，因此会进行批量重偏向，意思就是之后下面的20之后40的对象的对象头会发生重偏向，线程的id会偏向当前锁的线程id
 * 当class维护一个偏向锁撤销计数器的数值达到40.新创建的该class对象就是无锁状态，不再是匿名偏向状态
 */
public class BiasedLockingTest2 {
    public static void main(String[] args) throws  InterruptedException {
        //延时产生可偏向对象
        Thread.sleep(5000);
        // 创建一个list，来存放锁对象
        List<Object> list = new ArrayList<>();
        
        // 线程1
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                // 新建锁对象
                Object lock = new Object();
                //保证刚创建出来的偏向对象偏向thead1。
                synchronized (lock) {
                    list.add(lock);
                }
            }
            try {
                //为了防止JVM线程复用，在创建完对象后，保持线程thead1状态为存活
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thead1").start();

        //睡眠3s钟保证线程thead1创建对象完成。保证50个对象都偏向thead1成功，并放入list
        Thread.sleep(3000);
        System.out.println("打印thead1，list中第98个对象的对象头：");
        System.out.println((ClassLayout.parseInstance(list.get(97)).toPrintable()));
        
        // 线程2
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                Object obj = list.get(i);
                if(i>=15&&i<=21||i>=38){
                    System.out.println("thread2-第" + (i + 1) + "次加锁执行前\t"+
                            ClassLayout.parseInstance(obj).toPrintable());
                }
                synchronized (obj) {
                    if(i>=15&&i<=21||i>=38){
                        System.out.println("thread2-第" + (i + 1) + "次加锁执行中\t"+
                                ClassLayout.parseInstance(obj).toPrintable());
                    }
                }
                if(i==17||i==19){
                    System.out.println("thread2-第" + (i + 1) + "次释放锁\t"+
                            ClassLayout.parseInstance(obj).toPrintable());
                }
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thead2").start();


//        Thread.sleep(3000);
//
//        new Thread(() -> {
//            for (int i = 0; i < 100; i++) {
//                Object lock =list.get(i);
//                if(i>=17&&i<=21||i>=35&&i<=41||(i>=95&&i<98)){
//                    System.out.println("thread3-第" + (i + 1) + "次加锁执行前\t"+
//                            ClassLayout.parseInstance(lock).toPrintable());
//                }
//                synchronized (lock){
//                    if(i>=17&&i<=21||i>=35&&i<=41||(i>=95&&i<98)){
//                        System.out.println("thread3-第" + (i + 1) + "次加锁执行中\t"+
//                                ClassLayout.parseInstance(lock).toPrintable());
//                    }
//                }
//            }
//        },"thread3").start();


        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock =list.get(i);
                if(i>=17&&i<=21||i>=35&&i<=41){
                    System.out.println("thread3-第" + (i + 1) + "次加锁执行前\t"+
                            ClassLayout.parseInstance(lock).toPrintable());
                }
                synchronized (lock){
                    if(i>=17&&i<=21||i>=35&&i<=41){
                        System.out.println("thread3-第" + (i + 1) + "次加锁执行中\t"+
                                ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
            }
        },"thread3").start();



        Thread.sleep(3000);
        System.out.println("查看新创建的对象");
        System.out.println((ClassLayout.parseInstance(new Object()).toPrintable()));

        LockSupport.park();


    }
}