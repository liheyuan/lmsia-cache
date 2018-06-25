package com.coder4.sbmvt.cache;

import com.coder4.sbmvt.cache.transfomer.key.CacheKeyTransformer;
import com.coder4.sbmvt.cache.transfomer.value.CacheValueTransformer;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author coder4
 */
public class MemcachedCache<K, V> implements ICache<K, V> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final int connPoolSize = 16;

    private MemcachedClient memcachedClient;

    private CacheKeyTransformer<K> keyTransformer;

    private CacheValueTransformer<V> valueTransformer;

    private Transcoder<byte[]> transcoder = new Transcoder<byte[]>() {

        @Override
        public void setPrimitiveAsString(boolean primitiveAsString) {
        }

        @Override
        public void setPackZeros(boolean packZeros) {
        }

        @Override
        public void setCompressionThreshold(int to) {
        }

        @Override
        public void setCompressionMode(CompressionMode compressMode) {
        }

        @Override
        public boolean isPrimitiveAsString() {
            return false;
        }

        @Override
        public boolean isPackZeros() {
            return false;
        }

        @Override
        public CachedData encode(byte[] o) {
            return new CachedData(0, o);
        }

        @Override
        public byte[] decode(CachedData d) {
            if (d != null) {
                return d.getData();
            } else {
                return null;
            }
        }

    };

    public MemcachedCache(MemcachedClient client,
                          CacheKeyTransformer<K> keyTransformer,
                          CacheValueTransformer<V> valueTransformer) {
        this.memcachedClient = client;

        this.keyTransformer = keyTransformer;
        this.valueTransformer = valueTransformer;
    }

    public void init() throws Exception {

        // check
        if (keyTransformer == null) {
            throw new RuntimeException("keyTransformer can not be null");
        }

        if (valueTransformer == null) {
            throw new RuntimeException("valueTransformer can not be null");
        }

    }


    @Nullable
    @Override
    public V get(K key) {
        try {
            byte[] bytes = memcachedClient.get(keyTransformer.getKey(key), transcoder);
            if (bytes == null) {
                return null;
            }
            return valueTransformer.deserialize(bytes);
        } catch (Exception e) {
            LOG.error("memcached get exception", e);
            return null;
        }
    }

    @Override
    public Map<K, V> batchGet(Collection<K> keys) {
        if (keys == null || keys.isEmpty()) {
            return new HashMap<>();
        }

        Map<K, String> key2idMap = new HashMap<>();
        for (K key : keys) {
            key2idMap.put(key, keyTransformer.getKey(key));
        }

        Collection<String> ids = key2idMap.values();

        try {
            Map<String, byte[]> map = memcachedClient.get(ids, transcoder);
            if (map == null || map.isEmpty()) {
                return new HashMap<>();
            }

            Map<K, V> result = new HashMap<>();
            for (Entry<K, String> entry : key2idMap.entrySet()) {
                K key = entry.getKey();
                String id = entry.getValue();

                byte[] bytes = map.get(id);
                if (bytes != null) {
                    result.put(key, valueTransformer.deserialize(bytes));
                }
            }
            return result;

        } catch (Exception e) {
            LOG.error("batchGet exception", e);
            return new HashMap<>();
        }
    }

    @Override
    public void put(K key, V value) {
        put(key, value, 0);
    }

    @Override
    public void put(K key, V value, int ttlSecs) {
        try {
            memcachedClient.add(
                    keyTransformer.getKey(key),
                    ttlSecs,
                    valueTransformer.serialize(value));
        } catch (Exception e) {
            LOG.error("memcached put exception", e);
        }
    }

    @Override
    public void del(K key) {
        try {
            memcachedClient.delete(keyTransformer.getKey(key));
        } catch (Exception e) {
            LOG.error("memcached del exception", e);
        }
    }

    @Override
    public void clear() {
        try {
            memcachedClient.flushAll();
        } catch (Exception e) {
            LOG.error("memcached flushAll exception", e);
        }
    }
}