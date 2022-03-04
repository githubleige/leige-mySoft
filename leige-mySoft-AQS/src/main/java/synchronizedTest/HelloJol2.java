package synchronizedTest;

import org.openjdk.jol.info.ClassLayout;

public class HelloJol2 {
    public static void main(String[] args) {
        A o = new A();
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);
    }

    static class A{
        private int a;
        String str="hello world e";
    }
}
