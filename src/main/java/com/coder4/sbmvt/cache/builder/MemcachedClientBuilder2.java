package com.coder4.sbmvt.cache.builder;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;

/**
 * @author coder4
 */
public class MemcachedClientBuilder2 {

    public static MemcachedClient build(String serverList, int connPoolSize) throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(serverList));
        // conn pool
        builder.setConnectionPoolSize(connPoolSize);
        // consistent hash
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        return builder.build();
    }

}