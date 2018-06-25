package com.coder4.sbmvt.cache.builder;

import com.coder4.sbmvt.cache.configuration.MemcachedClientAutoConfiguration.MemcachedConfiguration;
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

    public static MemcachedClient build(MemcachedConfiguration config) throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(config.getServerList()));
        // conn pool
        builder.setConnectionPoolSize(config.getConnPoolSize());
        // consistent hash
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        return builder.build();
    }

}