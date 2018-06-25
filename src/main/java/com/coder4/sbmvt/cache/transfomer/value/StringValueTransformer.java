/**
 * @(#)IntegerValueTransformer.java, Jun 25, 2018.
 * <p>
 * Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.cache.transfomer.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author coder4
 */
public class StringValueTransformer implements CacheValueTransformer<String> {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private Charset CHARSET = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(String obj) {
        try {
            return obj.getBytes(CHARSET);
        } catch (Exception e) {
            LOG.error("serialize exception", e);
            return null;
        }
    }

    @Override
    public String deserialize(byte[] bytes) {
        return new String(bytes, CHARSET);
    }
}