package volatileTest;

public class volatileBack {
    //加上volatile耗时492，不加上volatile耗时6
    //原因分析，因为这两者的区别是：加上volatile每次执行完i++,不止要写回缓存（cache行），还要写回内存
    //但是如果是不加上volatile每次只需要写回缓存（cache行）。这就是区别。读都可以从缓存行读到，但是写有区别
//    private  volatile int i=0;
    private int i=0;
    public static void main(String[] args) {
        volatileBack a=new volatileBack();
        long start = System.currentTimeMillis();
        System.out.println(a.sum());
        System.out.println(System.currentTimeMillis() - start);
    }

    private long sum(){
        for(int j=0;j< 100000000;j++){
            i++;
        }
        return i;
    }
}
