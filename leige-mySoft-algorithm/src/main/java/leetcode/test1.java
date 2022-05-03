package leetcode;

import java.util.HashMap;
import java.util.Map;

public class test1 {

    public static void main(String[] args) {
        DiyMap map=new DiyMap();
        int[][] a=new int[][]{{1,4},{4,5},{8,10},{15,18}};
        for(int[] e : a ){
            map.put(e[0],e[1]);
        }
        System.out.println(map);


    }

}

class DiyMap extends HashMap<Integer,Integer>{
    @Override
    public Integer put(Integer key, Integer value){
        for(Map.Entry<Integer,Integer> entry : entrySet ()){
//            if((entry.getKey()<=key)&&(entry.getValue()>=value)){
//                return null;
//            }
            if((entry.getKey()>=key)&&(value>=entry.getValue())) {
                remove(entry.getKey());
            }
//            if()
            if((key>=entry.getKey()&&key<=entry.getValue())||(value>=entry.getKey()&&value<=entry.getValue())){
                remove(entry.getKey());
                key=Math.min(key,entry.getKey());
                value=Math.max(value,entry.getValue());
            }
        }
        return super.put(key,value);
    }
}
