package leetcode;

public class jumpII {
    public static void main(String[] args) {
        jumpII aa=new jumpII();
        int[] arr=new int[]{2,3,0,1,4};
        System.out.println(aa.jump(arr,arr.length-1));
    }

    public int jump(int[] nums) {
        return jump(nums,nums.length-1);
    }

    public  int jump(int[] nums,int index) {
        if(index==0){
            return 0;
        }
        int step=index;
        while (nums[index-step]<step){
            step--;
        }
        return 1+jump(nums,index-step);
    }
}
