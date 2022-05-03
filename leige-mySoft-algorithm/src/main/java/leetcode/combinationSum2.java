package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class combinationSum2 {
    private List<List<Integer>> list=new ArrayList<>();
    public static void main(String[] args) {
        /*Deque<Integer> deque=new ArrayDeque();
        deque.offer(10);
        deque.offer(11);
        deque.offer(12);
        List<Integer> list=new ArrayList<>();
        list.addAll(deque);
        System.out.println(deque);
        System.out.println(list);*/
        combinationSum2 aa=new combinationSum2();
        int[] arr=new int[]{2,3,6,7};
        List<List<Integer>> aList=aa.combinationSum(arr,7);
        System.out.println();
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if(candidates==null||candidates.length==0){
            return list;
        }
        Deque deque=new ArrayDeque();
        combination(candidates,target,deque,0);
        return list;
    }

    private void combination(int[] candidates,int target,Deque deque,int begin){
        if(target==0){
            List<Integer> sublist=new ArrayList<>();
            sublist.addAll(deque);
            list.add(sublist);
            return;
        }
        if(target<0){
            return;
        }
        for(int i=begin;i<candidates.length;i++){
            deque.offer(candidates[i]);
            combination(candidates,target-candidates[i],deque,i);
            deque.pollLast();
        }
    }
}
