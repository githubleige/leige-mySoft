package leetcode;

public class uniquePaths {

    public static void main(String[] args) {

        System.out.println(uniquePaths(3,7));
    }

    /**
     * m行n列的矩阵
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths1(int m, int n) {
        if(m==1||n==1){
            return 1;
        }
        return uniquePaths(m-1,n)+uniquePaths(m,n-1);


    }

    public static int uniquePaths(int m, int n) {

        int[][] arr=new int[m][n];
        for(int i=0;i<m;i++){
            arr[i][0]=1;
        }
        for(int j=0;j<n;j++){
            arr[0][j]=1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                arr[i][j]=arr[i-1][j]+arr[i][j-1];
            }
        }
        return arr[m-1][n-1];
    }
}
