package com.thirtygames.zero.common.etc.cache.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisShardInfo;

import com.thirtygames.zero.common.generic.GenericMapper;

@Configuration
@EnableCaching
@ComponentScan(basePackageClasses = GenericMapper.class)
// @PropertySource("classpath:redis.properties")
public class RedisCacheConfiguration {
 
//    @Autowired
//    Environment env;
 
    @Bean
    public JedisShardInfo jedisShardInfo() {
        return new JedisShardInfo("dev.30games.co.kr");
    }
 
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(jedisShardInfo());
    }
 
    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
 
    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager(redisTemplate(), 300);
    }
}
