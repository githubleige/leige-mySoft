package leetcode;

import java.util.TreeSet;

public class deleteDuplicates {
    public static void main(String[] args) {
//        TreeSet<Integer> treeSet=new TreeSet<>();
        TreeSet<Integer> treeSet=new TreeSet<>((o1,o2)->{
            return o1>o2 ? -1 : o1<o2?1:0;
        });
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(0);
        for (Integer e : treeSet){
            System.out.println(e);
        }
    }
}
