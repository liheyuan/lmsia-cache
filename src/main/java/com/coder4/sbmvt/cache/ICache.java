package com.coder4.sbmvt.cache;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author coder4
 */
public interface ICache<K, V> {

    /**
     * Single get from cache
     *
     * @param key
     * @return
     */
    @Nullable
    V get(K key);

    /**
     * Get from cache first, if not found get from func
     *
     * @param key
     * @param func provide get without cache
     * @return
     */
    @Nullable
    default V cacheGet(K key, Function<K, V> func) {
        V val = get(key);
        if (val != null) {
            return val;
        } else {
            val = func.apply(key);
            put(key, val);
            return val;
        }
    }

    /**
     * Batch get from cache
     *
     * @param keys
     * @return
     */
    Map<K, V> batchGet(Collection<K> keys);

    /**
     * Batch get from cache first with ttls, if not found get from func
     *
     * @param keys
     * @param func
     * @return
     */
    default Map<K, V> batchCacheGet(Collection<K> keys, Function<Collection<K>, Map<K, V>> func, int ttlSecs) {
        // hit map
        Map<K, V> hitMap = batchGet(keys);

        // miss keys
        Collection<K> missedKeys = null;
        if (hitMap == null || hitMap.isEmpty()) {
            missedKeys = keys;
        } else {
            missedKeys = keys.stream().filter(k -> !hitMap.containsKey(k)).collect(Collectors.toSet());
        }

        // check if no miss keys
        if (missedKeys == null || missedKeys.isEmpty()) {
            return hitMap;
        }

        // fetch miss key
        Map<K, V> missMap = func.apply(missedKeys);
        missMap.entrySet().forEach(e -> put(e.getKey(), e.getValue(), ttlSecs));

        if (missMap == null || missMap.isEmpty()) {
            // no miss map again
            return hitMap;
        } else {
            // union & return
            hitMap.putAll(missMap);
            return hitMap;
        }
    }

    /**
     * Batch get from cache first, if not found get from func
     *
     * @param keys
     * @param func
     * @return
     */
    default Map<K, V> batchCacheGet(Collection<K> keys, Function<Collection<K>, Map<K, V>> func) {
        return batchCacheGet(keys, func, 0);
    }

    /**
     * Single put
     *
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * Single put with ttls
     *
     * @param key
     * @param value
     */
    void put(K key, V value, int ttlSecs);

    /**
     * Delete entry form cache
     *
     * @param key
     */
    void del(K key);

    /**
     * Delete entry(s) form cache
     *
     * @param keys keys to delete
     */
    default void batchDel(Collection<K> keys) {
        if (keys != null) {
            keys.stream().forEach(key -> del(key));
        }
    }

    /**
     * delete all entry(s) in cache
     */
    void clear();

}