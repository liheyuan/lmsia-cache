package com.coder4.sbmvt.cache.configuration;

/**
 * @author coder4
 */
public class LocalCacheConfig {
    private int ttlSecs;
    private int capacity;

    public int getTtlSecs() {
        return ttlSecs;
    }

    public void setTtlSecs(int ttlSecs) {
        this.ttlSecs = ttlSecs;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}