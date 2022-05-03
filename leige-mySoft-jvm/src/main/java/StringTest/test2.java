package StringTest;

public class test2 {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        String c = "c";
        StringBuilder temp = new StringBuilder();
        temp.append(a).append(b).append(c);
        //下面的代码只会在堆内存中创建对象，不会在常量池中创建
        String s = temp.toString();
        System.out.println(s==s.intern());
    }
}
