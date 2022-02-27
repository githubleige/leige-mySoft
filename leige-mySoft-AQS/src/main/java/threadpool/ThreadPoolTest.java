package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/***
 * 线程池执行
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final Random random = new Random();
        final List<Integer> list = new ArrayList<Integer>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("时间："+(System.currentTimeMillis() - start));
        System.out.println("大小："+list.size());


    }
}
