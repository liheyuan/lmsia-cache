package com.coder4.sbmvt.cache.transfomer.value;

import java.nio.ByteBuffer;

/**
 * @author coder4
 */
public class IntegerValueTransformer implements CacheValueTransformer<Integer> {

    @Override
    public byte[] serialize(Integer obj) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(obj).array();
    }

    @Override
    public Integer deserialize(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}