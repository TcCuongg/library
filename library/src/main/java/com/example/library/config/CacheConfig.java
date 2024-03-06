package com.example.library.config;


import com.example.library.entity.Account;
import com.example.library.more.BookMore;
import jakarta.websocket.Session;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));
        return configuration;
    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration()).build();
        return cacheManager;
    }
    @Bean
    public RedisTemplate<String, Session> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Session> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Account> redisAccountTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Account> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Account> valueSerializer = new Jackson2JsonRedisSerializer<>(Account.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, BookMore> redisBookMoreTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BookMore> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<BookMore> valueSerializer = new Jackson2JsonRedisSerializer<>(BookMore.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }
}
