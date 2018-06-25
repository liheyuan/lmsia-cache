package com.coder4.sbmvt.cache.transfomer.key;

/**
 * @author coder4
 */
public interface CacheKeyTransformer<T> {

    String getKey(T t);

}