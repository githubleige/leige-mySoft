package leetcode;

public class test3 {
    public static void main(String[] args) {
        int[] ar1r=new int[]{1,2,4,5,6,0};
        int[] ar2r=new int[]{3};
        merge(ar1r,5,ar2r,1);
        for(int e : ar1r){
            System.out.println(e);
        }
    }
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if(n==0){
            return;
        }
        int[] tempNums1=new int[m];
        for(int i=0;i<m;i++){
            tempNums1[i]=nums1[i];
        }
        int index1=0,index2=0,index=0;
        while((index1<m)&&(index2<n)){
            nums1[index++]=tempNums1[index1]<=nums2[index2]?tempNums1[index1++]:nums2[index2++];
        }
        if(index1>=m){
            for(;index2<n;index2++){
                nums1[index++]=nums2[index2];
            }
        }else {
            for(;index1<m;index1++){
                nums1[index++]=tempNums1[index1];
            }
        }
    }
}
