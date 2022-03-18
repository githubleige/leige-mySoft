package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class CompletableFutureDemo {
    public static final Logger log = LoggerFactory.getLogger(CompletableFutureDemo.class);

    public static void main(String[] args) {
        CompletableFuture<String> cf;

        /*ExecutorService executorService = Executors.newFixedThreadPool(10);

        log.debug(" huangrui进入餐厅，点了份西红柿炒番茄");
        cf = CompletableFuture.supplyAsync(()->{
            log.debug("厨师炒菜");
            sleep(2,TimeUnit.SECONDS);
            return "西红柿炒番茄好了";
        },executorService).thenCombine(CompletableFuture.supplyAsync(()->{
            log.debug("服务员蒸饭");
            sleep(10,TimeUnit.SECONDS);
            return "米饭好了";
        }),(dish,rice)->{
            log.debug("服务员打饭");
            sleep(1,TimeUnit.SECONDS);
            log.debug("{},{}",dish,rice);
            return 0;
        });

       log.debug(" huangrui在刷抖音");
       log.debug("{}, huangrui开吃",cf.join());


        log.debug(" huangrui吃完饭去结账，要求开发票");*/
        /*cf = CompletableFuture.supplyAsync(()->{
            log.debug("服务员收款");
            sleep(1,TimeUnit.SECONDS);
            return "20";
        }).thenApply(money->{
            log.debug("服务员开发票，面额{}元",money);
            sleep(2,TimeUnit.SECONDS);
            return money+"元发票";
        });

        log.debug(" huangrui接到朋友电话");
        log.debug(" huangrui拿到{}，准备回家", cf.join());


        log.debug(" huangrui走出餐厅，来到公交车站，等待301路或者918路公交到来");*/
        cf = CompletableFuture.supplyAsync(() -> {
            log.debug("301路公交正在赶来");
            sleep(2,TimeUnit.SECONDS);
            return "301路到了";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            log.debug("918路公交正在赶来");
            sleep(1,TimeUnit.SECONDS);
            return "918路到了";
        }), bus -> {
            if(bus.startsWith("918")){
                throw new RuntimeException("918撞树了.......");
            }
            log.debug("301路公交正常到了");
            return bus;
        }).exceptionally(e->{
            log.debug(e.getMessage());
            log.debug(" huangrui叫出租车");
            sleep(3,TimeUnit.SECONDS);
            return "出租车到了";
        });

        log.debug("{}, huangrui坐车回家", cf.join());


    }

    //让线程sleep指定时间
    static void sleep(int t, TimeUnit u){
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}