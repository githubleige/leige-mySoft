package leetcode;

import java.util.TreeMap;

public class canJump {
    static boolean  flag;
    public static void main(String[] args) {
//        int[] nums = new int[]{2,0,6,9,8,4,5,0,8,9,1,2,9,6,6,5,8,0,1,9,5,7,7,1,5,8,2,8,2,6,8,2,2,7,5,1,7,9,6};
//        maxcanJump(nums,nums.length-1);
//        System.out.println(flag);
        TreeMap<Integer, String> map = new TreeMap();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");
        System.out.println(map.keySet());
    }

    /*public static void canJump(int[] nums,int lastIndex) {
        int tempIndex;
        if(lastIndex==0){
            flag=true;
            return;
        }
        for(int step=1;step<=lastIndex;step++){
            tempIndex=lastIndex-step;
            if(nums[tempIndex]>=step&&(!flag)){
                canJump(nums,tempIndex);
            }
        }
    }*/

    public static void maxcanJump(int[] nums,int lastIndex) {
        int tempIndex;
        if(lastIndex==0){
            flag=true;
            return;
        }
        for(int step=lastIndex;step>=1;step--){
            tempIndex=lastIndex-step;
            if(nums[tempIndex]>=step&&(!flag)){
                maxcanJump(nums,tempIndex);
            }
        }
    }

    public boolean canJump(int[] nums) {
        // 记录能跳到的【最远距离】 max
        int max = 0;
        for (int i = 0; i < nums.length ; i++) { // 最终 i == nums.length - 1
            // 如果i > max，说明这个位置跳不到，return false，可能中间就return false了
            if (i > max) {
                return false;
            }
            max = Math.max(max, i + nums[i]); // 更新最远距离
        }
        return true;
    }

}
