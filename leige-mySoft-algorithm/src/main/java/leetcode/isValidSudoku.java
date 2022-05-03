package leetcode;

import java.util.HashSet;
import java.util.Set;

public class isValidSudoku {
    public static void main(String[] args) {
        char[][] crr=new char[][]{{'5','3','.','.','7','.','.','.','.'},
                                 {'6','.','.','1','9','5','.','.','.'},
                                 {'.','9','8','.','.','.','.','6','.'},
                                 {'8','.','.','.','6','.','.','.','3'},
                                 {'4','.','.','8','.','3','.','.','1'},
                                 {'7','.','.','.','2','.','.','.','6'},
                                 {'.','6','.','.','.','.','2','8','.'},
                                 {'.','.','.','4','1','9','.','.','5'},
                                 {'.','.','.','.','8','.','.','7','9'}};
        System.out.println(isValidSudoku(crr));
    }
    public static boolean isValidSudoku(char[][] board) {
        Set<Character> setRows=new HashSet<>();
        Set<Character> setColumns=new HashSet<>();
        Set<String> setStr=new HashSet<>();
        //按照行的方式来查看
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if((board[i][j]!='.')&&(!setRows.add(board[i][j])||!setStr.add(""+i/3+j/3+board[i][j]))){
                    return false;
                }
                if((board[j][i]!='.')&&(!setColumns.add(board[j][i]))){
                    return false;
                }
            }
            setRows.clear();
            setColumns.clear();
        }
        return true;
    }
}
