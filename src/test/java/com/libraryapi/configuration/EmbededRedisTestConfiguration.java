package com.libraryapi.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@TestConfiguration
public  class EmbededRedisTestConfiguration {


    private RedisServer redisServer;

    public EmbededRedisTestConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @PostConstruct
    public void startRedis() {
        this.redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}
