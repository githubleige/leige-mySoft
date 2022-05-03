package StringTest;

public class test1 {
    public static void main(String[] args) {
        String s = "a" + "b" + "c";  //就等价于String s = "abc";
        String a = "a";
        String b = "b";
        String c = "c";
        String s1 = a + b + c;
        System.out.println(s1==s);
    }
}
