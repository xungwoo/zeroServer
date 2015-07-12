package com.thirtygames.zero.common.etc.cache.redis;

import java.io.Serializable;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;



@Slf4j
public class RedisCache implements Cache {
    @Getter
    private String name;
    @Getter
    private int expireSeconds;
 
    private RedisTemplate<Object, Serializable> redisTemplate;
 
    public RedisCache(String name, RedisTemplate<Object, Serializable> redisTemplate) {
        this(name, redisTemplate, 300);
    }
 
    public RedisCache(String name, RedisTemplate<Object, Serializable> redisTemplate, int expireSeconds) {
//        Guard.shouldNotBeEmpty(name, "name");
//        Guard.shouldNotBeNull(redisTemplate, "redisTemplate");
 
        this.name = name;
        this.redisTemplate = redisTemplate;
        
        log.debug("RedisCache Create!!" + name + ":" + redisTemplate);
    }
 
    @Override
    public Object getNativeCache() {
        return redisTemplate;
    }
 
    public String getKey(Object key) {
        return name + ":" + key;
    }
 
    @Override
    public ValueWrapper get(Object key) {
//        Guard.shouldNotBeNull(key, "key");
    	
    	log.debug("Cache Key::" + key);
 
        Object result = redisTemplate.opsForValue().get(getKey(key));
 
        SimpleValueWrapper wrapper = null;
        if (result != null) {
        	log.debug("Load Cache key::" + key);
            wrapper = new SimpleValueWrapper(result);
        }
        return wrapper;
    }
 
    @Override
    public void put(Object key, Object value) {
//        Guard.shouldNotBeNull(key, "key");
 
    	log.debug("Save Cache::" + key + ":" + value);
        redisTemplate.opsForValue().set(getKey(key), (Serializable) value, expireSeconds);
    }
 
    @Override
    public void evict(Object key) {
//        Guard.shouldNotBeNull(key, "key");
    	log.debug("Delete Cache ::" + key);
 
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
        	log.error("Fail Delete Cache::" + key, e);
        }
    }
 
    @Override
    public void clear() {
    	log.debug("Delete All Cache.");
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.flushAll();
                    return null;
                }
            });
        } catch (Exception e) {
        	log.warn("Fail delete all cache", e);
        }
    }
}
