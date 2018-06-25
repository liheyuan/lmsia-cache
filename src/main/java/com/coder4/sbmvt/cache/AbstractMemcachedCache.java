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
public abstract class AbstractMemcachedCache<K, V> implements ICache<K, V> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final int connPoolSize = 16;

    protected abstract MemcachedClient getMemcachedClient();

    protected abstract CacheKeyTransformer<K> getKeyTransformer();

    protected abstract CacheValueTransformer<V> getValueTransformer();

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

    public void init() throws Exception {

        // check
        if (getKeyTransformer() == null) {
            throw new RuntimeException("keyTransformer can not be null");
        }

        if (getValueTransformer() == null) {
            throw new RuntimeException("valueTransformer can not be null");
        }

    }


    @Nullable
    @Override
    public V get(K key) {
        try {
            byte[] bytes = getMemcachedClient().get(getKeyTransformer().getKey(key), transcoder);
            if (bytes == null) {
                return null;
            }
            return getValueTransformer().deserialize(bytes);
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
            key2idMap.put(key, getKeyTransformer().getKey(key));
        }

        Collection<String> ids = key2idMap.values();

        try {
            Map<String, byte[]> map = getMemcachedClient().get(ids, transcoder);
            if (map == null || map.isEmpty()) {
                return new HashMap<>();
            }

            Map<K, V> result = new HashMap<>();
            for (Entry<K, String> entry : key2idMap.entrySet()) {
                K key = entry.getKey();
                String id = entry.getValue();

                byte[] bytes = map.get(id);
                if (bytes != null) {
                    result.put(key, getValueTransformer().deserialize(bytes));
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
            getMemcachedClient().add(
                    getKeyTransformer().getKey(key),
                    ttlSecs,
                    getValueTransformer().serialize(value));
        } catch (Exception e) {
            LOG.error("memcached put exception", e);
        }
    }

    @Override
    public void del(K key) {
        try {
            getMemcachedClient().delete(getKeyTransformer().getKey(key));
        } catch (Exception e) {
            LOG.error("memcached del exception", e);
        }
    }

    @Override
    public void clear() {
        try {
            getMemcachedClient().flushAll();
        } catch (Exception e) {
            LOG.error("memcached flushAll exception", e);
        }
    }
}