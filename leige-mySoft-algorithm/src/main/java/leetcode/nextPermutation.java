package leetcode;

import java.util.Arrays;

public class nextPermutation {

    public static void main(String[] args) {
        int[] arr=new int[]{3,2,1};
        nextPermutation aa=new nextPermutation();
        aa.nextPermutation(arr);
        for(int e : arr ){
            System.out.println(e);
        }
    }

    public void nextPermutation(int[] nums) {
        int index=nums.length-1;
        while(index!=0){
            if(nums[index]>nums[index-1]){
                break;
            }
            index--;
        }
        if(index==0){
            nums=reverse(nums);
            return;
        }
        int current=index-1,min=index;
        while(index<nums.length){
            if(nums[index]>nums[current]&&nums[index]<nums[min]){
                min=index;
            }
            index++;
        }
        swap(nums,current,min);
        sort(nums,current);
    }

    private void sort(int[] nums,int current){
        if(current<nums.length-1){
            Arrays.sort(nums,current+1,nums.length);
        }

    }

    private void swap(int[] arr,int index1,int index2){
        int temp=arr[index1];
        arr[index1]=arr[index2];
        arr[index2]=temp;
    }

    private int[] reverse(int[] nums){
        int[] numsCopy=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            numsCopy[i]=nums[nums.length-1-i];
        }
        for(int i=0;i<nums.length;i++){
            nums[i]=numsCopy[i];
        }
        return numsCopy;
    }
}
