package forkJoin.recursivetask;

import java.util.concurrent.RecursiveTask;

public class LongSum extends RecursiveTask<Long> {
    // 任务拆分最小阈值
    static final int SEQUENTIAL_THRESHOLD = 10000000;

    //开始累加的下标
    int low;
    //累加到哪个下标结束，但是不包括high,包括high-1
    int high;
    int[] array;

    LongSum(int[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    }

    @Override
    protected Long compute() {

        //当任务拆分到小于等于阀值时开始求和
        if (high - low <= SEQUENTIAL_THRESHOLD) {

            long sum = 0;
            if(low==0){

                low=1/0;

            }
            for (int i = low; i < high; ++i) {
                sum += array[i];
            }
            return sum;
        } else {  // 任务过大继续拆分
            int mid = low + (high - low) / 2;
            LongSum left = new LongSum(array, low, mid);
            LongSum right = new LongSum(array, mid, high);
            // 提交任务
            left.fork();
            right.fork();
            //为了防止在主线程中直接抛出异常，显示不友好的信息，做了以下的check
//            if(right.isCompletedAbnormally()){
//                System.out.println(right.getException().getMessage());
//                return 0L;
//            }
//            if(left.isCompletedAbnormally()){
//                System.out.println(left.getException().getMessage());
//                return 0L;
//            }
            long rightAns,leftAns;
            //获取任务的执行结果,将阻塞当前线程直到对应的子任务完成运行并返回结果
            //如果right这个ForkJoinTask的状态不是NORMAL，那么会抛出异常，所以上面先做了check
//            try {
                rightAns = right.join();
//            long rightAns = right.compute();
                leftAns = left.join();
//            }catch (Exception e){
//                System.out.println("出现异常了"+e.toString());
//                System.out.println("出现异常了"+e.getMessage());
//                return 0L;
//            }

            return leftAns + rightAns;
        }
    }
}

       