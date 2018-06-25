package com.coder4.sbmvt.cache;

/**
 * @author coder4
 */
public interface CacheValueTransformer<T> {

    byte[] serialize(T obj);

    T deserialize(byte[] bytes);

}