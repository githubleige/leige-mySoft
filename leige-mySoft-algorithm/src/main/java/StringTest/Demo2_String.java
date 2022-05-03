package StringTest;

public class Demo2_String {



    public static void main(String[] args) {

        String st1 = "ab";

        String st2 = "abc";
        //任何数据和字符串进行加号（+）运算，最终得到是一个拼接的新的字符串。
        String st3 = st1 + "c";

        System.out.println(st2 == st3);

        System.out.println(st2.equals(st3));

    }

}
