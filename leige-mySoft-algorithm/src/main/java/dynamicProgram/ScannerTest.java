package dynamicProgram;

import java.util.Scanner;

public class ScannerTest {
    public static void main(String[] args) {
        //标准输入流和Linux相似，在不做重定向的情况下System.in表示键盘输入
        Scanner sc=new Scanner(System.in);
        //设置分割符号
//        sc.useDelimiter("\n");
//        sc.useDelimiter("t");
        while(sc.hasNext()){
            //System.out表示标准的输出流，在不做重定向的情况下是显示器
            System.out.println("键盘输入的是："+sc.next());
        }
    }
}
