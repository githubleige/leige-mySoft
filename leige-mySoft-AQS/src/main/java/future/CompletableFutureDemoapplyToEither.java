package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemoapplyToEither {
    public static final Logger log = LoggerFactory.getLogger(CompletableFutureDemoapplyToEither.class);
    public static void main(String[] args) {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
                public Integer get() {
                int number = new Random().nextInt(10);
                log.info("第一阶段start：" + number);
                try {
                    TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                log.info("第一阶段end：" + number);
                return number;
            }
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
             @Override
             public Integer get() {
                int number = new Random().nextInt(10);
                log.info("第二阶段start：" + number);
                try {
                    TimeUnit.SECONDS.sleep(number);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                log.info("第二阶段end：" + number);
                return number;
            }
        });

        future1.applyToEither(future2, new Function<Integer, Integer>() {
                @Override
            public Integer apply(Integer number) {
              //谁先执行完，下面就用执行完的那个线程来执行
             log.info("最快结果：" + number);
                return number * 2;
             }
        }).join();
    }
}
