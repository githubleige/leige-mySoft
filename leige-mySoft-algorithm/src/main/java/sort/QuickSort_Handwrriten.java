package sort;


import java.util.Arrays;

public class QuickSort_Handwrriten {

    public static int partition(int[] array, int low, int high) {
        int position=low;
        int pivot=array[high];
        while (low<high){
            if(array[low]<pivot){
                swap(array,low,position);
                position++;
            }
            low++;
        }
        swap(array,position,high);
        return position;
    }

    public static void quickSort(int[] array, int low, int high) {
        int position;
        if(low<high){
            position=partition(array,low,high);
            if (position!=low){
                quickSort(array,low,position-1);
            }
            if(position!=high){
                quickSort(array,position+1,high);
            }
        }
    }

    private static void swap(int[] array, int low, int high){
        int temp=array[low];
        array[low]=array[high];
        array[high]=temp;
    }

    public static void main(String[] args) {
        int[] array = {6,72,113,11,23,11,22,9,456};
        quickSort(array, 0, array.length -1);
        System.out.println("排序后的结果");
        System.out.println(Arrays.toString(array));
    }
}

