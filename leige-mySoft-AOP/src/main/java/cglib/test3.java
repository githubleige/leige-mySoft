package cglib;

public class test3 {
    public String name="gelei";
    public int age=20;

    public test3(){
        System.out.println("hello world");
    }

    public static void main(String[] args) {
       String str="cc";
       switch (str)
       {
           case "aa":
               System.out.println("aa");
           case "bb":
               System.out.println("bb");
       }
    }
}
