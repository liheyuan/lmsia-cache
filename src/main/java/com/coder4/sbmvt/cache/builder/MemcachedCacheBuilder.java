package com.coder4.sbmvt.cache.builder;

import com.coder4.sbmvt.cache.CacheKeyTransformer;
import com.coder4.sbmvt.cache.CacheValueTransformer;
import com.coder4.sbmvt.cache.MemcachedCache;
import com.coder4.sbmvt.cache.configuration.MemcachedClientAutoConfiguration.MemcachedConfiguration;
import net.rubyeye.xmemcached.MemcachedClient;

import javax.annotation.Nonnull;

/**
 * @author coder4
 */
public class MemcachedCacheBuilder<K, V> {

    public MemcachedCache<K, V> createCache(@Nonnull MemcachedConfiguration config,
                                            CacheKeyTransformer<K> keyTransformer,
                                            CacheValueTransformer<V> valueTransformer) throws Exception {
        if (config == null) {
            throw new RuntimeException("LocalCacheConfig is null");
        }

        // Step 1: build Client
        MemcachedClient client = MemcachedClientBuilder2.build(config);

        // Step 2: build Cache
        MemcachedCache<K, V> memcachedCache = new MemcachedCache<>(
                client,
                keyTransformer,
                valueTransformer);

        memcachedCache.init();

        return memcachedCache;
    }
}