package leetcode;

import java.util.Map;
import java.util.TreeMap;


public class deleteDuplicates2 {
    public static void main(String[] args) {

        TreeMap<Integer,Integer> treeMap=new TreeMap<>((o1, o2)->{
            return o1>o2 ? -1 : o1<o2?1:0;
        });
        treeMap.put(1,1);
        treeMap.put(2,1);
        treeMap.put(3,1);
        treeMap.put(4,1);
        treeMap.put(5,1);
        treeMap.put(0,1);
        for (Map.Entry<Integer,Integer> e : treeMap.entrySet()){
            System.out.println(e.getKey());
        }
    }
}
