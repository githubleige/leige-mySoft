package dynamicProgram;

public class minPathSum {
    public static void main(String[] args) {
        minPathSum aa=new minPathSum();
//        int[][] grid=new int[][]{{1,3,1},{1,5,1},{4,2,1}};
        int[][] grid=new int[][]{{1,2,3},{4,5,6}};
        System.out.println(minPathSum1(grid));
        System.out.println(aa.minPathSum(grid));
    }
    public static int minPathSum1(int[][] grid) {
        int rows=grid.length,columns=grid[0].length;
        int[][] arr=new int[rows][columns];
        arr[rows-1][columns-1]=grid[rows-1][columns-1];
        for(int i=rows-2;i>=0;i--){
            arr[i][columns-1]=grid[i][columns-1]+arr[i+1][columns-1];
        }
        for(int j=columns-2;j>=0;j--){
            arr[rows-1][j]=grid[rows-1][j]+arr[rows-1][j+1];
        }
        for(int i=rows-2;i>=0;i--)
            for(int j=columns-2;j>=0;j--){
                arr[i][j]=Math.min(arr[i+1][j],arr[i][j+1])+grid[i][j];
            }
        System.out.println(arr[0][0]);
         int i=0,j=0;
        while ((i!=rows-1)&&(j!=columns-1)){
            System.out.println("行： "+i+",列： "+j);
            if(arr[i+1][j]>arr[i][j+1]){
                j++;
            }else {
                i++;
            }
        }
        if(i==rows-1){
            while (j<=columns-1){
                System.out.println("行： "+i+",列： "+j);
                j++;
            }
        }else{
            while ((i<=rows-1)){
                System.out.println("行： "+i+",列： "+j);
                i++;
            }
        }
        return arr[0][0];
    }

    public  int minPathSum(int[][] grid) {
        int rows=grid.length,columns=grid[0].length;
        int[][] arr=new int[rows][columns];
        arr[rows-1][columns-1]=grid[rows-1][columns-1];
        for(int i=rows-2;i>=0;i--){
            arr[i][columns-1]=grid[i][columns-1]+arr[i+1][columns-1];
        }
        for(int j=columns-2;j>=0;j--){
            arr[rows-1][j]=grid[rows-1][j]+arr[rows-1][j+1];
        }
        for(int i=rows-2;i>=0;i--)
            for(int j=columns-2;j>=0;j--){
                arr[i][j]=Math.min(arr[i+1][j],arr[i][j+1])+grid[i][j];
            }
        return arr[0][0];
    }
}
