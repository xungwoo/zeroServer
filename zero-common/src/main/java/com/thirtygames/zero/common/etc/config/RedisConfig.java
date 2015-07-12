package com.thirtygames.zero.common.etc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@ComponentScan("com.thritygames.zero.common")
@PropertySource("classpath:/prop/redis-dev.properties")
public class RedisConfig {
 
 private @Value("${redis.host}") String redisHostName;
 private @Value("${redis.port}") int redisPort;
 
 @Bean
 public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
     return new PropertySourcesPlaceholderConfigurer();
 }
 
 @Bean
 JedisConnectionFactory jedisConnectionFactory() {
     JedisConnectionFactory factory = new JedisConnectionFactory();
     factory.setHostName(redisHostName);
     factory.setPort(redisPort);
     factory.setUsePool(true);
     return factory;
 }
 
 @Bean
 RedisTemplate<Object, Object> redisTemplate() {
     RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
     redisTemplate.setConnectionFactory(jedisConnectionFactory());
     return redisTemplate;
 }
 
 @Bean
 CacheManager cacheManager() {
     return new RedisCacheManager(redisTemplate());
 }
 
}