package com.coder4.sbmvt.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author coder4
 */
public class LocalCache<K, V> implements ICache<K, V> {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private Cache<K, V> gCache;

    private long ttlSecs = 0;

    public LocalCache(long capacity, long ttlSecs) {

        CacheBuilder builder = CacheBuilder.newBuilder();
        if (capacity > 0) {
            builder.maximumSize(capacity);
        }
        if (ttlSecs > 0) {
            this.ttlSecs = ttlSecs;
            builder.expireAfterWrite(ttlSecs, TimeUnit.SECONDS);
        }

        this.gCache = builder.build();
    }

    @Nullable
    @Override
    public V get(K key) {
        return gCache.getIfPresent(key);
    }

    @Override
    public Map<K, V> batchGet(Collection<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return new HashMap<>();
        } else {
            Map<K, V> result = new HashMap<>();
            for (K key : keys) {
                V val = gCache.getIfPresent(key);
                if (val != null) {
                    result.put(key, val);
                }
            }
            return result;
        }
    }

    @Override
    public void put(K key, V value) {
        gCache.put(key, value);
    }

    @Override
    public void put(K key, V value, int curTtlSecs) {
        if (curTtlSecs != this.ttlSecs) {
            LOG.error("not support per-put ttlSecs currently");
        }
        put(key, value);
    }

    @Override
    public void del(K key) {
        gCache.invalidate(key);
    }

    @Override
    public void batchDel(Collection<K> keys) {
        gCache.invalidateAll(keys);
    }

    @Override
    public void clear() {
        gCache.invalidateAll();
    }
}