package com.coder4.sbmvt.cache;

import com.coder4.sbmvt.cache.transfomer.key.CacheKeyTransformer;
import com.coder4.sbmvt.cache.transfomer.value.CacheValueTransformer;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 * @author coder4
 */
public class MemcachedCache<K, V> extends AbstractMemcachedCache<K, V> {

    private MemcachedClient memcachedClient;

    private CacheKeyTransformer<K> keyTransformer;

    private CacheValueTransformer<V> valueTransformer;

    public MemcachedCache(MemcachedClient client,
                          CacheKeyTransformer<K> keyTransformer,
                          CacheValueTransformer<V> valueTransformer) {
        this.memcachedClient = client;

        this.keyTransformer = keyTransformer;
        this.valueTransformer = valueTransformer;
    }

    @Override
    protected MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    @Override
    protected CacheKeyTransformer<K> getKeyTransformer() {
        return keyTransformer;
    }

    @Override
    protected CacheValueTransformer<V> getValueTransformer() {
        return valueTransformer;
    }
}