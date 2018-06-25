package com.coder4.sbmvt.cache.builder;

import com.coder4.sbmvt.cache.AbstractMemcachedCache;
import com.coder4.sbmvt.cache.MemcachedCache;
import com.coder4.sbmvt.cache.transfomer.key.CacheKeyTransformer;
import com.coder4.sbmvt.cache.transfomer.value.CacheValueTransformer;
import net.rubyeye.xmemcached.MemcachedClient;

import javax.annotation.Nonnull;

/**
 * @author coder4
 */
public class MemcachedCacheBuilder<K, V> {

    public AbstractMemcachedCache<K, V> createCache(@Nonnull MemcachedClient client,
                                                    CacheKeyTransformer<K> keyTransformer,
                                                    CacheValueTransformer<V> valueTransformer) throws Exception {

        MemcachedCache<K, V> memcachedCache = new MemcachedCache<>(
                client,
                keyTransformer,
                valueTransformer);

        memcachedCache.init();

        return memcachedCache;
    }
}