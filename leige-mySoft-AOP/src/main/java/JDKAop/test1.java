package JDKAop;

public class test1 {
    public static void main(String[] args) {
        int[] arrays=new int[]{0,1,2,3,4,5,6};
        int[] bb=arrays;
        System.out.println(arrays==bb);
        int[] arraysCopy = arrays.clone();
        System.out.println(arrays==arraysCopy);
    }
}
