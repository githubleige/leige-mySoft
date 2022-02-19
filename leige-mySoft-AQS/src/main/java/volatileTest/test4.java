package volatileTest;

public class test4 {
    public static volatile int race = 0;

    public static void main(String[] args) {
        System.out.println("hello world");
    }

    public static void increase() {

        race++;

    }
}
