package forkJoin.recursivetask;

import java.util.concurrent.RecursiveTask;

/**
 * 累加从start到end,差为1的等差数列
 */
public class NumberSum extends RecursiveTask<Long> {

    // 任务拆分最小阈值
    static final int SEQUENTIAL_THRESHOLD = 100000;

    //包括
    private Long start;
    //包括
    private Long end;

    public NumberSum(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if((end-start)<=SEQUENTIAL_THRESHOLD){
            long sum=0;
            for(long i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }else{
            long mid=(start+end)/2;
            NumberSum left=new NumberSum(start,mid);
            NumberSum right=new NumberSum(mid+1,end);
            left.fork();
            right.fork();
            return left.join()+right.join();
        }
    }
}
