package com.coder4.sbmvt.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author coder4
 */
public class LocalCacheTest {

    private static void testLocalCache() throws InterruptedException {
        LocalCache<TestKey, TestValue> cache = new LocalCache<>(100, 1);

        TestKey key1 = new TestKey("key1");
        TestKey key2 = new TestKey("key2");
        TestKey key3 = new TestKey("key3");
        TestValue val1 = new TestValue("val1-1", "val1-2");
        TestValue val2 = new TestValue("val2-1", "val2-2");

        cache.put(key1, val1);
        cache.put(key2, val2);

        System.out.println(cache.get(key1));
        System.out.println(cache.get(key2));
        System.out.println(cache.get(key3));
        System.out.println(cache.batchGet(Arrays.asList(key1, key2, key3)));

        Thread.sleep(1050);

        System.out.println(cache.get(key1));
        System.out.println(cache.get(key2));
        System.out.println(cache.get(key3));
        System.out.println(cache.batchGet(Arrays.asList(key1, key2, key3)));

    }

    private static void testLocalCacheBM() throws InterruptedException {
        LocalCache<TestKey, TestValue> cache = new LocalCache<>(100, 0);

        TestKey key1 = new TestKey("key1");
        TestKey key2 = new TestKey("key2");
        TestKey key3 = new TestKey("key3");
        TestValue val1 = new TestValue("val1-1", "val1-2");
        TestValue val2 = new TestValue("val2-1", "val2-2");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 3000000; i++) {
            cache.put(key1, val1);
            cache.put(key2, val2);
            cache.batchGet(Arrays.asList(key1, key2, key3));
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    private static void testCacheGet() {
        LocalCache<TestKey, TestValue> cache = new LocalCache<>(100, 0);

        TestKey key1 = new TestKey("key1");
        TestKey key2 = new TestKey("key2");
        TestKey key3 = new TestKey("key3");
        TestValue val1 = new TestValue("val1-1", "val1-2");
        TestValue val2 = new TestValue("val2-1", "val2-2");

        cache.put(key1, val1);
        cache.cacheGet(key1, key -> {
            System.out.println("use func for key 1");
            return val1;
        });

        cache.cacheGet(key2, key -> {
            System.out.println("use func for key 2");
            return val2;
        });

        cache.cacheGet(key2, key -> {
            System.out.println("use func for key 2 again");
            return val2;
        });
    }

    private static void testBatchCacheGet() {
        LocalCache<TestKey, TestValue> cache = new LocalCache<>(100, 0);

        TestKey key1 = new TestKey("key1");
        TestKey key2 = new TestKey("key2");
        TestKey key3 = new TestKey("key3");
        TestValue val1 = new TestValue("val1-1", "val1-2");
        TestValue val2 = new TestValue("val2-1", "val2-2");
        TestValue val3 = new TestValue("val3-1", "val3-2");

        cache.batchCacheGet(Arrays.asList(key1, key2), keys -> {
            System.out.println("use func for key 1 & key2");
            Map<TestKey, TestValue> map = new HashMap();
            map.put(key1, val1);
            map.put(key2, val2);
            return map;
        });

        cache.batchCacheGet(Arrays.asList(key1, key2), keys -> {
            System.out.println("use func for key 1 & key2 again");
            Map<TestKey, TestValue> map = new HashMap();
            map.put(key1, val1);
            map.put(key2, val2);
            return map;
        });

        cache.batchCacheGet(Arrays.asList(key2, key3), keys -> {
            System.out.println("use func for key 3");
            Map<TestKey, TestValue> map = new HashMap();
            map.put(key3, val3);
            return map;
        });
    }

    public static void main(String[] args) throws InterruptedException {
//        testLocalCache();
//        testLocalCacheBM();
        // testCacheGet();
        testBatchCacheGet();
    }
}