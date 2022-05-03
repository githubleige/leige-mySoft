package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class test2 {
    public static void main(String[] args) {
        test2 aa=new test2();
//        [[1,2],[3,5],[6,7],[8,10],[12,16]]
        int[][] arr=new int[][]{{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] temp=new int[]{4,8};
        int[][] bb=aa.insert(arr,temp);
        System.out.println();
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        Map<Integer,Integer> map=new TreeMap<>();
        int left,right,cur=0,temp,last;
        for(int[] arr : intervals){

            if((arr[0]<=newInterval[0])&&(arr[1]>=newInterval[1])){
                return intervals;
            }

//            if(left!=arr[0])||(right!=arr[1])
            if(contains(arr,newInterval[0])||contains(arr,newInterval[1])){
                left=Math.min(arr[0],newInterval[0]);
                right=Math.max(arr[1],newInterval[1]);
                arr[0]=left;
                temp=arr[1];
                arr[1]=right;
                if(arr[1]!=temp){
                    last=arr[1];
                    for(cur++;cur<intervals.length;cur++){
                        if(last>=intervals[cur][0]){
                            int max=Math.max(intervals[cur][1],last);
                            if(max==intervals[cur][1]){
                                map.put(arr[0],max);
                                continue;
                            }
                            continue;
                        }
                        map.put(intervals[cur][0],intervals[cur][1]);
                        return mapToArray(map);
                    }
                    if(cur==intervals.length){
                        return mapToArray(map);
                    }
                }
            }
            map.put(arr[0],arr[1]);
            cur++;
        }
        return null;
    }

    private int[][] mapToArray(Map<Integer,Integer> map){
        int[][] arr=new int[map.size()][2];
        int curr=0;
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            arr[curr][0]=entry.getKey();
            arr[curr][1]=entry.getValue();
            curr++;
        }
        return arr;
    }

    private boolean contains(int[] arr,int key){
        return key>=arr[0]&&key<=arr[1];
    }
}
