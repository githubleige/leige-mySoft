package forkJoin.recursivetask;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class numberSumForkJoin {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Instant now = Instant.now();
        long sum0=0;
        for(Long i=1L;i<=50000000L;i++){
            sum0+=i;
        }
        System.out.println("单线程总和是："+sum0);
        System.out.println("单线程执行时间："+ Duration.between(now,Instant.now()).toMillis());

        now = Instant.now();
        NumberSum sum=new NumberSum(1L,50000000L);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask= forkJoinPool.submit(sum);
        System.out.println("总和是："+forkJoinTask.get());
        System.out.println("fork/join执行时间："+ Duration.between(now,Instant.now()).toMillis());
        forkJoinPool.shutdown();
        System.out.println(Long.MAX_VALUE);
    }
}
