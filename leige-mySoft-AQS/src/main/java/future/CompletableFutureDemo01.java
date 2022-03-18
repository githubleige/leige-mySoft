package future;

import java.util.concurrent.*;

public class CompletableFutureDemo01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Runnable runnable = () -> System.out.println("执行无返回结果的异步任务");
        CompletableFuture.runAsync(runnable);

        ExecutorService executorService=new ThreadPoolExecutor(5,
                5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()+"执行有返回值的异步任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World";
        },executorService);
//        String result = future.join();
        String result = future.get();
        System.out.println(result);


    }
}