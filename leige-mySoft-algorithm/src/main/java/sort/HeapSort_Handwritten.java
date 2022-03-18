package sort;

import java.util.Arrays;

/**
 * 这样做的时间复杂度是n平方，不是nlgn.因为你每次都进行了
 * 这是一个错误的堆排序
 */
public class HeapSort_Handwritten {

    private static int lastSortIndex;

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNext()){
//            String[] strs=scanner.next().split(",");
//            int[] array=new int[strs.length];
//            for(int i=0;i<strs.length;i++){
//                array[i]=Integer.parseInt(strs[i]);
//            }
//        int[] array = new int[]{8,9,10,5,9,3,4,1,0};
//        int[] array = new int[]{9,8,1,4,2,3};
          int[] array=new int[]{8,9,10};
            heapSort(array);
            System.out.println(Arrays.toString(array));

        }


    private static void heapSort(int[] array){
        lastSortIndex=array.length-1;
//        adjustHeapSort(array);
        while (lastSortIndex>0){
            adjustHeapSort(array);
            swap(array,0,lastSortIndex);
            lastSortIndex--;

        }
    }

    private static void adjustHeapSort(int[] array){
        int maxIndex;
        int loopCount=(lastSortIndex-1)/2;
        //如果这里从后往前的比较的话是不是不用再往上递归了
//        for(int i=0;i<=loopCount;i++){
          for(int i=loopCount;i>=0;i--){
            maxIndex=i;
            if(array[maxIndex]<array[2*i+1]){
                maxIndex=2*i+1;
            }
            if((2*i+2<=lastSortIndex) && (array[maxIndex]<array[2*i+2])){
                maxIndex=2*i+2;
            }
            if(maxIndex!=i){
                swap(array,i,maxIndex);
                //交换后，此时i是3个数的最大值
//                int j=i;
//                while((j>0)&&(array[j]>array[(j-1)/2])){
//                    swap(array,j,(j-1)/2);
//                    j=(j-1)/2;
//                }
            }
        }
    }

    private static void swap(int[] array,int index1,int index2){
        int temp=array[index1];
        array[index1]=array[index2];
        array[index2]=temp;
    }

}
