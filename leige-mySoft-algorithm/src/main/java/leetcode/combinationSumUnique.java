package leetcode;

import java.util.*;

public class combinationSumUnique {
    List<List<Integer>> list=new ArrayList<>();
//    private Set<Integer> set=new HashSet<>();

    public static void main(String[] args) {
        combinationSumUnique a=new combinationSumUnique();
        int[] arr=new int[]{10,1,2,7,6,1,5};
//        Arrays.sort(arr);
        a.combinationSum2(arr,8);
        System.out.println();
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Deque<Integer> deque=new ArrayDeque<>();
        Arrays.sort(candidates);
        subcombinationSum2(candidates,target,0,deque);
        return list;
    }

    private void subcombinationSum2(int[] candidates, int target,int begin,Deque<Integer> deque){
        if(target==0){
            List<Integer> subList=new ArrayList<>();
            subList.addAll(deque);
            list.add(subList);
            return;
        }
        if(target<0||begin>=candidates.length){
            return;
        }
        int last=candidates[begin]-1;
        for(int i=begin;i<candidates.length;i++){
            if(candidates[i]!=last){
                if(target-candidates[i]<0){
                    break;
                }
                deque.add(candidates[i]);
                subcombinationSum2(candidates,target-candidates[i],i+1,deque);
                deque.pollLast();
                last=candidates[i];
            }

        }
    }
}
