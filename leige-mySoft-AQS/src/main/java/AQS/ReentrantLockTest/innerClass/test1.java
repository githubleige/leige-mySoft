package AQS.ReentrantLockTest.innerClass;

public class test1 {
    public static void main(String[] args) {
        Super1 super1 = new Super1();
        super1.setState(7);
//        sub1 sub1=new sub1();
        Isub1 aa=super1.new Sub1();
        System.out.println(aa.get());
    }
}
