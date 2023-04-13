package hse.leisure.voter.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Access time interval limiter.
 */
@Service
public class TimeIntervalService {
    /**
     * Кэши запросов пользователей.
     */
    LoadingCache<String, RateLimiter> ipRequestCaches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String s) throws Exception {
                    return RateLimiter.create(0.00001); //? key per second 1/86400=0.00001=24H
                }
            });

    /**
     * Получить лимитер запросов.
     * @param key Ключ.
     * @return Лимитер.
     * @throws ExecutionException
     */
    public RateLimiter getRateLimiter(String key) throws ExecutionException {
        return ipRequestCaches.get(key);
    }
}