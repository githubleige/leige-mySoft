package synchronizedTest;

public class test1 {
    Object o=new Object();
    public synchronized void print(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i=0;
        int y=i++;
    }

    public static synchronized void print2(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i=0;
        int y=i++;
    }

    public  void print1(){
        synchronized (o){
            int i=0;
            int y=i++;
        }

    }

    public static void main(String[] args) {
        test1 aa=new test1();
        new Thread(()->{
            aa.print();
        },"线程1").start();
        new Thread(()->{
            print2();
        },"线程2").start();
    }
}
