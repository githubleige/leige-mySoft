package future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CompletableFutureDemo02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //执行线程ForkJoinPool.commonPool-worker-9
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            if (new Random().nextInt(10) % 2 == 0) {
                int i = 12 / 0;
            }
            System.out.println("先方法执行结束！"+Thread.currentThread().getName());
            return "test";
        });

        future.whenComplete(new BiConsumer<String, Throwable>() {
            /**
             *
             * @param t 代表
             * @param action
             */
            //执行线程ForkJoinPool.commonPool-worker-9
            @Override
            public void accept(String t, Throwable action) {
                System.out.println(t+" 执行完成！"+Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+action.getMessage());
            }
        });

        future.exceptionally(new Function<Throwable, String>() {
            //执行线程ForkJoinPool.commonPool-worker-9
            @Override
            public String apply(Throwable t) {
                System.out.println(Thread.currentThread().getName()+"执行失败：" + t.getMessage());
                return "异常xxxx";
            }
        }).join();
    }
}