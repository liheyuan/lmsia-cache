package com.coder4.sbmvt.cache;

/**
 * @author coder4
 */
public class TestKey {
    private String key;

    public TestKey() {

    }

    public TestKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}