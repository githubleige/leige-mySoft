package volatileTest;

/**
 * volatile关键字有序性的重要性的体现在这个案例中
 *
 * 不是预想的输出结果（但是可能出现的结果）：
 * 30--main-->30
 * 30--join-->10
 *
 * 赋值a = 30，但是在另一个线程中取值输出输出却是10，不按预想输出。
 * 按照先行发生规则分析，有没有适用规则来保证顺序。首先是多线程所以程序次序规则不适用；没有使用synchronized
 * 或者其他concurrent包的锁，所以也没有管程锁定规则；没有使用volatile关键字，所以也不适用volatile变量规则。
 * 除此之外的规则就更加不用说。因此无规则使用，所以线程是无法确定顺序，无法保证有序。
 *
 * volatile变量规则（Volatile Variable Rule）：对一个volatile变量的写操作先行发生于后面对这个变量的读操作，
 * “后面”是指时间上的先后顺序。
 *
 * 如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用这个变量前，需要重新执行load或assign操作初始化变量的值。
 * 如果一个变量实现没有被lock操作锁定，则不允许对它执行unlock操作，也不允许去unlock一个被其他线程锁定的变量。
 * 对一个变量执行unlock操作之前，必须先把此变量同步回主内存（执行store和write操作）。
 */
public class NotSafeThreadDemo {
    public static void main(String[] args) {
        while(true){
            WrapObject wrapObject = new WrapObject();
            wrapObject.a = 30;
            final int tag = 30;
            System.out.println(tag + "--main-->" + wrapObject.a);
            //启动线程这行代码完全有可能进行指令重排序，排到wrapObject.a = 30;之前，导致wrapObject.a还是原来的值：10
            new Thread(() ->{
                System.out.println(tag +"--join-->"+wrapObject.a);
            }).start();
        }
    }
//     * 不是预想的输出结果（但是可能出现的结果）：
//     * 30--main-->30
//     * 30--join-->10
    static class WrapObject{
        public int a = 10;
    }
}
