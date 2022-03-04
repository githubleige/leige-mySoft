package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author  Fox
 *
 * 关闭指针压缩（-XX:-UseCompressedOops）
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        //jvm延迟偏向
        Thread.sleep(5000);
        Object obj = new Test();
        //Object obj = new Integer[4];
        //原来在5秒后穿件的obj对象最后3位101(开启偏向锁模式)，但是如果这期间我调用了hashCode，会导致偏向锁的撤销。为无锁状态
        //因为偏向锁标识为001，所以加锁的话直接变为轻量级锁
//        obj.hashCode();
        //查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(()->{
            synchronized (obj){
//                obj.hashCode();   正在偏向的话，调用hashCode方法，直接变成重量级锁
                //打印当前的线程名字+竞争同步对象的内存布局
                System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj).toPrintable());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"释放锁\n"+ClassLayout.parseInstance(obj).toPrintable());

            // jvm 优化
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        },"Thread1").start();


//        Thread.sleep(2000);
//
//
//        new Thread(()->{
//            synchronized (obj){
//                //打印当前的线程名字+竞争同步对象的内存布局
//                System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj).toPrintable());
//            }
//        },"Thread2").start();

    }
}

class Test{
    private boolean flag;
    private long  p;
}
