package com.example.library.config;


import com.example.library.RedisObject.BookMoreRedis;
import com.example.library.RedisObject.MessRedis;
import com.example.library.entity.*;
import com.example.library.RedisObject.AccountRedis;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import com.example.library.more.Mess;
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
    public RedisTemplate<String, AccountRedis> redisAccountRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AccountRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<AccountRedis> valueSerializer = new Jackson2JsonRedisSerializer<>(AccountRedis.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, BookMoreRedis> redisBookMoreTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BookMoreRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<BookMoreRedis> valueSerializer = new Jackson2JsonRedisSerializer<>(BookMoreRedis.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, BookManage> redisBookManageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BookManage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<BookManage> valueSerializer = new Jackson2JsonRedisSerializer<>(BookManage.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, String> redisStringTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<String> valueSerializer = new Jackson2JsonRedisSerializer<>(String.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, MessRedis> redisMessTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MessRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<MessRedis> valueSerializer = new Jackson2JsonRedisSerializer<>(MessRedis.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, BookStorage> redisBookStorageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BookStorage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<BookStorage> valueSerializer = new Jackson2JsonRedisSerializer<>(BookStorage.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Buy> redisBuyTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Buy> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Buy> valueSerializer = new Jackson2JsonRedisSerializer<>(Buy.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Cart> redisCartTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Cart> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Cart> valueSerializer = new Jackson2JsonRedisSerializer<>(Cart.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Category> redisCategoryTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Category> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Category> valueSerializer = new Jackson2JsonRedisSerializer<>(Category.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Notification> redisNotificationTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Notification> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Notification> valueSerializer = new Jackson2JsonRedisSerializer<>(Notification.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Storage> redisStorageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Storage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        //Set value serializer
        RedisSerializer<Storage> valueSerializer = new Jackson2JsonRedisSerializer<>(Storage.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }
}
