package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

public class HelloJol {
    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(5000);
        A o = new A();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    static class A{
        private int a;
        String str="hello world e";
    }
}
