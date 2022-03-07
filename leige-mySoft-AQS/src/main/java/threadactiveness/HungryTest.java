package threadactiveness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * @author  Fox
 * 定长线程池饥饿示例
 *
 */
public class HungryTest {
    public static final Logger log = LoggerFactory.getLogger(HungryTest.class);
 
    static final List<String> FOODS = Arrays.asList("猪脚饭", "宫保鸡丁", "鱼香肉丝", "麻婆豆腐");
 
    static final Random RANDOM = new Random();
 
    static ExecutorService pool = Executors.newFixedThreadPool(2);
 
    //随机做菜
    public static String cooking() {
        //RANDOM.nextInt(FOODS.size())产生一个0到FOODS.size()的随机整数
        return FOODS.get(RANDOM.nextInt(FOODS.size()));
    }


    public static void main(String[] args) throws InterruptedException {
        // 服务员需要点菜、以及自己去做菜
        HungryTest.test();
    }

    //这里的饥饿主要就是体现在线程池里面只有2个线程，一个线程提交任务后，这个任务里面又提交了一个任务，现在这个任务需要里面这个任务结束。
    //但是里面这个任务，又在排队
    public static void test() {
        pool.execute(() -> {
            //服务员开始点菜
            log.info("开始给顾客点菜");
            Future<String> food = pool.submit(() -> {
                log.info("开始做菜");
                return cooking();
            });
 
            //该服务员点完菜上菜
//            log.info("上菜:{}", cooking());
            //food.get()产生依赖，如果没有返回值，那么会阻塞线程
            try {
                log.info("上菜:{}", food.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        pool.execute(() -> {
            //服务员开始点菜
            log.info("开始给顾客点菜");
            Future<String> food = pool.submit(() -> {
                log.info("开始做菜");
                return cooking();
            });

            //该服务员点完菜上菜
//            log.info("上菜:{}", cooking());
            try {
                log.info("上菜:{}", food.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

    }
}
