package com.xiaov.seckill.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiaov
 * @since 2021-03-03 10:36
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
}
