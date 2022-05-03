package StringTest;

public class test3 {
    public static void main(String[] args) {
        String s1 =new String("he") +new String("llo");
        String s2 = s1.intern();

        System.out.println(s1 == s2);
    }
}
