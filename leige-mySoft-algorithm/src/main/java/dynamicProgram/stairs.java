package dynamicProgram;

import java.util.Scanner;

public class stairs {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入需要走的楼梯数目：");
        int stairsNum=sc.nextInt();
        System.out.println(recursiveStatirs(10));
    }

    public static int recursiveStatirs(int num){
        if(num==1){
            return 1;
        }else if(num==2){
            return 2;
        }else{
            return recursiveStatirs(num-1)+recursiveStatirs(num-2);
        }
    }
}
