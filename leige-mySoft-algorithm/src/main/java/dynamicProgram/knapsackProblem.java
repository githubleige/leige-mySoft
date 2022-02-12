package dynamicProgram;

import java.util.Scanner;

public class knapsackProblem {
    //背包装入每个商品对应的重量
    private static final int[] weights=new int[]{0,1,2,5,6,7};
    //背包装入每个商品对应的价值
    private static final int[] values=new int[]{0,1,6,18,22,28};
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入背包最大可以承受的重量：");
        if(weights.length!=values.length){
            throw new Exception("物品信息标注不全");
        }
        int W=sc.nextInt();
        //行列各加上1原因：需要包括商品的个数和最大重量的边界情况在内
        int[][] arrs=new int[values.length][W+1];
        //在不允许装任何一件商品的情况下，不管你背包能承受多大的重量，总价值都是0
        for(int i=0;i<W+1;i++){
            arrs[0][i]=0;
        }
        //在背包承受的最大重量是0时候，不管你有多少件商品可以承装，总价值也是0
        for(int i=0;i<values.length;i++){
            arrs[i][0]=0;
        }

        for(int i=1;i<=weights.length-1;i++)
            for(int j=1;j<=W;j++){
                if(weights[i]>j){
                    arrs[i][j]=arrs[i-1][j];
                }else{
                    //类似于数学上的数列，梳理出递推公式
                    //对于总重量不超过j的约束就体现在values[i]+arrs[i-1][j-weights[i]]这个公式中
                    //这里就利用了前面的计算结果
                    arrs[i][j]=Math.max(arrs[i-1][j],(values[i]+arrs[i-1][j-weights[i]]));
                }
            }

        System.out.println(arrs[values.length-1][W]);
        for(int i=0;i<values.length;i++){
            for(int j=0;j<W+1;j++){
                System.out.print(arrs[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }

    }
}
