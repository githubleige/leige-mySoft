package redisMultiCache.service;

import com.alibaba.fastjson.JSON;
import redisMultiCache.common.RedisKeyPrefixConst;
import redisMultiCache.common.RedisUtil;
import redisMultiCache.dao.ProductDao;
import redisMultiCache.model.Product;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 通过在写数据库的操作前面加分布式写锁，在需要查数据库的地方加分布式读锁（解决双写不一致问题）
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Redisson redisson;

    public static final Integer PRODUCT_CACHE_TIMEOUT = 60 * 60 * 24;
    public static final String EMPTY_CACHE = "{}";
    public static final String LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX = "lock:product:hot_cache_create:";
    public static final String LOCK_PRODUCT_UPDATE_PREFIX = "lock:product:update:";
    public static Map<String, Product> productMap = new HashMap<>();

    @Transactional
    public Product create(Product product) {
        Product productResult = productDao.create(product);
        redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult));
        return productResult;
    }

    @Transactional
    public Product update(Product product) {
        Product productResult = null;
        //RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        //读写锁的key还是一样的（hash的key）
        RReadWriteLock productUpdateLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + product.getId());
        RLock writeLock = productUpdateLock.writeLock();
        //加分布式写锁解决缓存双写不一致问题
        writeLock.lock();
        try {
            productResult = productDao.update(product);
            redisUtil.set(RedisKeyPrefixConst.PRODUCT_CACHE + productResult.getId(), JSON.toJSONString(productResult),
                    genProductCacheTimeout(), TimeUnit.SECONDS);
        } finally {
            writeLock.unlock();
        }
        return productResult;
    }

    public Product get(Long productId) {
        Product product = null;
        String productCacheKey = RedisKeyPrefixConst.PRODUCT_CACHE + productId;

        //从缓存里查数据
        product = getProductFromCache(productCacheKey);
        if (product != null) {
            return product;
        }

        //加分布式锁解决热点缓存并发重建问题（防止的问题在直播带货的过程中，冷门商品突然变成热门商品，
        // 在某一时刻突然出现几万请求过来击穿数据库，数据库容忍的最大并发量就是几百了顶多1千多，如果这里加锁就会导致只有一个请求会去查询数据库，其他的都直接从缓存中拿）
        RLock hotCreateCacheLock = redisson.getLock(LOCK_PRODUCT_HOT_CACHE_CREATE_PREFIX + productId);
        hotCreateCacheLock.lock();
        // 这个优化谨慎使用，防止超时导致的大规模并发重建问题（把时间设置的较大就可以解决，不然这种并发量太小了，无法忍受）
        // hotCreateCacheLock.tryLock(1, TimeUnit.SECONDS);
        try {
            product = getProductFromCache(productCacheKey);
            if (product != null) {
                return product;
            }

            //RLock productUpdateLock = redisson.getLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            RReadWriteLock productUpdateLock = redisson.getReadWriteLock(LOCK_PRODUCT_UPDATE_PREFIX + productId);
            //读写锁的key还是一样的（hash的key）
            RLock rLock = productUpdateLock.readLock();
            //加分布式读锁解决缓存双写不一致问题
            rLock.lock();
            try {
                //到库里去查
                product = productDao.get(productId);
                if (product != null) {
                    redisUtil.set(productCacheKey, JSON.toJSONString(product),
                            genProductCacheTimeout(), TimeUnit.SECONDS);
                } else {
                    //设置空缓存解决缓存穿透问题（防止用户恶意用压测工具，发起大量不存在的key来查询，这里也只会解决重复key的查询）
                    redisUtil.set(productCacheKey, EMPTY_CACHE, genEmptyCacheTimeout(), TimeUnit.SECONDS);
                }
            } finally {
                rLock.unlock();
            }
        } finally {
            hotCreateCacheLock.unlock();
        }

        return product;
    }


    private Integer genProductCacheTimeout() {
        //加随机超时机制解决缓存批量失效(击穿)问题
        return PRODUCT_CACHE_TIMEOUT + new Random().nextInt(5) * 60 * 60;
    }

    private Integer genEmptyCacheTimeout() {
        return 60 + new Random().nextInt(30);
    }

    private Product getProductFromCache(String productCacheKey) {
        Product product = null;
        //多级缓存查询，jvm级别缓存可以交给单独的热点缓存系统统一维护，有变动推送到各个web应用系统自行更新
        product = productMap.get(productCacheKey);
        if (product != null) {
            return product;
        }
        String productStr = redisUtil.get(productCacheKey);
        if (!StringUtils.isEmpty(productStr)) {
            if (EMPTY_CACHE.equals(productStr)) {
                redisUtil.expire(productCacheKey, genEmptyCacheTimeout(), TimeUnit.SECONDS);
                return new Product();
            }
            product = JSON.parseObject(productStr, Product.class);
            //缓存读延期
            redisUtil.expire(productCacheKey, genProductCacheTimeout(), TimeUnit.SECONDS);
        }
        return product;
    }

}