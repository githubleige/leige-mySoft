package sort;

import java.util.Arrays;

public class HeapSort_Handwritten2 {

    private static int lastSortIndex;

    public static void main(String[] args) {
        int[] array=new int[]{8,9,10};
//        int[] array = new int[]{9, 8, 7, 6, 5, 43, 1};
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void heapSort(int[] array){
        lastSortIndex=array.length-1;
        buildHeap(array);
        while (lastSortIndex>=0){
            swap(array,0,lastSortIndex);
            lastSortIndex--;
            adjustHeap(array,0);
        }
    }

    private static void buildHeap(int[] array){
        int lastNonLeaf=(lastSortIndex-1)/2;
        int maxIndex;
        for(int i=lastNonLeaf;i>=0;i--){
            maxIndex=i;
            if(((2*i+1)<=lastSortIndex)&&(array[2*i+1]>array[maxIndex])){
                maxIndex=2*i+1;
            }
            if(((2*i+2)<=lastSortIndex)&&(array[2*i+2]>array[maxIndex])){
                maxIndex=2*i+2;
            }
            if(maxIndex!=i){
                swap(array,maxIndex,i);
                adjustHeap(array,maxIndex);
            }
        }
    }

    private static void adjustHeap(int[] array,int i){

            int maxIndex=i;
            if(((2*i+1)<=lastSortIndex)&&(array[2*i+1]>array[maxIndex])){
                maxIndex=2*i+1;
            }
            if(((2*i+2)<=lastSortIndex)&&(array[2*i+2]>array[maxIndex])){
                maxIndex=2*i+2;
            }
            if(maxIndex!=i){
                swap(array,maxIndex,i);
                adjustHeap(array,maxIndex);
            }

    }

    private static void swap(int[] array,int i,int j){
        int temp=array[i];
        array[i]=array[j];
        array[j]=temp;
    }
}
