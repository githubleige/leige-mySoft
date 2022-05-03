package dynamicProgram;


public class bagkiller {

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        int[] w= {2,5,4,2,3};
        int[] v= {6,3,5,4,6};
        System.out.println(bagkiller(w,v,12));
    }
    public static int bagkiller(int[] w,int[] v,int n) {
        int [][] dp=new int[w.length+1][n+1];
        for (int i=1;i<=w.length;i++) {
            for (int j=1;j<=n;j++) {
                if(j<w[i-1]) //装不下
                    dp[i][j]=dp[i-1][j]; //就等于i-1件物品容量等于j时候的价值
                else
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-w[i-1]]+v[i-1]);//装得下分为两种情况1.装 2.不装
            }
        }
        return dp[w.length][n];
    }
}

