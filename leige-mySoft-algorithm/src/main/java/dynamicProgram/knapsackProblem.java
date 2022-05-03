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
        //arrs[i][j]表示的是在前i个物品中能够装入最大重量为j的背包的最大价值
        int[][] arrs=new int[values.length][W+1];
        //在不允许装任何一件商品的情况下，不管你背包能承受多大的重量，总价值都是0
        for(int i=0;i<W+1;i++){
            arrs[0][i]=0;
        }
        //在背包承受的最大重量是0时候，不管你有多少件商品可以承装，总价值也是0
        for(int i=0;i<values.length;i++){
            arrs[i][0]=0;
        }

        //下面的核心思想就是，现在有n件商品，对应的价值是v1,v2……vn,对应的重量是W1,W2,……Wn
        //用上面的两个数组构造dp数组
        //现在假设给我一个上限，最大重量不能超过W
        //构造dp数组，行是前i个商品添加进来的情景,列是在上限为j的情况，综合arrs[i][j]表示：
        //在前i个商品中选择商品重量不超过j的情况下的最大价值。很容易想到就是初始化arrs[i][j]是一个行为v[].length+1，列为W[].length+1的数组
        //为什么全部都加1.方便理解行为0，列为0.一个表示在前0商品中选择，一个重量不能超过0.那价值只能是0
        for(int i=1;i<=weights.length-1;i++)
            for(int j=1;j<=W;j++){
                //这种情况主要就是针对现在最大承载重量是j，但是我现在往现有的商品中添加一种商品选择，但是这个新加的商品的重量一下子
                //就超出我的上限了，所以没有选择。（例子：最大承载重量是5.我往商品里加一个重量为6的商品。那不跟没加的一样么）
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
