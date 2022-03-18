package forkJoin.recursivetask;


import forkJoin.util.Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;


/**
 * @author Fox
 *
 * 利用ForkJoinPool计算1亿个整数的和
 */
public class LongSumMain {
	// 获取逻辑处理器数量 12
	static final int NCPU = Runtime.getRuntime().availableProcessors();

	static long calcSum;


	public static void main(String[] args) throws Exception {
		//准备数组
		int[] array = Utils.buildRandomIntArray(100000000);

		Instant now = Instant.now();
		// 单线程计算数组总和
		calcSum = seqSum(array);
		System.out.println("seq sum=" + calcSum);
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());


		//递归任务（需要提交到 ForkJoinPool）
		LongSum ls = new LongSum(array, 0, array.length);
		// 构建ForkJoinPool
  		ForkJoinPool fjp  = new ForkJoinPool(NCPU);
		//获取当前时间，方便后面计算时间
		now = Instant.now();
		//ForkJoin计算数组总和（核心）
		ForkJoinTask<Long> result = fjp.submit(ls);

		System.out.println("forkjoin sum=" + result.get());
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

		fjp.shutdown();

		//并行流的最小执行单元是：预测数据量大小 / (默认并发度 * 4) 的结果作为最小执行单元的数量（配置的默认值是cpu 数 – 1，可以通过java.util.concurrent.ForkJoinPool.common.parallelism设置）
		now = Instant.now();
		//并行流计算数组总和  并行流会出现常见的并发问题和使用的是一个公共的线程池（公共线程池会有公共线程池的问题）
		Long sum = (Long) IntStream.of(array).asLongStream().parallel().sum();
		System.out.println("IntStream sum="+sum);
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

	}


	static long seqSum(int[] array) {
		long sum = 0;
		for (int i = 0; i < array.length; ++i) {
			sum += array[i];
		}
		return sum;
	}
}