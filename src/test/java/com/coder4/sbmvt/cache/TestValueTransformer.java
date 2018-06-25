package com.coder4.sbmvt.cache;

import com.coder4.sbmvt.cache.transfomer.value.JsonCacheValueTransformer;

/**
 * @author coder4
 */
public class TestValueTransformer extends JsonCacheValueTransformer<TestValue> {

    public TestValueTransformer() {
        super(TestValue.class);
    }
}