package redisLock.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

public class redissonLock {
    public static void main(String[] args) {
        // 此为单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.0.130:6379").setDatabase(0);
        Redisson redisson=(Redisson) Redisson.create(config);
        String lockKey = "lock:product_101";
        //Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "zhuge");
        //stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);
        /*String clientId = UUID.randomUUID().toString();
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS); //jedis.setnx(k,v)
        if (!result) {
            return "error_code";
        }*/
        //获取锁对象(设置hash结构的key)
        RLock redissonLock = redisson.getLock(lockKey);
        //加分布式锁
        redissonLock.lock();  //  .setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS);
        try {
//            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock")); // jedis.get("stock")
            int stock=0;
            if (stock > 0) {
                int realStock = stock - 1;
//                stringRedisTemplate.opsForValue().set("stock", realStock + ""); // jedis.set(key,value)
                System.out.println("扣减成功，剩余库存:" + realStock);
            } else {
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            /*if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }*/
            //解锁
            redissonLock.unlock();
        }
    }
}
