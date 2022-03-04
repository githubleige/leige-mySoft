package ThreadTest;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) {
        Thread parkThread = new Thread(new ParkThread());
        parkThread.start();

        System.out.println(Thread.currentThread()+"唤醒parkThread");
        //为指定线程parkThread提供“许可”
        LockSupport.unpark(parkThread);
        new Thread(()->{
            LockSupport.unpark(parkThread);
            System.out.println(Thread.currentThread()+"唤醒parkThread");
        }).start();
        //唤醒因为park而等待的线程也可以通过interrupt。
//        parkThread.interrupt();
    }

    static class ParkThread implements Runnable{

        @Override
        public void run() {
            System.out.println("ParkThread开始执行");
            // 等待“许可”
            LockSupport.park();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ParkThread执行完成");
        }
    }
}