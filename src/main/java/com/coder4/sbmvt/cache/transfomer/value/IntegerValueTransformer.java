/**
 * @(#)IntegerValueTransformer.java, Jun 25, 2018.
 * <p>
 * Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
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