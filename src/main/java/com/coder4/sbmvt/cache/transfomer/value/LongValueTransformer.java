package com.coder4.sbmvt.cache.transfomer.value;

import java.nio.ByteBuffer;

/**
 * @author coder4
 */
public class LongValueTransformer implements CacheValueTransformer<Long> {

    @Override
    public byte[] serialize(Long obj) {
        return ByteBuffer.allocate(Long.BYTES).putLong(obj).array();
    }

    @Override
    public Long deserialize(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }
}