package leetcode;

import java.util.ArrayList;
import java.util.List;

public class spiralOrder {
    int index;
    int indexLength;

    int above,blow,left,right;

    List<Integer> list=new ArrayList<>();
    public static void main(String[] args) {
        int[][] a=new int[][]{{1,2,3,4},{5,6,8,9},{10,11,12,13}};
//        System.out.println(a[2][0]);
        spiralOrder aa=new spiralOrder();
        aa.spiralOrder(a);
        System.out.println(aa.list);
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int rows=matrix.length;
        int columns=matrix[0].length;
        above=0;blow=rows-1;left=0;right=columns-1;
        indexLength=rows*columns;
        while(index<indexLength){
            LR(matrix,above,left,right);
            AB(matrix,right,above,blow);
            RL(matrix,blow,right,left);
            BA(matrix,left,blow,above);
        }
        return list;
    }

    private void LR(int[][] matrix,int row,int Lcolumn,int Rcolumn){
        if(index>=indexLength){
            return;
        }
        for(int j=Lcolumn;j<Rcolumn+1;j++){
            list.add(matrix[row][j]);
            index++;
        }
        above++;
    }

    private void RL(int[][] matrix,int row,int Rcolumn,int Lcolumn){
        if(index>=indexLength){
            return;
        }
        for(int j=Rcolumn;j>Lcolumn-1;j--){
            list.add(matrix[row][j]);
            index++;
        }
        blow--;
    }

    private void AB(int[][] matrix,int column,int Arow,int Brow){
        if(index>=indexLength){
            return;
        }
        for(int i=Arow;i<Brow+1;i++){
            list.add(matrix[i][column]);
            index++;
        }
        right--;
    }

    private void BA(int[][] matrix,int column,int Brow,int Arow){
        if(index>=indexLength){
            return;
        }
        for(int i=Brow;i>Arow-1;i--){
            list.add(matrix[i][column]);
            index++;
        }
        left++;
    }

    public List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }
        int rows = matrix.length, columns = matrix[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int total = rows * columns;
        int row = 0, column = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;
        for (int i = 0; i < total; i++) {
            order.add(matrix[row][column]);
            visited[row][column] = true;
            int nextRow = row + directions[directionIndex][0], nextColumn = column + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns || visited[nextRow][nextColumn]) {
                directionIndex = (directionIndex + 1) % 4;
            }
            row += directions[directionIndex][0];
            column += directions[directionIndex][1];
        }
        return order;
    }

}
