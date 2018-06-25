package com.coder4.sbmvt.cache.configuration;

import com.coder4.sbmvt.cache.builder.MemcachedClientBuilder2;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author coder4
 */
@Configuration
@ConfigurationProperties(prefix = "memcached")
public class MemcachedClientAutoConfiguration {

    // Server list seperate by space
    private String serverList;

    // Connection Pool Size, default 64
    private int connPoolSize = 64;

    public String getServerList() {
        return serverList;
    }

    public void setServerList(String serverList) {
        this.serverList = serverList;
    }

    public int getConnPoolSize() {
        return connPoolSize;
    }

    public void setConnPoolSize(int connPoolSize) {
        this.connPoolSize = connPoolSize;
    }

    @ConditionalOnMissingBean(MemcachedClient.class)
    public MemcachedClient createMemcachedClient() throws IOException {
        return MemcachedClientBuilder2.build(serverList, connPoolSize);
    }


}