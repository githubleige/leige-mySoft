package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;


public class CompletableFutureDemothenAcceptBoth {
    public static final Logger log = LoggerFactory.getLogger(CompletableFutureDemothenAcceptBoth.class);

    public static void main(String[] args) {

        CompletableFuture<Integer> futrue1= CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
             public Integer get() {
                int number = new Random().nextInt(3) + 1;
                log.info("是否是后台进程"+Thread.currentThread().isDaemon());
                  try {
                    TimeUnit.SECONDS.sleep(number);
                     } catch (InterruptedException e) {
                     e.printStackTrace();
                      }
                  log.info("第一阶段："+ number);
                  return number;
                }
             });

         CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
              @Override
             public String get() {
                  int number = new Random().nextInt(3) + 1;
                  log.info("是否是后台进程"+Thread.currentThread().isDaemon());
                  try {
                      TimeUnit.SECONDS.sleep(number);
                     } catch (InterruptedException e) {
                     e.printStackTrace();
                     }
                  log.info("第二阶段："+ number);
                return ""+number;
                  }
             });

//       Void voida=futrue1.thenAcceptBoth(future2, new BiConsumer<Integer, String>() {
//              @Override
//             public void accept(Integer x, String y) {
//                   log.info("最终结果："+ (x + y));
//                  }
//             }).join();
        futrue1.thenAcceptBoth(future2, new BiConsumer<Integer, String>() {
            @Override
            public void accept(Integer x, String y) {
                log.info("最终结果是否是后台进程"+Thread.currentThread().isDaemon());
                log.info("最终结果："+ (x + y));
            }
        });
        System.out.println("主线程结束");
        }


    static void sleep(int t, TimeUnit u){
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}

