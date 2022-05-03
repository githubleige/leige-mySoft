package leetcode;

import java.util.Arrays;

public class trap {
    private int fisrtMaxIndex,lastMaxIndex;
    public static void main(String[] args) {
        trap aa=new trap();
        int[] arr=new int[]{4,2};
//        forwadTrap(arr,0,arr.length-1);
//        System.out.println(forwadTrap(arr,0,arr.length-1));
//        aa.trap(arr);
        System.out.println(aa.trap(arr));
    }

    public int trap(int[] height) {
        if (height==null||height.length<=2)
            return 0;
        fisrtMaxIndex=lastMaxIndex=0;
        int max=height[0];
        for(int i=1;i<=height.length-1;i++){
            if(height[i]>max){
                fisrtMaxIndex=lastMaxIndex=i;
                max=height[i];
            }
            if(height[i]==max){
                lastMaxIndex=i;
            }
        }
        int[] subHeight=new int[height.length-lastMaxIndex];
        for(int i=lastMaxIndex;i<height.length;i++){
            subHeight[height.length-1-i]=height[i];
        }
        return forwadTrap(height,0,fisrtMaxIndex)+forwadTrap(height,fisrtMaxIndex,lastMaxIndex)+forwadTrap(subHeight,0,subHeight.length-1);
    }

    private int forwadTrap(int[] height,int begin,int end){
        if(end-begin<=1){
            return 0;
        }
        int left=begin,right=0,sum=0;
        for(int i=begin+1;i<=end;i++){
            if(height[i]>=height[left]){
                right=i;
                break;
            }
            sum+=height[i];
        }
        return (right-left-1)*height[left]-sum+forwadTrap(height,right,end);
    }

    public int trap2(int[] height) {
        int sum = 0;
        //最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
        for (int i = 1; i < height.length - 1; i++) {
            int max_left = 0;
            //找出左边最高
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] > max_left) {
                    max_left = height[j];
                }
            }
            int max_right = 0;
            //找出右边最高
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > max_right) {
                    max_right = height[j];
                }
            }
            //找出两端较小的
            int min = Math.min(max_left, max_right);
            //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
            if (min > height[i]) {
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }


}
