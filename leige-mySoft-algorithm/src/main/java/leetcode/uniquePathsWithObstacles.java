package leetcode;

public class uniquePathsWithObstacles {

    public static void main(String[] args) {
//        int[][] arr=new int[][]{{0,0,0},{0,1,0},{0,0,0}};
        int[][] arr=new int[][]{{0,1},{0,0}};
        System.out.println(uniquePathsWithObstacles(arr));
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rows=obstacleGrid.length,columns=obstacleGrid[0].length;
        int[][] arr=new int[rows][columns];
        if((obstacleGrid[rows-1][columns-1]==1)||(obstacleGrid[0][0]==1)){
            return 0;
        }

        for(int i=rows-1;i>=0;i--){
            if(obstacleGrid[i][columns-1]==1){
                for(;i>=0;i--){
                    arr[i][columns-1]=0;
                }
            }else {
                arr[i][columns-1]=1;
            }

        }

        for(int j=columns-1;j>=0;j--){
            if(obstacleGrid[rows-1][j]==1){
                for(;j>=0;j--){
                    arr[rows-1][j]=0;
                }
            }else
            {
                arr[rows-1][j]=1;
            }
        }

        for(int i=rows-2;i>=0;i--){
            for(int j=columns-2;j>=0;j--){
                if(obstacleGrid[i][j]==1){
                    arr[i][j]=0;
                }else {
                    arr[i][j]=arr[i+1][j]+arr[i][j+1];
                }
            }
        }
        return arr[0][0];
    }
}
