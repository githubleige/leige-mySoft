package forkJoin.recursivetask;


import forkJoin.util.Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


/**
 * @author Fox
 *
 * 利用ForkJoinPool计算1亿个整数的和
 */
public class ForkJoinLongSumMain {
	// 获取逻辑处理器数量 12
	static final int NCPU = Runtime.getRuntime().availableProcessors();

	static long calcSum;


	public static void main(String[] args) throws Exception {
		//准备数组
		int[] array = Utils.buildRandomIntArray(100000000);

		//递归任务（需要提交到 ForkJoinPool）
		LongSum ls = new LongSum(array, 0, array.length);
//		LongSum ls1 = new LongSum(array, 0, array.length);
		// 构建ForkJoinPool线程池
  		ForkJoinPool fjp  = new ForkJoinPool(NCPU);

//		ForkJoinPool fjp1= new ForkJoinPool(NCPU,
//				ForkJoinPool.defaultForkJoinWorkerThreadFactory,
//				(t,e)-> System.out.println(t.getName()+":"+e.getMessage()),
//		false);

		//获取当前时间，方便后面计算时间
		Instant now = Instant.now();
		//ForkJoin计算数组总和（核心）
		ForkJoinTask<Long> result = fjp.submit(ls);

		System.out.println("forkjoin sum=" + result.get());
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

		fjp.shutdown();

	}
}