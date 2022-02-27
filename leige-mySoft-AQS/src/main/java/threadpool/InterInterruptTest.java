package threadpool;

import java.util.concurrent.TimeUnit;

/*interrupt是Thread类的实例方法，它的主要作用是给目标线程发送一个通知，有人希望你退出啦，
同时会将目标线程的中断标志设置为true，也就是已经有人打断过该线程了。至于目标线程如何处理，完全取决于目标线程自身*/
public class InterInterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }

        });

//        Thread t2 = new Thread(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(1000);
//            } catch (InterruptedException e) {
////                e.printStackTrace();
//                System.out.println(e.getMessage());
//            }
//        });

        t1.start();
//        t2.start();
        TimeUnit.MILLISECONDS.sleep(100);
        t1.interrupt();
    }
}
