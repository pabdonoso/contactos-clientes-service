package com.bankia.smi.service;

import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.stereotype.Service;


@Service
public class RedisService {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${tasbp.queue.input.contactoTelefonico}")
    private String queueContactoTelefonico;


    public boolean sendRedis(String message) throws Exception {

        Jedis jedisClient = this.jedisFactory();
        jedisClient.lpush(queueContactoTelefonico, (message));
        return true;
    }


    private Jedis jedisFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(54);
        JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
        return jedisPool.getResource();
    }

}
