package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author  Fox
 *
 * 关闭指针压缩（-XX:-UseCompressedOops）
 */
public class ObjectTest2 {

    public static void main(String[] args) throws InterruptedException {
        //jvm延迟偏向
        Thread.sleep(5000);
        Object obj = new Test2();
        //Object obj = new Integer[4];
        //obj.hashCode();
        //查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj.getClass()).toPrintable());
            System.out.println("======================");
            synchronized (obj.getClass()){
                //打印当前的线程名字+竞争同步对象的内存布局
                System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj.getClass()).toPrintable());
            }
//            System.out.println(Thread.currentThread().getName()+"释放锁\n"+ClassLayout.parseInstance(obj).toPrintable());

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

class Test2{
    private boolean flag;
    private long  p;
}
