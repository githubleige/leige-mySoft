package volatileTest;

import java.util.Map;

public class test1 {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

//    public int inc = 0;
//
//    public synchronized void increase() {
//        inc++;
//        System.out.println(inc);
//    }

    public static void main(String[] args) throws Exception{
        while(true){
            final test1 test = new test1();
            test.inc=0;
            for(int i=0;i<10;i++){
                new Thread(){
                    public void run() {
                        int j=0;
                        for(;j<1000;j++){
                            test.increase();

                        }
//                        System.out.println(j);
                    }
                }.start();
            }

//        while(Thread.activeCount()>1)  //保证前面的线程都执行完
//            Thread.yield();
            Thread.sleep(10000L);
            Map<Thread, StackTraceElement[]> map= Thread.getAllStackTraces();

            for(Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet())
                System.out.println(entry.getKey().getName());
            System.out.println(map.size());
            System.out.println(test.inc);
        }

    }

}
