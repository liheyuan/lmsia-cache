package com.coder4.sbmvt.cache.transfomer.key;

/**
 * @author coder4
 */
public class DefaultCacheKeyTransformer<T> implements CacheKeyTransformer<T> {

    private String cacheType;

    public DefaultCacheKeyTransformer(String cacheType) {
        this.cacheType = cacheType;
    }

    @Override
    public String getKey(T t) {
        return cacheType + "#" + t.toString();
    }

}