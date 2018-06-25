package com.coder4.sbmvt.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coder4
 */
public class JsonCacheValueTransformer<T> implements CacheValueTransformer<T> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;

    private Class<T> cls;

    public JsonCacheValueTransformer(Class<T> cls) {
        this.objectMapper = new ObjectMapper();
        this.cls = cls;
    }

    @Override
    public byte[] serialize(T o) {
        byte[] defReturn = new byte[1];
        try {
            if (o == null) {
                return defReturn;
            }
            return objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            LOG.error("JsonCacheValueTransformer serialize exception", e);
            return defReturn;
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            if (bytes == null) {
                return null;
            }
            return objectMapper.readValue(bytes, cls);
        } catch (Exception e) {
            LOG.error("JsonCacheValueTransformer deserialize exception", e);
            return null;
        }
    }
}