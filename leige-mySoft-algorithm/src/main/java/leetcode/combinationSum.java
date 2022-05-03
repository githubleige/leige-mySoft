package leetcode;

import java.util.*;

/**
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。 
 *
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 */
public class combinationSum {
    /*List<List<Integer>>=new A
    public static void main(String[] args) {
        System.out.println(2-1+"*"+3);
    }



    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        int[] arr=new int[candidates.length+1];
        arr[0]=0;
        for(int i=1;i<arr.length;i++){
            arr[i]=candidates[i-1];
        }
        Map<String,List<List<Integer>>> map=new HashMap<>();
        int[][] dp=new int[candidates.length+1][target+1];
        for(int i=0;i<candidates.length+1;i++){
            dp[i][0]=0;
        }
        for(int j=0;j<target+1;j++){
            dp[0][j]=0;
        }
        List<List<Integer>> temp,descTemp;
        for(int i=1;i<candidates.length+1;i++){
            for(int j=1;j<target+1;j++){
                if(arr[i]>j){
                    temp=map.get(i-1+"*"+j);
                    if(temp==null){
                        temp =new ArrayList<List<Integer>>();
                    }
                    map.put(i+"*"+j,temp);
                }else{
                    descTemp=new ArrayList<List<Integer>>();
                    if(map.get(i-1+"*"+j)!=null){
                        descTemp.addAll(map.get(i-1+"*"+j));
                    }
                    List<Integer> copyTempList;
                    for(int count=1;count*arr[i]<=j;count++){
                        temp=map.get(i+"*"+(j-count*arr[i]));
                        if(temp==null){
                            temp =new ArrayList<List<Integer>>();
                            temp.add(new ArrayList<Integer>());
                        }
                        for(List<Integer> tempList : temp){
                            copyTempList=new ArrayList<>() ;
                            Collections.copy(copyTempList,tempList);
                            int countTemp=count;
                            while(countTemp!=0){
                                copyTempList.add(arr[i]);
                                countTemp--;
                            }
                        }

                    }

                }
            }
        }
    }*/
}
