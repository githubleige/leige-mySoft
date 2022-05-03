package leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer, Integer>{

    private int capacity;

    public static void main(String[] args) {
//        LinkedHashMap<Integer, Integer> map=new LinkedHashMap<>();
        LRUCache map=new LRUCache(2);
        map.put(1,1);
        map.put(2,2);
        map.get(1);
        map.put(3,3);

        System.out.println(map.get(2));

        /*HashMap<nodeKey, Integer> map1=new HashMap<>(13,0.5f);
        map1.put(new nodeKey(2),3);
        map1.put(new nodeKey(1),3);
        System.out.println(map1.size());*/
    }

    /*public LinkedHashMap(int initialCapacity,
                         float loadFactor,
                         boolean accessOrder)*/

    public LRUCache(int initialCapacity) {
        super(initialCapacity,1,true);
        capacity=initialCapacity;
    }

    public int get(int key) {
     return getOrDefault(key,-1);
    }

    public void put(int key, int value) {
        super.put(key,value);
    }

    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest){
            return size()>capacity;
    }
}

class nodeKey{
    private int val;

    public nodeKey(int val) {
        this.val = val;
    }

    @Override
    public int hashCode() {
        return 112;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
