package threadpool;

public class ThreadDemo extends Thread {

    private String name;

    public ThreadDemo(String name) {

        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());

        System.out.println(name);
    }


    public static void main(String[] args) {
     // new ThreadDemo("monkey老师").run();
       Thread t1=new ThreadDemo("monkey老师");
       t1.setDaemon(true);
       t1.start();

        System.out.println("hello world");
    }

}
