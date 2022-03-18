package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureDemoThenApply {
    public static final Logger log = LoggerFactory.getLogger(CompletableFutureDemoThenApply.class);
    public static void main(String[] args) {
        CompletableFuture<String>  completableFuture=CompletableFuture.supplyAsync(
                new Supplier<Integer>(){
                    @Override
                    public Integer get() {
                        log.info("我先去拿钱");
                        sleep(2,TimeUnit.SECONDS);
                        log.info("我拿了2元钱");
                        return 2;
                    }
                }
        ).thenApply(money->{
            int a=money;
            log.info("我们去买辣条吧");
            sleep(1,TimeUnit.SECONDS);
            log.info("我们到小店了,我们有{}元钱",money);
            return "cheers";
        });
        log.info(completableFuture.join());
    }

    static void sleep(int t, TimeUnit u){
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}
