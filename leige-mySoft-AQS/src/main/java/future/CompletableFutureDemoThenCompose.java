package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;


public class CompletableFutureDemoThenCompose {
    public static final Logger log = LoggerFactory.getLogger(CompletableFutureDemoThenCompose.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future=CompletableFuture.supplyAsync(new Supplier<Integer>(){
                @Override
                public Integer get() {
                    int num=new Random().nextInt(30);
                    log.info("第一阶段："+num);
                    return num;
                }
        }
        ).thenCompose(new Function<Integer, CompletionStage<String>>(){

            @Override
            public CompletionStage<String> apply(Integer param) {
                return CompletableFuture.supplyAsync(new Supplier<String>(){

                    @Override
                    public String get() {
                        int number=param*2;
                        log.info("第二阶段："+number);
                        return ""+number;
                    }
                });
            }
        });
        log.info("最终结果:"+future.get());
    }
}